package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * @author lgh on 2020/5/28 17:05
 * @description
 */
public class CustomLayout extends HorizontalScrollView {
    public CustomLayout(Context context) {
        this(context, null);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //未处理padding、margin、layoutparam
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measuerHeight = 0;
        final int count = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//等价于getDefaultSize(0, widthMeasureSpec)
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);//等价于getDefaultSize(0, heightMeasureSpec)
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (count == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            final View childAt = getChildAt(0);
            measureWidth = childAt.getMeasuredWidth() * count;
            measuerHeight = childAt.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measuerHeight);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childAt = getChildAt(0);
            measuerHeight = childAt.getMeasuredHeight();
            setMeasuredDimension(widthSpecSize, measuerHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childAt = getChildAt(0);
            measureWidth = childAt.getMeasuredWidth();
            setMeasuredDimension(measureWidth, heightSpecSize);
        }
    }
}
