package com.viger.mycode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.ScrollView;

public class MyListView extends ListView {

    private ScrollView scrollView;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
