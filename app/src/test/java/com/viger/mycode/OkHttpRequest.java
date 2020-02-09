package com.viger.mycode;

import com.viger.mycode.utils.OkHttpClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class OkHttpRequest<T> implements IExecute<T> {
    @Override
    public void get(String url, Map<String, String> params, HttpUtils.HttpCallBack<T> callBack) {
        System.out.println("OkHttpRequest====> get");
    }

    public void test() {
        OkHttpClientUtils.getInstance(null).get("", new HashMap<String, String>(), new OkHttpClientUtils.MyCallback() {
            @Override
            public void onSuccess(Response response) throws IOException {

            }

            @Override
            public void onFailed(IOException e) {

            }
        });
    }
}
