package com.viger.mycode.myglide;

import android.content.Context;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 每次调用都是同一个对象
 */
class RequestManager {

    private static RequestManager instance;
    private Context context;
    private String url;
    private LinkedBlockingQueue<RequestBuilder> requestQueue = new LinkedBlockingQueue<RequestBuilder>();
    private DispatcherTask[] dispatcherTasks;

    private RequestManager() {
    }

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


    public RequestManager addRequestQueue(RequestBuilder requestBuilder) {
        if(!requestQueue.contains(requestBuilder)) {
            requestQueue.add(requestBuilder);
        }
        return this;
    }


    public void dispatcher() {
        //Log.d("RequestManager","==================dispatcher()");
        int count = Runtime.getRuntime().availableProcessors();
        dispatcherTasks = new DispatcherTask[count];
        for (int i=0;i<dispatcherTasks.length;i++) {
            DispatcherTask task = new DispatcherTask(requestQueue);
            task.start();
            dispatcherTasks[i] = task;
        }
    }

    public void stop() {
        if(dispatcherTasks.length > 0) {
            for (DispatcherTask dispatcherTask : dispatcherTasks) {
                if(!dispatcherTask.isInterrupted()) {
                    dispatcherTask.interrupt();
                }
            }
        }
    }

}
