package com.example.androidview.recyclerview.layoutmanager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * @author lgh on 2021/4/12 15:53
 * @description
 */
public class DefaultLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    ///////////////////////////////  测量   //////////////////////////////////
    @Override
    public void measureChild(@NonNull @NotNull View child, int widthUsed, int heightUsed) {
        super.measureChild(child, widthUsed, heightUsed);
    }

    @Override
    public void measureChildWithMargins(@NonNull @NotNull View child, int widthUsed, int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public int getDecoratedMeasuredWidth(@NonNull @NotNull View child) {
        return super.getDecoratedMeasuredWidth(child);
    }

    @Override
    public int getDecoratedMeasuredHeight(@NonNull @NotNull View child) {
        return super.getDecoratedMeasuredHeight(child);
    }

    ///////////////////////////////  布局   //////////////////////////////////
    @Override
    public void layoutDecorated(@NonNull @NotNull View child, int left, int top, int right, int bottom) {
        super.layoutDecorated(child, left, top, right, bottom);
    }

    @Override
    public void layoutDecoratedWithMargins(@NonNull @NotNull View child, int left, int top, int right, int bottom) {
        super.layoutDecoratedWithMargins(child, left, top, right, bottom);
    }

    ///////////////////////////////   回收   //////////////////////////////////
    @Override
    public void detachAndScrapView(@NonNull @NotNull View child, @NonNull @NotNull RecyclerView.Recycler recycler) {
        super.detachAndScrapView(child, recycler);
    }

    @Override
    public void detachAndScrapViewAt(int index, @NonNull @NotNull RecyclerView.Recycler recycler) {
        super.detachAndScrapViewAt(index, recycler);
    }

    @Override
    public void detachAndScrapAttachedViews(@NonNull @NotNull RecyclerView.Recycler recycler) {
        super.detachAndScrapAttachedViews(recycler);
    }

    //更新布局状态，并根据布局状态设置预取位置
    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state,
                                                 LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
    }


    //计算预取位置
    @Override
    public void collectInitialPrefetchPositions(int adapterItemCount, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectInitialPrefetchPositions(adapterItemCount, layoutPrefetchRegistry);
    }

    @Override
    public void removeAndRecycleView(@NonNull @NotNull View child, @NonNull @NotNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleView(child, recycler);
    }


    @Override
    public void removeAndRecycleViewAt(int index, @NonNull @NotNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleViewAt(index, recycler);
    }

    @Override
    public void removeAndRecycleAllViews(@NonNull @NotNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
    }


    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }


}
