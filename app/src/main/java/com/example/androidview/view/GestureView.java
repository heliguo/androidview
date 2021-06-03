package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/31 14:47
 * @description
 */
public class GestureView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureDetector mGestureDetector;

    /**
     * 用于自动计算滑动的偏移
     * 用于onFling()方法中调用OverScroll.fling()启动惯性滑动
     */
    private OverScroller mOverScroller;


    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, this);
        mGestureDetector.setOnDoubleTapListener(this);
        mOverScroller = new OverScroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 每次ACTION_DOWN事件出现的时候都会被调用
     * 返回true可以保证必然消费掉事件
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    /**
     * 按下100ms不松手会被调用，用于标记【按下状态】
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 用户单击时调用，ps：长按和双击不会调用
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 用户滑动时调用，第一个事件是ACTION_DOWN事件，
     * 第二个事件是当前事件，
     * 偏移是按下事件位置-当前事件位置
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 用户长按500ms调用
     * 【GestureDetectorCompat变为了600ms】
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 惯性滑动场景
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        int startX = 0;
        int startY = 0;
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;

        /*
         *初始化滑动
         */
        mOverScroller.fling(startX, startY, ((int) velocityX), ((int) velocityY), minX, maxX, minY, maxY);

        /*
         * 下一帧刷新
         */
        postOnAnimation(new FlingRunning());

        return false;
    }

    class FlingRunning implements Runnable {

        @Override
        public void run() {
            //计算此时的位置，并且如果滑动已经结束，就停止
            if (mOverScroller.computeScrollOffset()) {
                //把此时的位置应用于界面
                int offsetX = mOverScroller.getCurrX();
                int offsetY = mOverScroller.getCurrY();
                invalidate();
                /*
                 * 下一帧刷新
                 */
                postOnAnimation(this);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * 双击回调
     */

    /**
     * 用户单击调用，与onSingleTapUp()区别在于：300ms后触发
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击调用，第二次点击调用，而非抬起调用【长按事件也是按下超过500ms调用】
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    /**
     * 用户双击第二次按下时，第二次按下后移动时、第二次按下后抬起时都会被调用
     * 【常用于双击拖拽场景】
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
