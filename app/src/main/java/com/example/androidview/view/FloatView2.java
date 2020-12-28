package com.example.androidview.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/17 16:54
 * @description 悬浮view 靠左停靠动画
 */
public class FloatView2 extends FrameLayout {

    /**
     * 最小滑动距离
     */
    private final int mTouchSlop;

    private final int mScreenHeight;

    private final int mScreenWidth;

    private int lastX;

    private int lastY;

    private int measureWidth;
    private int measureHeight;

    private long mDownTime;

    private static final long mOffsetClickTime = 300;

    private static final long mOffsetLongClickTime = 600;

    private boolean isMove = false;

    private final int mDuration = 1000;


    public FloatView2(@NonNull Context context) {
        this(context, null);
    }

    public FloatView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        post(() -> {
            int diffX = (int) (mScreenWidth - getLeft() - getTranslationX() - measureWidth);
            int diffY = (int) (mScreenHeight - getTop() - getTranslationY() - measureHeight) / 2;
            setTranslationX(diffX);
            setTranslationY(diffY);
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
                    if (left + measureWidth / 2 > mScreenWidth / 2 && left < mScreenWidth - measureWidth ||
                            (left >= mScreenWidth - measureWidth && offsetX < 0) ||
                            (left + measureWidth / 2 <= mScreenWidth / 2 && offsetX > 0)) {
                        setTranslationX(getTranslationX() + offsetX);
                    }

                }
                if (Math.abs(offsetY) > 0) {
                    if (top > 0 && top < mScreenHeight - measureHeight || (top <= 0 && offsetY > 0) ||
                            (top >= mScreenHeight - measureHeight && offsetY < 0)) {
                        setTranslationY(getTranslationY() + offsetY);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis();
                long offsetTime = upTime - mDownTime;

                int offsetX1 = Math.abs(x - lastX);
                int offsetY1 = Math.abs(y - lastY);

                if (offsetX1 < mTouchSlop && offsetY1 < mTouchSlop && offsetTime < mOffsetClickTime && !isMove) {
                    performClick();
                }
                if (offsetX1 < mTouchSlop && offsetY1 < mTouchSlop && offsetTime >= mOffsetLongClickTime && !isMove) {
                    performLongClick();
                }

                int diff = (int) (mScreenWidth - getLeft() - getTranslationX() - measureWidth);
                if (diff > 0) {
                    // 回到最左侧
                    ObjectAnimator animator = ObjectAnimator.ofFloat(this,
                            "TranslationX",
                            getTranslationX(),
                            getTranslationX() + diff
                    );
                    animator.setDuration(mDuration);
                    animator.start();
                }

                break;
        }

        return true;
    }


}
