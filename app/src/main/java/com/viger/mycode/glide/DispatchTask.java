package com.viger.mycode.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.LinkedBlockingQueue;

public class DispatchTask extends Thread {

    private LinkedBlockingQueue<RequestBuilder> queue;
    private Handler handler;

    public DispatchTask(LinkedBlockingQueue<RequestBuilder> queue) {
        this.queue = queue;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {

        while(!Thread.interrupted()) {

            try {
                Thread.sleep(5000);

                Log.d("tag"," run() ==== > name : " + Thread.currentThread().getName());

                RequestBuilder requestBuilder = queue.take();
                placeHolder(requestBuilder);
                //联网加载图片
                Bitmap bitmap = loadImage(requestBuilder);
                //设置图片
                showImageView(bitmap, requestBuilder);


            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void showImageView(Bitmap bitmap, RequestBuilder requestBuilder) {
        if(bitmap != null && requestBuilder.getImageView()!= null && requestBuilder.getMd5FileName().equals(requestBuilder.getImageView().getTag())) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageBitmap(bitmap);
                    if(requestBuilder.getListener()!= null) {
                        requestBuilder.getListener().onResourceReady(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap loadImage(RequestBuilder requestBuilder) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(requestBuilder.getUrl());
            URLConnection urlConnection = url.openConnection();
            is = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            if(requestBuilder.getListener()!= null) {
                requestBuilder.getListener().onLoadFailed(new GlideException(e.getMessage()));
            }
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    private void placeHolder(RequestBuilder requestBuilder) {
        if(requestBuilder.getPlaceholder() > 0 && requestBuilder.getImageView() != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageResource(requestBuilder.getPlaceholder());
                }
            });
        }
    }
}
