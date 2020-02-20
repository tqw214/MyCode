package com.viger.binderclient.http;

import java.util.Map;

public interface IHttpProcees {

    void doGet(String url, Map<String, String> params, IHttpCallBack callBack);

}
