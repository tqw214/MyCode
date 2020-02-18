package com.viger.mycode.okhttp;

import android.util.Log;

import com.viger.mycode.BuildConfig;
import com.viger.mycode.utils.ToolsUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkUtils {

    private Retrofit.Builder retrofitBuilder;
    private static NetWorkUtils instance;

    private NetWorkUtils() {
        //初始化
        retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient());
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        //String path = MyApplication.getApplication().getCacheDir().getAbsolutePath() + File.separator + "data";
        //String PATH_DATA = MyApplication.getApplication().getCacheDir().getAbsolutePath() + File.separator + "data";
        //String PATH_NET_CACHE = PATH_DATA + "/netCache";
        String PATH_NET_CACHE = "";
        File netCachePath = new File(PATH_NET_CACHE);
        Cache cache = new Cache(netCachePath, 1024 * 1024 * 50);
        builder.cache(cache);
        Interceptor netCacheInterceptor = new NetCacheInterceptor();
        builder.addInterceptor(netCacheInterceptor);
        builder.addNetworkInterceptor(netCacheInterceptor);
        builder.connectTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }

    //自定义缓存拦截器
    class NetCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!ToolsUtils.isNetworkConnected()) {
                //请求的时候没有网络
                CacheControl cacheControl = new CacheControl.Builder()
                        .onlyIfCached().maxStale(60 * 60 * 24 * 7, TimeUnit.SECONDS).build();
                request = request.newBuilder().cacheControl(cacheControl).build();
            }
            Response response = chain.proceed(request);
            if(ToolsUtils.isNetworkConnected()) {
                //响应的时候有网络
                int maxAge = 0;
                response = response.newBuilder()
                        .header("Cache-Control","public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }else {
                //响应的时候无网络
                int maxStale = 60 * 60 * 24 * 14;
                response = response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();

            }
            return response;
        }
    }

    private class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }

    public static NetWorkUtils getInstance() {
        if(instance == null) {
            synchronized (NetWorkUtils.class) {
                if (instance == null) {
                    instance = new NetWorkUtils();
                }
            }
        }
        return instance;
    }


    public <T> T getApiService(Class<T> apiServiceClass, String host, boolean useGson) {
        if(useGson) {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }
        return retrofitBuilder.baseUrl(host).build().create(apiServiceClass);
    }
}
