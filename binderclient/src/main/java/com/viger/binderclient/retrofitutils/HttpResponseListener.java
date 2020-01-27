package com.viger.binderclient.retrofitutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public abstract class HttpResponseListener<T> {

    public Type getType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type actualTypeArgument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        return actualTypeArgument;
    }

    public abstract void onResponse(T t, Headers headers);

    public void onFailure(Call<ResponseBody> call, Throwable e) {
        L.e(e);
    }

}
