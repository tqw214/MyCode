package com.viger.mycode.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Request;
import okhttp3.Response;

public interface MyInterceptor {
    Response intercept(MyInterceptor.Chain chain) throws IOException;

    interface Chain {

        Request request();

        Response proceed(Request request) throws IOException;

        Connection connection();

        Call call();

        int connectTimeoutMillis();

        MyInterceptor.Chain withConnectTimeout(int timeout, TimeUnit unit);

        int readTimeoutMillis();

        MyInterceptor.Chain withReadTimeout(int timeout, TimeUnit unit);

        int writeTimeoutMillis();

        MyInterceptor.Chain withWriteTimeout(int timeout, TimeUnit unit);
    }
}
