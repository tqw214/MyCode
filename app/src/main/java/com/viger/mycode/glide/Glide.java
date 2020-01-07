package com.viger.mycode.glide;

import android.content.Context;

public class Glide {
    public static RequestManager with(Context context) {
        return RequestManager.getInstance().get(context);
    }
}
