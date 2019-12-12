package com.viger.mycode.myglide;

import android.content.Context;

import com.viger.mycode.utils.Md5Utils;

/**
 * 每次调用产生新的对象
 */
class RequestBuilder {

    private final Context context;
    private String url;
    private String md5FileName;

    public RequestBuilder(Context context) {
        this.context = context;
    }

    public RequestBuilder load(String url) {
        this.url = url;
        this.md5FileName = Md5Utils.md5(url);
        return this;
    }
}
