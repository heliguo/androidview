package com.example.androidview.expandrecyclerview.impl;

import android.view.View;
import android.widget.TextView;

import com.example.androidview.R;
import com.example.androidview.expandrecyclerview.ParentViewHolder;

public class MyParentViewHolder extends ParentViewHolder<TitleBean> {

    public MyParentViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(TitleBean data) {
        TextView textView = getView(R.id.tv_expandable_item_time);
        textView.setText(data.getTime());
    }
}
