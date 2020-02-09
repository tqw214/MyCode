package com.viger.mycode.utils;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientUtils {

    private OkHttpClient mOkHttpClient;
    private volatile static OkHttpClientUtils sOkHttpClientUtils;
    private Context mContext;

    private OkHttpClientUtils(Context context) {
        this.mContext = context;
        mOkHttpClient = new OkHttpClient
                .Builder()
                .cookieJar(new PersistenceCookieJar())
                .followRedirects(true)
                .followSslRedirects(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();

    }

    public static OkHttpClientUtils getInstance(Context context) {
        if(sOkHttpClientUtils == null) {
            synchronized (OkHttpClientUtils.class) {
                if(sOkHttpClientUtils == null) {
                    sOkHttpClientUtils = new OkHttpClientUtils(context);
                }
            }
        }
        return sOkHttpClientUtils;
    }

    public void get(String url, Map<String, String> params, MyCallback myCallback) {
        StringBuffer sb = new StringBuffer(url);
        if(params != null && !params.isEmpty()) {
            sb.append("?");
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getKey());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        url = sb.toString();
        Request request = new Request
                .Builder()
                .url(url)
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(myCallback != null) {
                    myCallback.onFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(myCallback != null) {
                    myCallback.onSuccess(response);
                }
            }
        });

    }

    public void get(String url, MyCallback myCallback) {
        get(url, null, myCallback);
    }

    public void post(String url, Map<String, String> params, MyCallback myCallback) {
        FormBody.Builder formBody = new FormBody.Builder();
        if(params!= null && !params.isEmpty()) {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myCallback.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myCallback.onSuccess(response);
            }
        });
    }


    public interface MyCallback {
        void onSuccess(Response response) throws IOException;
        void onFailed(IOException e);
    }

    //自定义
    class PersistenceCookieJar implements CookieJar {

        private List<Cookie> cache;

        private List<Cookie> invalidCookies;

        private List<Cookie> validCookies;

        public PersistenceCookieJar() {
            cache = new ArrayList<Cookie>();
        }

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cache.addAll(cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
           invalidCookies = new ArrayList<Cookie>();
           validCookies = new ArrayList<Cookie>();
            for(Cookie cookie : cache) {
                if(cookie.expiresAt() < System.currentTimeMillis()) {
                    invalidCookies.add(cookie);
                }else if(cookie.matches(url)){
                    validCookies.add(cookie);
                }
            }
            cache.removeAll(invalidCookies);
            return validCookies;
        }
    }

}
