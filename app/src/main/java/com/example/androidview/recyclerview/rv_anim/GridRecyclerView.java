package com.example.androidview.recyclerview.rv_anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author lgh on 2021/4/12 11:49
 * @description recyclerview 动画 使用 gridLayoutAnimation 必须自定义recyclerview 否则崩溃
 */
public class GridRecyclerView extends RecyclerView {

    public GridRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {

        final LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager && getAdapter() != null) {

            GridLayoutAnimationController.AnimationParameters parameters =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;
            if (parameters == null) {
                parameters = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = parameters;
            }
            parameters.count = count;
            parameters.index = index;
            final int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            parameters.columnsCount = spanCount;
            parameters.rowsCount = count / spanCount;
            final int invertedIndex = count - 1 - index;
            parameters.column = spanCount - 1 - (invertedIndex % spanCount);
            parameters.row = parameters.rowsCount - 1 - invertedIndex / spanCount;

        } else
            super.attachLayoutAnimationParameters(child, params, index, count);
    }
}
