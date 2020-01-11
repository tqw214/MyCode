package com.viger.mycode.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl("http://www.baidu.com/")
                    .client(OkHttpManager.getOkHttpClick())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ServiceApi getApi() {
        return getRetrofit().create(ServiceApi.class);
    }



}
