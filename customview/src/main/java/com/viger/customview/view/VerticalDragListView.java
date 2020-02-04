package com.viger.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper mDragHelper;

    private View mDragListView;

    // 后面菜单的高度
    private int mMenuHeight;
    // 菜单是否打开
    private boolean mMenuIsOpen = false;

    public VerticalDragListView(Context context) {
        this(context, null);
    }

    public VerticalDragListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, mDragHelperCallback);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            View menuView = getChildAt(0);
            mMenuHeight = menuView.getMeasuredHeight();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("VerticalDragListView 只能包含两个子布局");
        }
        mDragListView = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", String.valueOf(event.getY()));
        mDragHelper.processTouchEvent(event);
        return true;
    }


    private int downY;

    /**
     * 需要拦截的情况：打开或者关闭滑动 true
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(mMenuIsOpen) {
            return true;
        }
        ListView listView = (ListView) mDragListView;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                mDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();

                //向下，到顶，关闭
                if(!mMenuIsOpen && (moveY - downY) > 0 && isTop(listView)) {
                    return true;
                }
                break;
//                if(mMenuIsOpen) {
//                    //打开的情况往下滑动
//                    if((moveY - downY) > 0) {
//                        if(isTop(listView)) {
//                            return false;
//                        }else {
//                            return true;
//                        }
//                    }else {
//                        //打开的情况往上滑动
//                        return true;
//                    }
//                }else {
//                    //关闭的情况往下滑动
//                    if((moveY - downY) > 0) {
//                        if(isTop(listView)) {
//                            return true;
//                        }else {
//                            return false;
//                        }
//                    }else {
//                        //关闭的情况往上滑动
//                        return false;
//                    }
//                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }
    }

    private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mDragListView;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if(top < 0) {
                top = 0;
            }
            if(top > mMenuHeight) {
                top = mMenuHeight;
            }
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if(releasedChild == mDragListView) {
                if(mDragListView.getTop() > mMenuHeight/2) {
                    // 滚动到菜单的高度（打开）
                    mDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mMenuIsOpen = true;
                }else {
                    // 滚动到0的位置（关闭）
                    mDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }
        }
    };

    public boolean isBottom(final ListView listView) {
        boolean result=false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result= (listView.getHeight()>=bottomChildView.getBottom());
        };
        return  result;
    }

    public boolean isTop(final ListView listView) {
        boolean result=false;
        if(listView.getFirstVisiblePosition()==0){
            final View topChildView = listView.getChildAt(0);
            result=topChildView.getTop()==0;
        }
        return result ;
    }

}
