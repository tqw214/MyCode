package com.viger.customview.view;

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
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.viger.customview.R;
import com.viger.customview.utils.DisplayUtils;

public class QQStepView extends View {

    private int borderWidth;
    private int innerColor;
    private int outerColor;
    private int stepTextColor;
    private int stepTextSize;
    private int defaultWidthAndHeight = 80;


    // 总共的，当前的步数
    private int mStepMax = 0;
    public int mCurrentStep = 0;

    private Paint mOutPaint, mInnerPaint, mTextPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        borderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth, 6);
        borderWidth = DisplayUtils.dp2px(context, borderWidth);
        innerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, Color.RED);
        outerColor = typedArray.getColor(R.styleable.QQStepView_outerColor, Color.BLUE);
        stepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, Color.GREEN);
        stepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, 16);
        stepTextSize = DisplayUtils.sp2px(context, stepTextSize);
        typedArray.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(borderWidth);
        mOutPaint.setColor(outerColor);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(borderWidth);
        mInnerPaint.setColor(innerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(stepTextColor);
        mTextPaint.setTextSize(stepTextSize);

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

        if (widthMode == MeasureSpec.AT_MOST) {
            resultWidthSize = defaultWidthAndHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            resultHeightSize = defaultWidthAndHeight;
        }
        int minSize = Math.min(resultWidthSize, resultHeightSize);
        setMeasuredDimension(minSize, minSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画底层圆弧
        RectF oval = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
        canvas.drawArc(oval, 135, 270, false, mOutPaint);
        //画上层百分比圆弧
        if (mStepMax == 0) return;
        float angle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(oval, 135, angle * 270, false, mInnerPaint);
        //画文字
        String text = mCurrentStep + "";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.descent - fontMetrics.ascent)/2 - fontMetrics.descent);
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, baseLine, mTextPaint);

    }

    public int getStepMax() {
        return mStepMax;
    }

    public void setStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public int getCurrentStep() {
        return mCurrentStep;
    }

    public void setCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        invalidate();
    }

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mCurrentStep);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                setCurrentStep((int) animatedValue);
            }
        });
        valueAnimator.start();
    }

}
