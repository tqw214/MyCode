package com.viger.customview.view04;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowerLayout extends ViewGroup {

    private ArrayList<View> mViews;

    public FlowerLayout(Context context) {
        this(context,null);
    }

    public FlowerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViews = new ArrayList<View>();
    }

    public void addView(View view) {
        mViews.add(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
