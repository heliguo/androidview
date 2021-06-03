package com.example.androidview.recyclerview.cardscale;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.utils.ScreenUtils;

/**
 * @author lgh on 2021/4/12 16:16
 * @description 动态添加 padding margin
 */
public class CardAdapterHelper {

    public static void onCreateViewHolder(ViewGroup parent, View itemView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = parent.getWidth() - ScreenUtils.dp2px(itemView.getContext(), 2 * (CardScaleConstant.PAGER_PADDING + CardScaleConstant.PAGER_MARGIN));
        itemView.setLayoutParams(lp);
    }

    public static void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = ScreenUtils.dp2px(itemView.getContext(), CardScaleConstant.PAGER_PADDING);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + ScreenUtils.dp2px(itemView.getContext(), CardScaleConstant.PAGER_MARGIN) : 0;
        int rightMarin = position == itemCount - 1 ? padding + ScreenUtils.dp2px(itemView.getContext(), CardScaleConstant.PAGER_MARGIN) : 0;
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0);
    }

    private static void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public static void setPagePadding(int pagePadding) {
        CardScaleConstant.PAGER_PADDING = pagePadding;
    }

    public static void setPageMarin(int pagerMargin) {
        CardScaleConstant.PAGER_MARGIN = pagerMargin;
    }
}
