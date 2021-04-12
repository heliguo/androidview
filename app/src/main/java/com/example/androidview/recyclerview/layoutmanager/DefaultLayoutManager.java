package com.example.androidview.recyclerview.layoutmanager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lgh on 2021/4/12 15:53
 * @description
 */
public class DefaultLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
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


}
