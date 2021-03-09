package com.example.androidview.expandrecyclerview.impl;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.androidview.R;
import com.example.androidview.expandrecyclerview.ChildViewHolder;

public class MyChildViewHolder extends ChildViewHolder<ContentBean> {

    public MyChildViewHolder(View itemView) {
        super(itemView);

    }

    public void bind(ContentBean data) {
//        itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.shape_expandable_bg_child));
        getView(R.id.view_line).setVisibility(View.VISIBLE);
        TextView name = getView(R.id.tv_expandable_child_drug);
        name.setText(data.getTitle());
        TextView count = getView(R.id.tv_expandable_child_drug_dosage);
        count.setText(data.getContent());
//        TextView btn = getView(R.id.tv_expand_child_confirm_take_medicine);
    }

}
