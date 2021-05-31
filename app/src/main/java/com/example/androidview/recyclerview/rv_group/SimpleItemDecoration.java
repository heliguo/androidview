package com.example.androidview.recyclerview.rv_group;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lgh on 2021/5/31 9:57
 * @description
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    int itemOffset = 18;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        final int childAdapterPos = parent.getChildAdapterPosition(view);
        if (childAdapterPos != RecyclerView.NO_POSITION && childAdapterPos != parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, itemOffset, 0, 0);
        }
    }
}
