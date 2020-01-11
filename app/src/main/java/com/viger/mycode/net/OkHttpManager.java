package com.viger.mycode.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpManager {

    public static OkHttpClient getOkHttpClick() {
        return new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
    }

}
