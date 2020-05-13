package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * author:lgh on 2020-05-12 14:13
 */
public class TestView extends View implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetector mDetector;
    private Context mContext;
    private Scroller mScroller;

    private int lastX;
    private int lastY;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initGestureDetector();
        mScroller = new Scroller(context);
    }

    private void initGestureDetector() {
        mDetector = new GestureDetector(mContext, this);
        //解决长按屏幕无法拖动问题
        mDetector.setIsLongpressEnabled(false);
    }

    @Override
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

                if (offsetX != 0 || offsetY != 0) {
                    //方法1：使用MarginLayoutParams，与点击事件不冲突但不如方法3
//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                    layoutParams.leftMargin = getLeft() + offsetX;
//                    layoutParams.topMargin = getTop() + offsetY;
//                    setLayoutParams(layoutParams);

                    //方法2：使用layout方法来重新放置它的位置，与点击事件不冲突效果好
//                    layout(getLeft() + offsetX, getTop() + offsetY,
//                            getRight() + offsetX, getBottom() + offsetY);

                    //方法3：使用LayoutParams，与点击事件不冲突效果,好，同方法2
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                    layoutParams.leftMargin = getLeft() + offsetX;
                    layoutParams.topMargin = getTop() + offsetY;
                    setLayoutParams(layoutParams);

                    //方法4：使用scrollBy，添加点击事件后移动完毕后会执行点击事件
//                    ((View)getParent()).scrollBy(-offsetX,-offsetY);

                    //方法5：使用offset，添加点击事件后移动完毕后会执行点击事件
//                    offsetLeftAndRight(offsetX);//对left和right进行偏移
//                    offsetTopAndBottom(offsetY);//对top和bottom进行偏移

                }
                break;
            case MotionEvent.ACTION_UP:

                //计算移动的距离
                int offsetX1 = x - lastX;
                int offsetY1 = y - lastY;

                /**
                 * 点击事件
                 */
                if (offsetX1 == 0 && offsetY1 == 0) {
                    onClickListener.onClick(this);
                }
                break;
        }

        return true;//消耗触摸事件

//        VelocityTracker velocityTracker = VelocityTracker.obtain();
//        velocityTracker.addMovement(event);
//        velocityTracker.clear();
//        velocityTracker.recycle();

//        return mDetector.onTouchEvent(event);
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

    private OnClickListener onClickListener;

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    /**
     * 以下为OnGestureListener 重写方法
     */

    /**
     * 单触摸 action_down
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 单按压无移动 action_down
     *
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 手指松开，事件为  action_down --->  action_up (无action_move)
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 事件为  action_down + 一系列 action_move
     *
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 长按事件
     *
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 快速滑动
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    /**
     * 以下为 OnDoubleTapListener 重写方法
     */

    /**
     * 双击 与 onSingleTapConfirmed 不共存
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    /**
     * 单击 与 onDoubleTap 不共存
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 表示发生双击事件
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
