package com.viger.binderclient.http;

public interface IHttpCallBack {

    void onSuccess(String content);
    void onFailed(Exception e);

}
