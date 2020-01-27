package com.viger.binderclient.retrofitutils;

import android.content.Context;
import retrofit2.Call;

public class ItheimaHttp {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setCache(boolean cache) {
        HttpHelper.setHttpCache(cache);
    }

    public static final void init(Context context, String httpBaseUrl) {
        mContext = context.getApplicationContext();
        HttpHelper.setBaseUrl(httpBaseUrl);
    }

    //get
    public static <T> Call getAsync(String apiUrl, final HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.getAsync(apiUrl,null,null, httpResponseListener);
    }

    //post
    public static <T> Call postAsync(String apiUrl, final HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.postAsync(apiUrl,null,null,  httpResponseListener);
    }

    public static <T> Call send(Request request, final HttpResponseListener<T> httpResponseListener) {
        if(RequestMethod.GET.equals(request.getRequestMethod())) {
            return HttpHelper.getAsync(request.getApiUlr(),request.getHeaderMap(),request.getParamsMap(), httpResponseListener);
        }else {
            return HttpHelper.postAsync(request.getApiUlr(),request.getHeaderMap(),request.getParamsMap(), httpResponseListener);
        }
    }

    public static <T> okhttp3.Call upload(Request request, UploadListener uploadListener) {
        return HttpHelper.upload(request, uploadListener);
    }

    public static Request newRequest(String apiUlr, RequestMethod method) {
        return new Request(apiUlr, method);
    }

    public static Request newPostRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.POST);
    }

    public static Request newUploadRequest(String uploadFileUrl, RequestMethod method) {
        return new Request(uploadFileUrl, method);
    }

    public static Request newGetRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.GET);
    }

    /**
     * 是否显示日志，默认不现实 true:显示
     *
     * @param isDebug
     */
    public static void setDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }


}
