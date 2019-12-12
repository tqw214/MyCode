package com.viger.mycode.myglide;

import android.graphics.Bitmap;

public interface RequestListener {

    void onResourceReady(Bitmap bitmap);

    void onLoadFailed(GlideException e);

}
