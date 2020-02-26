package com.viger.mycode.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class UIAdapter {
    private static volatile  UIAdapter instance = null;
    //设计师的参考尺寸
    private static final float defaultWidth = 1080;
    private static final float defaultHeight = 1920;
    //屏幕的真实尺寸
    private int screenWidth;
    private int screenHeight;
    private UIAdapter(){

    }
    public static UIAdapter getInstance(){
        if(null==instance){
            synchronized (UIAdapter.class){
                if(null==instance){
                    instance = new UIAdapter();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        if(null==context){
            return;
        }
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        if(displayMetrics.widthPixels>displayMetrics.heightPixels){//横屏
            screenWidth = displayMetrics.heightPixels;
            screenHeight = displayMetrics.widthPixels;
        }else{
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels-getStatusBarHeight(context);
        }
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    public  float scaleX(){
        return screenWidth/defaultWidth;
    }
    public  float scaleY(){
        return screenHeight/defaultHeight;
    }
    public void scaleView(View v, int w, int h, int l, int t, int r, int b) {
        if(v==null){
            return;
        }
        w = (int) (w*scaleX());
        h = (int) (h*scaleY());
        l = (int) (l*scaleX());
        t = (int) (t*scaleY());
        r = (int) (r*scaleX());
        b = (int) (b*scaleY());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        if (params != null) {
            params.width = w;
            params.height = h;
            params.setMargins(l, t, r, b);
        }
    }




}
