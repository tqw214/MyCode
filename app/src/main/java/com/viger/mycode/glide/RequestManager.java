package com.viger.mycode.glide;

import android.content.Context;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {

    private static RequestManager instance;
    private Context context;
    private LinkedBlockingQueue<RequestBuilder> requestQueue;
    private static final int threadCount = Runtime.getRuntime().availableProcessors();
    private DispatchTask[] dispatcherTasks;

    private RequestManager() {
        requestQueue = new LinkedBlockingQueue<RequestBuilder>();
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

    public RequestManager get(Context context) {
        this.context = context;
        return this;
    }

    public RequestBuilder load(String url) {
        return new RequestBuilder(context).load(url);
    }


    public RequestManager addRequestQueue(RequestBuilder requestBuilder) {
        if(!requestQueue.contains(requestBuilder)) {
            requestQueue.add(requestBuilder);
        }
        return this;
    }

    public void dispatcher() {
        dispatcherTasks = new DispatchTask[threadCount];
        for(int i=0;i<threadCount;i++) {
            dispatcherTasks[i] = new DispatchTask(requestQueue);
            dispatcherTasks[i].start();
        }
    }

    public void stop() {
        if(dispatcherTasks.length > 0) {
            for(DispatchTask task : dispatcherTasks) {
                if(!task.isInterrupted()) {
                    task.interrupt();
                }
            }
        }
    }

}
