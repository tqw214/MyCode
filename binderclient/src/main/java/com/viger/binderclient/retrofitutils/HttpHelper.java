package com.viger.binderclient.retrofitutils;

import java.io.File;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

class HttpHelper {

    private static WeakReference<HttpHelper> sInstance;
    private volatile Retrofit mRetrofit;

    private static String sBaseUrl;


    private File mCacheFile;
    private static boolean mIsCache = true;

    private HttpHelper() {
        mCacheFile = new File(ItheimaHttp.getContext().getCacheDir().getAbsolutePath() + File.separator + "retrofit2_http_cache");
        if(!mCacheFile.exists() && !mCacheFile.isDirectory()) {
            mCacheFile.mkdirs();
        }
        OkHttpClient okHttpClient = createOkhttpAndCache();
    }

    private OkHttpClient createOkhttpAndCache() {

        return null;
    }

    public static void setHttpCache(boolean cache) {
        mIsCache  = cache;
    }

    public static void setBaseUrl(String httpBaseUrl) {
        sBaseUrl = httpBaseUrl;
    }

    public static String getBaseUrl() {
        return sBaseUrl;
    }

    public static <T> Call getAsync(String apiUrl,Object o, Object o1, HttpResponseListener<T> httpResponseListener) {
        return null;
    }

    public static <T> Call postAsync(String apiUrl,Object o, Object o1, HttpResponseListener<T> httpResponseListener) {
        return null;
    }


    public static okhttp3.Call upload(Request request, UploadListener uploadListener) {
        return null;
    }
}
