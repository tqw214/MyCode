package com.viger.mycode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View {

    private Paint paint;

    public MyCustomView(Context context) {
        this(context, null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(100,100,200,200, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth()/2,getHeight()/2,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,150,getResources().getDisplayMetrics()),
                paint);

    }
}
