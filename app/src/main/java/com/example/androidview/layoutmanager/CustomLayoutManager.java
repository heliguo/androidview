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
        return dx;
    }

    private void fill(RecyclerView.Recycler recycler, boolean fillEnd) {
        if (getChildCount() == 0)
            return;
        if (fillEnd) {
            View anchorView = getChildAt(getChildCount() - 1);
            int anchorPosition = getPosition(anchorView);
            while (anchorView.getRight() < getWidth() - getPaddingRight()) {
                int position = (anchorPosition + 1) % getChildCount();
                if (position < 0) {
                    position += getChildCount();
                }
                View scarpItem = recycler.getViewForPosition(position);
                addView(scarpItem);
                measureChildWithMargins(scarpItem, 0, 0);

                int left = anchorView.getRight();
                int top = anchorView.getPaddingTop();
                int right = left + getDecoratedMeasuredWidth(scarpItem);
                int bottom = top + getDecoratedMeasuredHeight(scarpItem) - getPaddingBottom();
                layoutDecorated(scarpItem, left, top, right, bottom);
                anchorView = scarpItem;
            }

        }else {
            
        }
    }
}
