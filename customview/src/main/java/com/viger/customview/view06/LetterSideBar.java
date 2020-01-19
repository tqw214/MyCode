package com.viger.customview.view06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.viger.customview.utils.DisplayUtils;

public class LetterSideBar extends View {

    private Paint mPaint;

    private static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    // 当前触摸的位置字母
    private String mCurrentTouchLetter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(DisplayUtils.sp2px(context, 12));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int textWidth = (int) mPaint.measureText("A");
        int width = textWidth + getPaddingRight() + getPaddingLeft();
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/mLetters.length;
        for(int i=0;i<mLetters.length;i++) {
            int centerY = i * itemHeight + itemHeight/2 + getPaddingTop();
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int base = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
            int dy = centerY + base;
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int centerX = getWidth()/2-textWidth/2;
            canvas.drawText(mLetters[i], centerX, dy , mPaint);
            if(mLetters[i].equals(mCurrentTouchLetter)) {
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetters[i], centerX, dy , mPaint);
            }else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(mLetters[i], centerX, dy , mPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                checkLetterIsChoose(y);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.parseColor("#eeeeee"));
//                if (mListener != null) {
//                    mListener.touch(mCurrentTouchLetter, false);
//                }
                mCurrentTouchLetter = "";
                invalidate();
                break;
        }
        return true;
    }

    private void checkLetterIsChoose(float y) {
        int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/mLetters.length;
            int currentPosition = (int) (y / itemHeight);
            if (currentPosition < 0)
                currentPosition = 0;
            if (currentPosition > mLetters.length - 1)
                currentPosition = mLetters.length - 1;
            mCurrentTouchLetter = mLetters[currentPosition];
            if (mListener != null) {
                mListener.touch(mCurrentTouchLetter, true);
            }
            // 重新绘制
            invalidate();
    }



    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener listener) {
        this.mListener = listener;
    }

    // 接口回掉其他View会不会使用？
    public interface LetterTouchListener {
        void touch(CharSequence letter, boolean isTouch);
    }

}
