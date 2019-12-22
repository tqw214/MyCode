package com.viger.mycode;

import java.util.Map;

public class VolleyRequest<T> implements IExecute<T> {
    @Override
    public void get(String url, Map<String, String> params, HttpUtils.HttpCallBack<T> callBack) {
        System.out.println("VolleyRequest===> get");
    }
}
