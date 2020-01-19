package com.viger.customview.view05;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.viger.customview.R;
import com.viger.customview.utils.DisplayUtils;

public class MyProgressBar extends View {

    private int mInnerBackground = Color.RED;
    private int mOuterBackground = Color.RED;
    private int mRoundWidth = 10;// 10px
    private int mProgressTextSize = 15;
    private int mProgressTextColor = Color.RED;

    private Paint mInnerPaint, mOuterPaint, mTextPaint;

    private int mMax = 100;
    private int mProgress = 0;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        mInnerBackground = array.getColor(R.styleable.MyProgressBar_innerBackground, mInnerBackground);
        mOuterBackground = array.getColor(R.styleable.MyProgressBar_outerBackground, mOuterBackground);
        mRoundWidth = (int) array.getDimension(R.styleable.MyProgressBar_roundWidth, DisplayUtils.dp2px(context,10));
        mProgressTextSize = array.getDimensionPixelSize(R.styleable.MyProgressBar_progressTextSize,
                DisplayUtils.sp2px(context,mProgressTextSize));
        mProgressTextColor = array.getColor(R.styleable.MyProgressBar_progressTextColor, mProgressTextColor);
        array.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackground);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackground);
        mOuterPaint.setStrokeWidth(mRoundWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int resultSize = Math.min(widthSize, heightSize);
        setMeasuredDimension(resultSize/2, resultSize/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画内园
        int centerX = getMeasuredWidth()/2;
        int centerY = getMeasuredHeight()/2;
        canvas.drawCircle(centerX, centerY, centerX-mRoundWidth/2, mInnerPaint);

        if (mProgress == 0) {
            return;
        }

        float percent = (float)mProgress / mMax;

        //画外圆圆弧
        RectF rectF = new RectF(mRoundWidth/2, mRoundWidth/2, getMeasuredWidth()-mRoundWidth/2, getMeasuredHeight()-mRoundWidth/2);
        canvas.drawArc(rectF, 0, percent * 360, false, mOuterPaint);

        //画进度文字
        String text = (int)(percent * 100) + "%";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetricsInt metrics = mTextPaint.getFontMetricsInt();
        //int dy = (metrics.descent - metrics.ascent)/2 - metrics.descent;
        int dy = (metrics.bottom - metrics.top) / 2 - metrics.bottom;
        int baseLineY = dy + getMeasuredHeight()/2;
        canvas.drawText(text, getMeasuredWidth()/2-rect.width()/2, baseLineY, mTextPaint);

    }

    // 给几个方法
    public synchronized void setMax(int max) {
        if (max < 0) {
            max = 100;
        }
        this.mMax = max;
    }

    public synchronized void setProgress(int progress) {
//        if (progress < 0) {
//            progress = 0;
//        }
//        this.mProgress = progress;
//        // 刷新 invalidate
//        invalidate();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, progress);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mProgress = (int) animatedValue;
                invalidate();
            }
        });
        valueAnimator.start();
    }

}
