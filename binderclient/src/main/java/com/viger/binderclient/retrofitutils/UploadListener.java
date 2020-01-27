package com.viger.binderclient.retrofitutils;

public abstract class UploadListener {

    public abstract void onResponse(okhttp3.Call call, okhttp3.Response response);

    public abstract void onProgress(long progress, long total, boolean done);

    public void onFailure(okhttp3.Call call, Throwable t) {
        L.e(t);
    }

}
