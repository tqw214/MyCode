package com.viger.mycode.okhttp;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUploadFile {
    /**
     * @param url
     * @param pathList
     * @throws Exception
     */
    public static void upLoadFiles(final String url, final String signPath, final Map<String, Object> params,
                                   final List<String> pathList) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            try {
                MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");
                RequestBody bodyParams = RequestBody.create(MutilPart_Form_Data, new Gson().toJson(params));
                MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("keyVo", "", bodyParams);
                //循环添加文件
                for (int i = 0; i < pathList.size(); i++) {
                    File file = new File(pathList.get(i));
                    requestBodyBuilder.addFormDataPart("imgs", file.getName(), RequestBody.create(MutilPart_Form_Data,
                            new File(pathList.get(i))));
                }
                RequestBody requestBody = requestBodyBuilder.build();
                Request request = new Request.Builder()
                        .url( url)
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                //myCallBack.onSuccess(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
                //myCallBack.onError(new UnknownServiceException("服务器错误"), false);
            }
        }

        public void downloadFile() {
            Request request = new Request.Builder().url("").build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("myTag", "下载失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        writeFile(response);
                    }
                }
            });
        }

    private void writeFile(Response response) {
        InputStream is = null;
        FileOutputStream fos = null;
        is = response.body().byteStream();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "fileName");
        try {
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            //获取下载的文件的大小
            long fileSize = response.body().contentLength();
            long sum = 0;
            int porSize = 0;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes);
                sum += len;
                porSize = (int) ((sum * 1.0f / fileSize) * 100);
                Message message = handler.obtainMessage(1);
                message.arg1 = porSize;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //pro.setProgress(msg.arg1);
            }
        }
    };

}
