package com.viger.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.viger.customview.R;


public class MyTextView extends View {

    private String mText;
    private int mTextColor = Color.BLACK;
    private int mTextSize = 15;
    private Paint mPaint;

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
        mText = typedArray.getString(R.styleable.MyTextView_MyTextView_text);
        mTextColor = typedArray.getColor(R.styleable.MyTextView_MyTextView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_MyTextView_textSize, mTextSize);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(mTextSize));
        mPaint.setColor(mTextColor);

        //setBackgroundColor(Color.TRANSPARENT);
        //setWillNotDraw(false);

    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int resultWidthSize = widthSize;
        int resultHeightSize = heightSize;

        //测量文字
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText,0,mText.length(),bounds);

        if(widthMode == MeasureSpec.AT_MOST) {
            resultWidthSize = bounds.width();
        }

        if(heightMode == MeasureSpec.AT_MOST) {
            resultHeightSize = bounds.height();
        }

        //处理padding
        resultWidthSize += (getPaddingLeft() + getPaddingRight());
        resultHeightSize += (getPaddingTop() + getPaddingBottom());

        setMeasuredDimension(resultWidthSize, resultHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float baseLine = getHeight()/2 + getBaselineToCenterDistance(mPaint);
        canvas.drawText(mText,getPaddingLeft(),baseLine,mPaint);
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     * @param p
     * @return 基线和centerY的距离
     */
    public static float getBaselineToCenterDistance(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
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
