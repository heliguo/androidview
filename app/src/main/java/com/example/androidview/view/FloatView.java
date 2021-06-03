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

    private long mDownTime;
    private long mUpTime;

    private final long mOffsetClickTime = 300;

    private final long mOffsetLongClickTime = 600;

    private boolean isMove = false;

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
                isMove = false;
                mDownTime = System.currentTimeMillis();
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:

                isMove = true;

                int offsetX = x - lastX;
                int offsetY = y - lastY;

                int top = (int) (getTop() + getTranslationY());
                int left = (int) (getLeft() + getTranslationX());
                if (Math.abs(offsetX) > 0) {
                    if (left + width / 2 > mScreenWidth / 2 && left < mScreenWidth - width ||
                            (left >= mScreenWidth - width && offsetX < 0) ||
                            (left + width / 2 <= mScreenWidth / 2 && offsetX > 0)) {
                        mLayoutParams.leftMargin = left + offsetX;
                    }

                }
                if (Math.abs(offsetY) > 0) {
                    if (top > 0 && top < mScreenHeight - height || (top <= 0 && offsetY > 0) ||
                            (top >= mScreenHeight - height && offsetY < 0)) {
                        mLayoutParams.topMargin = top + offsetY;
                    }
                }
                setLayoutParams(mLayoutParams);
                break;
            case MotionEvent.ACTION_UP:

                mUpTime = System.currentTimeMillis();
                long offsetTime = mUpTime - mDownTime;

                int offsetX1 = Math.abs(x - lastX);
                int offsetY1 = Math.abs(y - lastY);

                if (offsetX1 < mTouchSlop && offsetY1 < mTouchSlop &&
                        (offsetTime < mOffsetClickTime || offsetTime >= mOffsetLongClickTime)) {
                    isMove = false;
                }
                if (!isMove) {
                    if (offsetTime < mOffsetClickTime) {
                        performClick();
                    }
                    /**
                     * move事件后长按也会触发，可以通过判断是否在最终位置处理是否在move后长按事件
                     */
                    if (offsetTime >= mOffsetLongClickTime) {
                        performLongClick();
                    }
                }
                if (getLeft() + width < mScreenWidth) {
                    //                    int diff = mScreenWidth - width - getLeft();
                    mLayoutParams.leftMargin = mScreenWidth - width;
                    setLayoutParams(mLayoutParams);

                }
                break;
        }

        return true;
    }


}
