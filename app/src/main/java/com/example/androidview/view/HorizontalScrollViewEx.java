package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

/**
 * @author:lgh on 2020/6/14 14:12
 */
public class HorizontalScrollViewEx extends HorizontalScrollView {

    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;

    private int mChildWidth;

    private int mChildIndex;

    private int mLastX;

    private int mLastY;

    private int mLastXIntercept;

    private int mLastYIntercept;

    private Scroller mScroller;

    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mScroller == null) {
            mScroller = new Scroller(context);
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;

        int x = ((int) ev.getX());
        int y = ((int) ev.getY());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                intercept = Math.abs(offsetX) > Math.abs(offsetY);

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        mVelocityTracker.addMovement(ev);
        int x = ((int) ev.getX());
        int y = ((int) ev.getY());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                Log.e(TAG, "onTouchEvent: ");
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                scrollBy(-offsetX, 0);
                break;
            case MotionEvent.ACTION_UP:

                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                if (xVelocity > 0) {
                    smoothScrollByH(-1000, 0);
                } else {
                    smoothScrollByH(1000, 0);//父类有该方法
                }
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);//如果没有子view则为0
        } else if (widthSpaceMode == MeasureSpec.AT_MOST &&
                heightSpaceMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth, heightSpaceSize);
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, measureHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                final int measuredWidth = childView.getMeasuredWidth();
                mChildWidth = measuredWidth;
                childView.layout(childLeft, 0,
                        childLeft + measuredWidth, childView.getMeasuredHeight());
                childLeft += measuredWidth;
            }
        }

    }

    private void smoothScrollByH(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 10000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
