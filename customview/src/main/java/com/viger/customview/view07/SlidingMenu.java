package com.viger.customview.view07;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.viger.customview.R;
import com.viger.customview.utils.DisplayUtils;

public class SlidingMenu extends HorizontalScrollView {

    // 菜单的宽度
    private int mMenuWidth;

    private View mContentView,mMenuView;

    private GestureDetector gestureDetector;

    private ViewDragHelper viewDragHelper;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedValue = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightMargin = typedValue.getDimension(R.styleable.SlidingMenu_menuRightMargin, DisplayUtils.dp2px(context, 50));
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        typedValue.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childcount = container.getChildCount();
        if(childcount != 2 ) {
            throw new RuntimeException("只能放置两个子View!");
        }
        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams layoutParams = mMenuView.getLayoutParams();
        layoutParams.width = mMenuWidth;
        mMenuView.setLayoutParams(layoutParams);
        // 2.菜单页的宽度是 屏幕的宽度 - 右边的一小部分距离（自定义属性）
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams layoutParams1 = mContentView.getLayoutParams();
        layoutParams1.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(layoutParams1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        scrollTo(mMenuWidth, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / mMenuWidth;
        //Log.e("TAG", "scale -> " + scale);
        float rightScale = 0.7f + 0.3f * scale;
        ViewCompat.setPivotX(mContentView,0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView,rightScale);
        ViewCompat.setScaleY(mContentView,rightScale);

        // 菜单的缩放和透明度
        // 透明度是 半透明到完全透明  0.5f - 1.0f
        float leftAlpha = 0.5f + (1-scale) * 0.5f;
        ViewCompat.setAlpha(mMenuView,leftAlpha);
        // 缩放 0.7f - 1.0f
        float leftScale = 0.7f + (1-scale)*0.3f;
        ViewCompat.setScaleX(mMenuView,leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);
        ViewCompat.setTranslationX(mMenuView, 0.25f*l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        //Log.d("tag","getScrollX = " + getScrollX());
        if(action == MotionEvent.ACTION_UP) {
            int scrollX = getScrollX();
            if(scrollX > mMenuWidth/2) {
                closeMenu();
            }else {
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(0,0);
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth,0);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * Dip into pixels
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
