package com.viger.mycode;

import java.util.Map;

public class HttpUtils {

    private static HttpUtils instance = null;
    private static IExecute httpExecute;

    private HttpUtils(){
        //httpExecute = new OkHttpRequest();
        //httpExecute = new VolleyRequest();
    }

    public static HttpUtils getInstance() {
        if(instance == null) {
            synchronized (HttpUtils.class) {
                if(instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public static void init(IExecute iExecute) {
        httpExecute = iExecute;
    }

    public <T> void get(String url, Map<String, String> params, HttpCallBack<T> callBack) {
        httpExecute.get(url, params, callBack);
    }

    public interface HttpCallBack<T> {
        void success(T t);
        void failed(String message);
    }

}
