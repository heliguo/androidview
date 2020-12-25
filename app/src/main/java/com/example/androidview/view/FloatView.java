package com.example.androidview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/17 16:54
 * @description 悬浮view
 */
public class FloatView extends FrameLayout {

    /**
     * 最小滑动距离
     */
    private final int mTouchSlop;

    private final int mScreenHeight;

    private final int mScreenWidth;

    private int lastX;

    private int lastY;

    private int width;
    private int height;

    private int measureWidth;
    private int measureHeight;

    private ViewGroup.MarginLayoutParams mLayoutParams;

    private OnClickListener onClickListener;

    public FloatView(@NonNull Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        post(() -> {
            height = measureHeight;
            width = measureWidth;
            mLayoutParams = (MarginLayoutParams) getLayoutParams();
            mLayoutParams.topMargin = mScreenHeight / 2 - height;
            mLayoutParams.leftMargin = mScreenWidth - width;
            setLayoutParams(mLayoutParams);
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;
                int offsetY = y - lastY;

                int top = getTop();
                int left = getLeft();
                if (Math.abs(offsetX) > mTouchSlop || Math.abs(offsetY) > mTouchSlop) {
                    if (left + width / 2 > mScreenWidth / 2 && left < mScreenWidth - width ||
                            (left >= mScreenWidth - width && offsetX < 0) ||
                            (left + width / 2 <= mScreenWidth / 2 && offsetX > 0)) {
                        mLayoutParams.leftMargin = left + offsetX;
                    }

                    if (top > 0 && top < mScreenHeight - height || (top <= 0 && offsetY > 0) ||
                            (top >= mScreenHeight - height && offsetY < 0)) {
                        mLayoutParams.topMargin = top + offsetY;
                    }
                    setLayoutParams(mLayoutParams);
                }
                break;
            case MotionEvent.ACTION_UP:
                int offsetX1 = x - lastX;
                int offsetY1 = y - lastY;

                if (offsetX1 == 0 && offsetY1 == 0) {
                    if (onClickListener != null) {
                        onClickListener.onClick(this);
                    }
                }
                if (getLeft() + width < mScreenWidth) {
                    int diff = (int) (mScreenWidth - width - getLeft());
                    if (mOnSmoothScrollListener != null) {
                        mOnSmoothScrollListener.smoothScroll(diff);
                    }
                    //                    mLayoutParams.leftMargin = mScreenWidth - width;
                    //                    setLayoutParams(mLayoutParams);
                    //                    smoothScrollTo(-diff, 1000);

                }
                break;
        }

        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    private OnSmoothScrollListener mOnSmoothScrollListener;

    public void setOnSmoothScrollListener(OnSmoothScrollListener onSmoothScrollListener) {
        mOnSmoothScrollListener = onSmoothScrollListener;
    }

    public interface OnSmoothScrollListener {

        void smoothScroll(int dx);

    }


}
