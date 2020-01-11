package com.viger.mycode.net;

public interface HttpCallBack<T> {
    void onSuccess(T result);
    void onFailed(Exception e);
}
