package com.viger.customview.view05;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.viger.customview.R;

public class RatingBar extends View {

    private static final String TAG = "RatingBar";

    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int mGradeNumber = 5;

    private int mCurrentGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal ");
        }

        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starFocus ");
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);

        mGradeNumber = array.getInt(R.styleable.RatingBar_gradeNumber, mGradeNumber);

        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 高度  一张图片的高度， 自己去实现 padding  + 加上间隔
        int padding = getPaddingLeft();
        int height = mStarFocusBitmap.getHeight() + padding * 2;
        int width = mStarFocusBitmap.getWidth() * mGradeNumber + padding * (mGradeNumber+1);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int x1 = (int) event.getX();
                int y1 = (int) event.getY();
                if(x1>0 && x1 < getWidth() && y1>0 && y1<getHeight()) {
                    int currentGrade = (int) (x1/(mStarNormalBitmap.getWidth()+ getPaddingLeft()) + 1);
                    if(currentGrade == mCurrentGrade){
                        return true;
                    }
                    // 再去刷新显示
                    mCurrentGrade = currentGrade;
                    invalidate();// onDraw()  尽量减少onDraw()的调用，目前是不断调用，怎么减少？
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int currentGrade = (int) (x/(mStarNormalBitmap.getWidth()+ getPaddingLeft()) + 1);
                // 范围问题
                if(currentGrade<0){
                    currentGrade = 0;
                }
                if(currentGrade>mGradeNumber){
                    currentGrade = mGradeNumber;
                }
                // 分数相同的情况下不要绘制了 , 尽量减少onDraw()的调用
                if(currentGrade == mCurrentGrade){
                    return true;
                }
                // 再去刷新显示
                mCurrentGrade = currentGrade;
                invalidate();// onDraw()  尽量减少onDraw()的调用，目前是不断调用，怎么减少？
                break;
            case MotionEvent.ACTION_UP: {
                if(onRatingBarCallBack != null) {
                    onRatingBarCallBack.value(mCurrentGrade);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<mGradeNumber;i++) {

            int left = i * (mStarNormalBitmap.getWidth()) + (i+1) * getPaddingLeft();
            if(mCurrentGrade > i) {
                canvas.drawBitmap(mStarFocusBitmap, left, getPaddingTop(), null);
            }else {
                canvas.drawBitmap(mStarNormalBitmap, left, getPaddingTop(), null);
            }

        }
    }

    private OnRatingBarCallBack onRatingBarCallBack;

    public void setOnRatingBarCallBack(OnRatingBarCallBack callBack) {
        this.onRatingBarCallBack = callBack;
    }

    public interface OnRatingBarCallBack {
        void value(int starNumber);
    }

}
