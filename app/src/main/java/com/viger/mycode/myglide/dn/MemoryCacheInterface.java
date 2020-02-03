package com.viger.mycode.myglide.dn;

public interface MemoryCacheInterface {

    public interface ResourceRemoveListener {
        void onResourceRemoved(Resource resource);
    }

    void setResourceRemoveListener(ResourceRemoveListener listener);

    Resource put(Key key, Resource resource);

    Resource remove2(Key key);

}
