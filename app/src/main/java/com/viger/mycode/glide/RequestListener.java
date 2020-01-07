package com.viger.mycode.glide;

import android.graphics.Bitmap;

public interface RequestListener {
    void onResourceReady(Bitmap bitmap);
    void onLoadFailed(GlideException e);
}
