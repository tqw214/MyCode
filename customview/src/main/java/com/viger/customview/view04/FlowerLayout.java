package com.viger.customview.view04;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowerLayout extends ViewGroup {

    private List<Integer> listLineHeight;
    private List<List<View>> listLineView;

    public static class LineView {
        private int top;
        private int left;
        private int right;
        private int bottom;

        public LineView(int t, int l, int r, int b) {
            this.top = t;
            this.left = l;
            this.right = r;
            this.bottom = b;
        }
    }

    public FlowerLayout(Context context) {
        this(context,null);
    }

    public FlowerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        listLineHeight = new ArrayList<Integer>();
        listLineView = new ArrayList<List<View>>();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidthSieze = 0;
        int resultHeightSize = 0;

        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            resultWidthSieze = widthSize;
            resultHeightSize = heightSize;
        }else {

            int childCount = getChildCount();

            int currentLineWidth = 0;
            int currentLineHeight = 0;

            if(widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {

                //等于所有子view的宽
                //每一行的viwe

                List<View> lineView = new ArrayList<View>();

                for(int i=0;i<childCount;i++) {

                    View child = getChildAt(i);
                    measureChild(child, widthMeasureSpec, heightMeasureSpec);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                    int leftMargin = params.leftMargin;
                    int rightMargin = params.rightMargin;
                    int topMargin = params.topMargin;
                    int bottomMargin = params.bottomMargin;

                    int childWidth = child.getMeasuredWidth() + leftMargin + rightMargin;
                    int childHeight = child.getMeasuredHeight() + topMargin + bottomMargin;

                    if(currentLineWidth + childWidth > widthSize) {
                        resultWidthSieze = Math.max(resultWidthSieze, currentLineWidth);
                        resultHeightSize += currentLineHeight;

                        listLineHeight.add(currentLineHeight);
                        listLineView.add(lineView);

                        currentLineWidth = childWidth;
                        currentLineHeight = childHeight;

                        lineView = new ArrayList<View>();
                        lineView.add(child);


                    }else {
                        currentLineWidth += childWidth;
                        currentLineHeight = Math.max(currentLineHeight, childHeight);
                        lineView.add(child);

                    }

                }
            }
        }
        setMeasuredDimension(resultWidthSieze, resultHeightSize);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left,top,right,bottom;
        int currentLef = 0;
        int currentTop = 0;
        for(int i=0;i<listLineView.size();i++) {
            List<View> lineView = listLineView.get(i);
            for(int j=0;j<lineView.size();j++) {
                View view = lineView.get(j);
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                left = currentLef + params.leftMargin;
                top = currentTop + params.topMargin;
                right = left + view.getMeasuredWidth() + params.rightMargin;
                bottom = top + view.getMeasuredHeight() + params.bottomMargin;
                view.layout(left, top, right, bottom);
                currentLef += view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            }
            currentLef = 0;
            currentTop += listLineHeight.get(i);
        }
        listLineView.clear();
    }
}
