package com.viger.binderclient;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyViewGroup1 extends LinearLayout {
    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("tag", "MyViewGroup1:dispatchTouchEvent:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("tag", "MyViewGroup1:onInterceptTouchEvent:" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", "MyViewGroup1:onTouchEvent:" + event.getAction());
        return super.onTouchEvent(event);
    }
}
