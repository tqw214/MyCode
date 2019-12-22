package com.viger.mycode;

import java.util.Map;

public interface IExecute<T> {

    void get(String url, Map<String, String> params, HttpUtils.HttpCallBack<T> callBack);

}
