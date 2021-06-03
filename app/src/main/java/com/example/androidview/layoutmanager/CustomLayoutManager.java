package com.example.androidview.layoutmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lgh on 2020/12/29 19:54
 * @description
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = getItemCount();
        if (itemCount <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int paddingLeft = getPaddingLeft();
        for (int i = 0; ; i++) {
            if (paddingLeft >= getWidth() - getPaddingRight()) {
                break;
            }
            View view = recycler.getViewForPosition(i % itemCount);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int right = paddingLeft + getDecoratedMeasuredWidth(view);
            int top = getPaddingTop();
            int bottom = top + getDecoratedMeasuredHeight(view);
            layoutDecorated(view, paddingLeft, top, right, bottom);
            paddingLeft = right;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fill(recycler, dx > 0);
        offsetChildrenHorizontal(-dx);
        recyclerChildView(dx > 0, recycler);
        return dx;
    }


    private void fill(RecyclerView.Recycler recycler, boolean fillEnd) {
        if (getChildCount() == 0)
            return;
        if (fillEnd) {
            //填充尾部
            View anchorView = getChildAt(getChildCount() - 1);
            int anchorPosition = getPosition(anchorView);
            while (anchorView.getRight() < getWidth() - getPaddingRight()) {
                int position = (anchorPosition + 1) % getChildCount();
                if (position < 0) {
                    position += getChildCount();
                }
                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem);
                measureChildWithMargins(scrapItem, 0, 0);

                int left = anchorView.getRight();
                int top = getPaddingTop();
                int right = left + getDecoratedMeasuredWidth(scrapItem);
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }

        } else {
            //填充首部
            View anchorView = getChildAt(0);
            int anchorPosition = getPosition(anchorView);

            while (anchorView.getPaddingLeft() > getPaddingLeft()) {
                int position = (anchorPosition - 1) % getItemCount();
                if (position < 0) {
                    position += getChildCount();
                }
                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem, 0);

                measureChildWithMargins(scrapItem, 0, 0);

                int right = anchorView.getLeft();
                int top = getPaddingTop();
                int left = right - getDecoratedMeasuredWidth(scrapItem);
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }

        }
    }


    private void recyclerChildView(boolean fillEnd, RecyclerView.Recycler recycler) {

        if (fillEnd) {
            //回收头部
            for (int i = 0; ; i++) {
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getRight() < getPaddingLeft();
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }

        } else {
            //回收尾部
            for (int i = getChildCount() - 1; ; i--) {
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getLeft() > getWidth() - getPaddingRight();
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        }
    }
}
