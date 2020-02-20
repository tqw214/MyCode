package com.viger.binderclient.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<T> implements IHttpCallBack {

    @Override
    public void onSuccess(String content) {
        Gson gson = new Gson();
        Class clazz = getClassForObject(this);
        T data = (T) gson.fromJson(content, clazz);
        onSuccess(data);
    }

    private Class getClassForObject(Object callback) {
        Type genericSuperclass = callback.getClass().getGenericSuperclass();
        Type[] actualTypeArgument = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        return (Class) actualTypeArgument[0];
    }

    abstract void onSuccess(T t);

    abstract void onFailed(String t);

    @Override
    public void onFailed(Exception e) {
        onFailed(e.getMessage());
    }
}
