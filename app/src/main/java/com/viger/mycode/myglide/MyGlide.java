package com.viger.mycode.myglide;

import android.content.Context;

public class MyGlide {

    //返回的RequestManager为单例
    public static RequestManager with(Context context) {
        return RequestManager.getInstance().get(context);
    }
}
