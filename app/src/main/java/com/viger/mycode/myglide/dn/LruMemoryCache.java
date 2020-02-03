package com.viger.mycode.myglide.dn;

import android.os.Build;
import android.util.LruCache;

public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCacheInterface {

    private ResourceRemoveListener listener;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(Key key, Resource value) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return value.getBitmap().getAllocationByteCount();
        }
        return value.getBitmap().getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, Key key, Resource oldValue, Resource newValue) {
        if(null != listener && null != oldValue) {
            this.listener.onResourceRemoved(oldValue);
        }
    }

    @Override
    public void setResourceRemoveListener(ResourceRemoveListener listener) {
        this.listener = listener;
    }

    @Override
    public Resource remove2(Key key) {
        Resource remove = remove(key);
        return remove;
    }
}
