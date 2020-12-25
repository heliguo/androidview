package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomView extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;
    private OnClickListener onClickListener;

    private int width;
    private int height;

    private int mScreenHeight;

    private int mScreenWidth;

    private ViewGroup.MarginLayoutParams mLayoutParams;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        mScroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        mLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = x;
                lastY = y;
                Log.e("````````", "down ");
                break;

            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;
                int offsetY = y - lastY;

                int top = getTop();
                int left = getLeft();

                if (offsetX != 0 || offsetY != 0) {
                    //方法1：使用MarginLayoutParams，与点击事件不冲突但不如方法3
                    if (left + width / 2 > mScreenWidth / 2) {
                        mLayoutParams.leftMargin = left + offsetX;
                    }
                    mLayoutParams.topMargin = top + offsetY;
                    setLayoutParams(mLayoutParams);

                    //方法2：使用layout方法来重新放置它的位置，与点击事件冲突
                    //                    layout(getLeft() + offsetX, getTop() + offsetY,
                    //                            getRight() + offsetX, getBottom() + offsetY);

                    //方法3：使用LayoutParams，与点击事件不冲突效果,好，同方法2
                    //                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                    //                    layoutParams.leftMargin = getLeft() + offsetX;
                    //                    layoutParams.topMargin = getTop() + offsetY;
                    //                    setLayoutParams(layoutParams);

                    //方法4：使用scrollBy，添加点击事件后移动完毕后会执行点击事件
                                        ((View)getParent()).scrollBy(-offsetX,-offsetY);

                    //方法5：使用offset，添加点击事件后移动完毕后会执行点击事件
                    //                    offsetLeftAndRight(offsetX);//对left和right进行偏移
                    //                    offsetTopAndBottom(offsetY);//对top和bottom进行偏移

                }
                break;
            case MotionEvent.ACTION_UP:

                //计算移动的距离
                int offsetX1 = x - lastX;
                int offsetY1 = y - lastY;

                if (offsetX1 == 0 && offsetY1 == 0) {
                    if (onClickListener != null) {
                        onClickListener.onClick(this);
                    }

                }
                int left1 = getLeft();
                if (left1 + width < mScreenWidth) {
                    mLayoutParams.leftMargin = mScreenWidth - width;
                    setLayoutParams(mLayoutParams);
                }
                break;
        }

        return true;//消耗触摸事件
    }

    public void smoothScrollTo(int destX, int destY, int time) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int delta = destX - scrollX;
        int deltaY = destY - scrollY;
        //1000秒内滑向destX
        mScroller.startScroll(scrollX, scrollX, delta, deltaY, time);//与computeScroll同时使用
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }
}