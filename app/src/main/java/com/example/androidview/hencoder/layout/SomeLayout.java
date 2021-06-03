package com.example.androidview.hencoder.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author lgh on 2020/12/9 15:18
 * @description
 */
public class SomeLayout extends ViewGroup {

    public SomeLayout(Context context) {
        this(context, null);
    }

    public SomeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SomeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int usedWidth = 0;
        int usedHeight = 0;
        int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int selfHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int widthSpec;
            int heightSpec;
            /**
             * lp.width 与 lp.height 对应xml中的layout_width 和 layout_height
             */
            LayoutParams lp = childView.getLayoutParams();
            switch (lp.width) {
                case MATCH_PARENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        widthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth, MeasureSpec.EXACTLY);
                    } else {
                        widthSpec = MeasureSpec.makeMeasureSpec(0, UNSPECIFIED);
                    }

                    break;
                case WRAP_CONTENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        widthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth, MeasureSpec.AT_MOST);
                    } else {
                        widthSpec = MeasureSpec.makeMeasureSpec(0, UNSPECIFIED);
                    }
                    break;

                default:
                    widthSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
                    break;
            }
            usedWidth += widthSpec;

            switch (lp.height) {
                case MATCH_PARENT:
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                        heightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedWidth, MeasureSpec.EXACTLY);
                    } else {
                        heightSpec = MeasureSpec.makeMeasureSpec(0, UNSPECIFIED);
                    }
                    break;
                case WRAP_CONTENT:
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                        heightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedHeight, MeasureSpec.AT_MOST);
                    } else {
                        heightSpec = MeasureSpec.makeMeasureSpec(0, UNSPECIFIED);
                    }
                    break;

                default:
                    heightSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
                    break;
            }
            usedHeight += heightSpec;
        }
        setMeasuredDimension(usedWidth,usedHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
