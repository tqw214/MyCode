package com.viger.mycode.myglide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.viger.mycode.utils.Md5Utils;

import java.lang.ref.SoftReference;

/**
 * 每次调用产生新的对象
 */
class RequestBuilder {

    private final Context context;
    private String url;
    private String md5FileName;
    private int placeholderId;
    private SoftReference<ImageView> imageView;
    private RequestListener listener;

    public RequestListener getListener() {
        return listener;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5FileName() {
        return md5FileName;
    }

    public void setMd5FileName(String md5FileName) {
        this.md5FileName = md5FileName;
    }

    public int getPlaceholderId() {
        return placeholderId;
    }

    public void setPlaceholderId(int placeholderId) {
        this.placeholderId = placeholderId;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public RequestBuilder(Context context) {
        this.context = context;
    }

    public RequestBuilder load(String url) {
        this.url = url;
        this.md5FileName = Md5Utils.md5(url);
        return this;
    }

    public RequestBuilder placeholder(int placeholderId) {
        this.placeholderId = placeholderId;
        return this;
    }

    public RequestBuilder listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(md5FileName);
        this.imageView = new SoftReference<ImageView>(imageView);
        if(TextUtils.isEmpty(url)) {
            throw new GlideException();
        }
        if(placeholderId < 0) {

        }

        RequestManager.getInstance().addRequestQueue(this).dispatcher();

    }

}
