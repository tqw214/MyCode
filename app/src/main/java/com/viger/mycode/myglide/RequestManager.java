package com.viger.mycode.myglide;

import android.content.Context;

/**
 * 每次调用都是同一个对象
 */
class RequestManager {

    private static RequestManager instance;
    private Context context;
    private String url;

    private RequestManager() {}

    public static RequestManager getInstance() {
        if(instance == null) {
            synchronized (RequestManager.class) {
                if(instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    //单例
    public RequestManager get(Context context) {
        this.context = context;
        return this;
    }

    public RequestBuilder load(String url) {
        this.url = url;
        return new RequestBuilder(context).load(url);
    }


}
