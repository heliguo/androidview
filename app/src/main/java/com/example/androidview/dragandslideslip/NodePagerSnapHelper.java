package com.example.androidview.dragandslideslip;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


public class NodePagerSnapHelper extends PagerSnapHelper {

    private OnPageListener mOnPageListener;
    private int mCurrentPosition = -1;

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) targetView.getLayoutParams();
        int position = params.getViewAdapterPosition();
        if (mOnPageListener != null && mCurrentPosition != position) {
            mOnPageListener.onPageSelected(mCurrentPosition = position);
        }
        return super.calculateDistanceToFinalSnap(layoutManager, targetView);
    }

    public void setOnPageListener(OnPageListener onPageListener) {
        mOnPageListener = onPageListener;
    }

    public interface OnPageListener {
        void onPageSelected(int position);
    }
}
