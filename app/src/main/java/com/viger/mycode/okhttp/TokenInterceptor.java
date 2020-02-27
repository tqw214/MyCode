package com.viger.mycode.okhttp;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * token过期处理
 * okhttp添加应用拦截器
 * 使用方式：okBuilder.addInterceptor(new TokenInterceptor()); //请求过期更换token
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Cookie", getSeesionId());
        Response response = chain.proceed(builder.build());
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        if(isTokenExpired(content)) {
            ////如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
            String newToken = getNewToken();
            setSeesionId(newToken);
            //使用新的Token，创建新的请求
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Cookie", newToken)
                    .build();
            return chain.proceed(newRequest);
        }
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    private String getNewToken() {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
//        IndexService service = IndexService.Builder.getServer();
//        Call<BaseObjResult<UserBean>> call = service.getToke(
//                UserInfo.getInstance().getPhone(),
//                UserInfo.getInstance().getPwd(),
//                0);

        //要用retrofit的同步方式
//        BaseObjResult<UserBean> newToken = null;
//        try {
//            newToken = call.execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return newToken.getResult().getPHPSESSID();
        return "";
    }

    private String getSeesionId() {
        return "";
    }

    private void setSeesionId(String mes) {

    }

    private boolean isTokenExpired(String resultStr) {
        RequestCode requestCode = new Gson().fromJson(resultStr, RequestCode.class);
        //err==3  token过期
        if (requestCode.getErr() == 3) {
            //LogUtils.e("Token登录过期了");
            //ToastUtils.showShortSafe("Token登录过期了");
            return true;
        }
        return false;
    }

    class RequestCode {
        private int err;
        private String msg;

        public int getErr() {
            return err;
        }

        public void setErr(int err) {
            this.err = err;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
