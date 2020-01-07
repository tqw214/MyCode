package com.viger.mycode.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.viger.mycode.utils.Md5Utils;

import java.lang.ref.SoftReference;

public class RequestBuilder {

    private Context context;
    private String url;
    private String md5FileName;
    private int placeholder;
    private SoftReference<ImageView> imageView;

    public RequestListener getListener() {
        return listener;
    }

    private RequestListener listener;


    public RequestBuilder(Context context) {
        this.context = context;
    }

    public RequestBuilder load(String url) {
        this.url = url;
        this.md5FileName = Md5Utils.md5(url);
        return this;
    }


    public RequestBuilder placeholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public RequestBuilder listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(ImageView iv) {
        iv.setTag(md5FileName);
        this.imageView = new SoftReference<ImageView>(iv);
        if(TextUtils.isEmpty(url)) {
           // throw new GlideException("url is empty!");
        }
        if(placeholder <= 0) {

        }
        RequestManager.getInstance().addRequestQueue(this).dispatcher();
    }

    public String getUrl() {
        return url;
    }

    public String getMd5FileName() {
        return md5FileName;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

}
