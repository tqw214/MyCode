package com.viger.customview.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 获取屏幕适配的缩放系数
 */
public class ScreenUtils {

    private static ScreenUtils instance = null;

    private static final int standard_width = 720;
    private static final int standard_height = 1280;

    private int displayMetricsWidth;
    private int displayMetricsHeight;

    private ScreenUtils(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        Log.d("tag","displayMetricsWidth="+displayMetricsWidth);
        Log.d("tag","displayMetricsHeight="+displayMetricsHeight);
        int systemBarHeight = getValue(context, "system_bar_height", 48);
        if(displayMetrics.widthPixels > displayMetrics.heightPixels) {
            displayMetricsWidth = displayMetrics.heightPixels;
            displayMetricsHeight = displayMetrics.widthPixels - systemBarHeight;
        }else {
            displayMetricsWidth = displayMetrics.widthPixels;
            displayMetricsHeight = displayMetrics.heightPixels - systemBarHeight;
        }
    }

    private int getValue(Context context, String name, int defaultValue) {
        try {
            Class clazz = Class.forName("com.android.internal.R$Dimen");
            Object o = clazz.newInstance();
            Field field = clazz.getField(name);
            field.setAccessible(true);
            int x = (int) field.get(o);
            return context.getResources().getDimensionPixelSize(x);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ScreenUtils getInstance(Context context) {
        if(instance == null) {
            synchronized (ScreenUtils.class) {
                if(instance == null) {
                    instance = new ScreenUtils(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    //得到缩放系数
    public float getScaleWidth() {
        return displayMetricsWidth/standard_width;
    }

    public float getScaleHeight() {
        return displayMetricsHeight/standard_height;
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * Dip into pixels
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Pixels converted into a dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
