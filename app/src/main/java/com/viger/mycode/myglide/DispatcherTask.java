package com.viger.mycode.myglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.LinkedBlockingQueue;

public class DispatcherTask extends Thread {

    private LinkedBlockingQueue<RequestBuilder> requestQueue;
    private Handler handler;

    public DispatcherTask(LinkedBlockingQueue<RequestBuilder> requestQueue) {
        this.requestQueue = requestQueue;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        super.run();
        while(!isInterrupted()) {
            //Log.d("DispatcherTask","=======run()" + Thread.currentThread().getName());
            try {
                RequestBuilder requestBuilder = requestQueue.take();
                //设置展位图片
                placeholder(requestBuilder);
                //联网加载图片
                Bitmap bitmap = loadImage(requestBuilder);
                //设置图片
                showImageView(bitmap,requestBuilder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final Bitmap bitmap, final RequestBuilder requestBuilder) {
        if(bitmap != null && requestBuilder.getImageView() != null && requestBuilder.getImageView().getTag().equals(requestBuilder.getMd5FileName())) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageBitmap(bitmap);
                    if(requestBuilder.getListener() != null) {
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
        } catch (IOException e) {
            e.printStackTrace();
            if(requestBuilder.getListener() != null) {
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

    private void placeholder(final RequestBuilder requestBuilder) {
        if(requestBuilder.getPlaceholderId()>0 && requestBuilder.getImageView() != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageResource(requestBuilder.getPlaceholderId());
                }
            });
        }
    }
}
