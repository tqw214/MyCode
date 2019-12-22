package com.viger.mycode;

import java.util.Map;

public class OkHttpRequest<T> implements IExecute<T> {
    @Override
    public void get(String url, Map<String, String> params, HttpUtils.HttpCallBack<T> callBack) {
        System.out.println("OkHttpRequest====> get");
    }
}
