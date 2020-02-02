package com.viger.mycode.myglide.dn;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActiveResource {

    private Map<Key, ResourceWeakReference> weakReferenceMap = new HashMap<Key, ResourceWeakReference>();
    private ReferenceQueue<Resource> queue;
    private Thread cleanReferenceQueueTherad;
    private final Resource.ResourceListener resourceListener;
    private boolean isShutdown;

    public ActiveResource(Resource.ResourceListener listener) {
        this.resourceListener = listener;
    }

    //加入活动缓存
    public void activete(Key key, Resource resource) {
        resource.setResourceListener(key, resourceListener);
        weakReferenceMap.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
    }

    //移除活动缓存
    public Resource deactivete(Key key) {
        ResourceWeakReference reference = weakReferenceMap.remove(key);
        if(reference!=null) {
            return reference.get();
        }
        return null;
    }

    private ReferenceQueue<? super Resource> getReferenceQueue() {
        if(queue == null) {
            queue = new ReferenceQueue<Resource>();
            cleanReferenceQueueTherad = new Thread(){
                @Override
                public void run() {
                    while (!isShutdown) {
                        try {
                            ResourceWeakReference reference = (ResourceWeakReference) queue.remove();
                            weakReferenceMap.remove(reference.key);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            cleanReferenceQueueTherad.start();
        }
        return queue;
    }

    void shutdown() {
        isShutdown = true;
        if(cleanReferenceQueueTherad != null) {
            cleanReferenceQueueTherad.interrupt();
            try {
                cleanReferenceQueueTherad.join(TimeUnit.SECONDS.toMillis(5));
                if(cleanReferenceQueueTherad.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static final class ResourceWeakReference extends WeakReference<Resource> {

        final Key key;

        public ResourceWeakReference(Key key, Resource referent, ReferenceQueue<? super Resource> q) {
            super(referent, q);
            this.key = key;
        }

    }

    public Resource get(Key key) {
        ResourceWeakReference resourceWeakReference = weakReferenceMap.get(key);
        if(resourceWeakReference != null) {
            return resourceWeakReference.get();
        }
        return null;
    }

}
