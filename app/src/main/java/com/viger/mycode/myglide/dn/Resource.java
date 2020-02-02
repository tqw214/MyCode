package com.viger.mycode.myglide.dn;

import android.graphics.Bitmap;

public class Resource {

    private Bitmap bitmap;

    private int acquired;

    private Key key;

    private ResourceListener listener;

    public interface ResourceListener {
        void onResourceReleased(Key key, Resource resource);
    }

    public void setResourceListener(Key key, ResourceListener listener) {
        this.key = key;
        this.listener = listener;
    }

    public void recycle() {
        if(acquired > 0) {
            return;
        }
        if(!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public void release(){
        if(--acquired == 0) {
            listener.onResourceReleased(key, this);
        }
    }

    public void acquire() {
        if(bitmap.isRecycled()) {
            throw new IllegalStateException("Acquire a recycled resource");
        }
        ++acquired;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
