package com.viger.customview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

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

    //得到缩放系数
    public float getScaleWidth() {
        return displayMetricsWidth/standard_width;
    }

    public float getScaleHeight() {
        return displayMetricsHeight/standard_height;
    }


}
