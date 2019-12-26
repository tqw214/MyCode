package com.viger.mycode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.viger.mycode.R;

public class MyTextView extends View {

    private String mText;
    private int mTextColor = Color.BLACK;
    private int mTextSize = 15;

    //代码中new
    public MyTextView(Context context) {
        this(context, null);
    }

    //布局中使用
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mText = typedArray.getString(R.styleable.MyTextView_text);
        mTextColor = typedArray.getColor(R.styleable.MyTextView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_textSize, mTextSize);


        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int resultWidthSize = 100;
        int resultHeightSize = 100;

        if(widthMode == MeasureSpec.AT_MOST) {

        }
        if(widthMode == MeasureSpec.EXACTLY) {
            resultWidthSize = widthSize;
        }

        if(heightMode == MeasureSpec.AT_MOST) {

        }
        if(heightMode == MeasureSpec.EXACTLY) {
            resultHeightSize = heightSize;
        }
        setMeasuredDimension(resultWidthSize, resultHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("tag","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("tag","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("tag","ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
