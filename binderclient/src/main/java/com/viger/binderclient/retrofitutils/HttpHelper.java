package com.viger.binderclient.retrofitutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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
        mRetrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addCallAdapterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createOkhttpAndCache() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(mIsCache) {
            Cache cache = new Cache(mCacheFile, 1024*1024*10);
            //添加网络过滤 & 实现网络数据缓存
            Interceptor interceptor = createHttpInterceptor();
            builder.addInterceptor(interceptor);
            builder.addNetworkInterceptor(interceptor);
            builder.cache(cache);
        }
        //设置超时
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    public static HttpHelper getInstance() {
        if(sInstance == null || sInstance.get() == null) {
            synchronized (HttpHelper.class) {
                if(sInstance == null || sInstance.get() == null) {
                    sInstance = new WeakReference<HttpHelper>(new HttpHelper());
                }
            }
        }
        return sInstance.get();
    }

    //创建网络过滤对象，添加缓存
    private Interceptor createHttpInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                boolean isNetwork = isNetworkConnected(ItheimaHttp.getContext());
                if(!isNetwork) {
                    //没有网络的时候使用缓存
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if(isNetwork) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control","public,max-age="+ maxAge)
                            .removeHeader("Pragma")
                            .build();
                }else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
    }

    //判断是否有网络连接
    private boolean isNetworkConnected(Context context) {
        if(context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if(activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
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

    public static <T> Call getAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap, final HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        HttpService httpService = getInstance().mRetrofit.create(HttpService.class);
        Call<ResponseBody> call = httpService.get(apiUrl, headers, paramMap);
        parseNetData(call, httpResponseListener);
        return call;
    }

    private static <T> void parseNetData(Call<ResponseBody> call, final HttpResponseListener<T> httpResponseListener) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    if(L.isDebug) {
                        L.i("response data:" + json);
                    }
                    if(!String.class.equals(httpResponseListener.getType())) {
                        Gson gson = new Gson();
                        T t = gson.fromJson(json, httpResponseListener.getType());
                        httpResponseListener.onResponse(t, response.headers());
                    }else {
                        httpResponseListener.onResponse((T)json, response.headers());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (L.isDebug) {
                        L.e("Http Exception:", e);
                    }
                    httpResponseListener.onFailure(call, e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                httpResponseListener.onFailure(call, t);
            }
        });
    }

    public static <T> Call postAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap, HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        HttpService httpService = getInstance().mRetrofit.create(HttpService.class);
        Call<ResponseBody> call = httpService.post(apiUrl, headers, paramMap);
        parseNetData(call, httpResponseListener);
        return call;
    }


    public static okhttp3.Call upload(Request request, final UploadListener uploadListener) {
        if (request.getUploadFiles() == null || request.getUploadFiles().size() == 0) {
            /*new FileNotFoundException("file does not exist(文件不存在)").printStackTrace();*/
            new FileNotFoundException("please add upload file").printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                //拦截
                okhttp3.Response response = chain.proceed(chain.request());
                //包装响应体并返回
                return response.newBuilder()
                        .body(new ProgressResponseBody(response.body(), uploadListener))
                        .build();
            }
        }).build();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody = null;
        //第一个参数是服务获取文件的key,第二个参数文件的名称,第三个参数上传到服务器文件以流的形式
        if (request.getUploadFiles() != null) {
            for (String key : request.getUploadFiles().keySet()) {
                multipartBodyBuilder.addFormDataPart(key
                        , request.getUploadFiles().get(key).getName()
                        , RequestBody.create(request.getMediaType(), request.getUploadFiles().get(key)));
            }
        }
        if (request.getParamsMap() != null) {
            for (String key : request.getParamsMap().keySet()) {
                multipartBodyBuilder.addFormDataPart(key, request.getParamsMap().get(key).toString());
            }
        }

        requestBody = multipartBodyBuilder.build();
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, uploadListener);
        okhttp3.Request.Builder methodBuilder = new okhttp3.Request.Builder().method(request.getRequestMethod().getValue(), progressRequestBody);
        if (request.getHeaderMap() != null) {
            for (String key : request.getHeaderMap().keySet()) {
                methodBuilder.addHeader(key, request.getHeaderMap().get(key).toString());
            }
        }

        okhttp3.Request okHttpRequest = methodBuilder.url(request.getApiUlr()).build();
        okhttp3.Call call = okHttpClient.newCall(okHttpRequest);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                uploadListener.onFailure(call, e);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                uploadListener.onResponse(call, response);
            }
        });
        return call;
    }

    public static interface HttpService<T> {
        @GET
        Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String,Object> params);

        @FormUrlEncoded
        @POST
        Call<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

        @Multipart
        @POST
        Call<String> postUpload(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params, @PartMap Map<String, RequestBody> part);

        @Multipart
        @GET
        Call<String> getUpload(@Url String url, @HeaderMap Map<String, Object> headers, @FieldMap Map<String, Object> paramsField,/*@Part("filedes") String des,*/ @PartMap Map<String, RequestBody> params);
    }
}
