package com.viger.mycode.myglide.dn;

public class CacheTest implements Resource.ResourceListener, MemoryCacheInterface.ResourceRemoveListener {
    private LruMemoryCache lruMenoryCache;
    private ActiveResource activeResource;


    public Resource test(Key key){
        lruMenoryCache = new LruMemoryCache(10);
        lruMenoryCache.setResourceRemoveListener(this);
        activeResource = new ActiveResource(this);

        Resource resource = activeResource.get(key);

        if(null != resource) {
            resource.acquire();
            return resource;
        }

        Resource res = lruMenoryCache.get(key);
        if(null != res) {
            res.acquire();
            activeResource.activete(key, res);
            lruMenoryCache.remove2(key);
            return res;
        }
        return null;

    }

    @Override
    public void onResourceRemoved(Resource resource) {

    }

    @Override
    public void onResourceReleased(Key key, Resource resource) {

    }

}
