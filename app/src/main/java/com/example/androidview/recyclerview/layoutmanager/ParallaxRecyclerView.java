package com.example.androidview.recyclerview.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;


/**
 * @author lgh on 2021/6/15 15:10
 * @description
 */
public class ParallaxRecyclerView extends RecyclerView {

    private float mDownX;


    public ParallaxRecyclerView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxRecyclerView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setChildrenDrawingOrderEnabled(true); //开启重新排序
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof ParallaxLayoutManager3)) {
            throw new IllegalArgumentException("The layout manager must be ParallaxLayoutManager");
        }
        super.setLayoutManager(layout);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int center = getParallaxLayoutManager().getCenterPosition();
        // 获取 RecyclerView 中第 i 个 子 view 的实际位置
        int actualPos = getParallaxLayoutManager().getChildActualPos(i);

        // 距离中间item的间隔数
        int dist = actualPos - center;
        int order;
        if (dist < 0) { // [< 0] 说明 item 位于中间 item 左边，按循序绘制即可
            order = i;
        } else { // [>= 0] 说明 item 位于中间 item 右边，需要将顺序颠倒绘制
            order = childCount - 1 - dist;
        }

        if (order < 0)
            order = 0;
        else if (order > childCount - 1)
            order = childCount - 1;

        return order;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                getParent().requestDisallowInterceptTouchEvent(true); //设置父类不拦截滑动事件
                break;
            case MotionEvent.ACTION_MOVE:
                //如果是滑动到了最前和最后，开放父类滑动事件拦截
                //滑动到中间，设置父类不拦截滑动事件
                getParent().requestDisallowInterceptTouchEvent((!(ev.getX() > mDownX) || getParallaxLayoutManager().getCenterPosition() != 0) &&
                        (!(ev.getX() < mDownX) || getParallaxLayoutManager().getCenterPosition() != getParallaxLayoutManager().getItemCount() - 1));
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 获取LayoutManger，并强制转换为 ParallaxLayoutManager
     */
    public ParallaxLayoutManager3 getParallaxLayoutManager() {
        return ((ParallaxLayoutManager3) getLayoutManager());
    }
}
