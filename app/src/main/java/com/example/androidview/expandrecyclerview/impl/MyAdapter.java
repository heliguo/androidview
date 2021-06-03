package com.example.androidview.expandrecyclerview.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidview.R;
import com.example.androidview.expandrecyclerview.ExpandableAdapter;

import java.util.List;

public class MyAdapter
        extends ExpandableAdapter<MyParentViewHolder, MyChildViewHolder, TitleBean, ContentBean> {

    private List<TitleBean> mData;

    public MyAdapter(List<TitleBean> data) {
        super(data);
        mData = data;
    }

    public List<TitleBean> getData() {
        return mData;
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup parent, int parentType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_father, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(parentType, parent, false);
        return new MyParentViewHolder(itemView);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup child, int childType) {
//        View itemView = LayoutInflater.from(child.getContext()).inflate(R.layout.item_expandable_child, child, false);
        View itemView = LayoutInflater.from(child.getContext()).inflate(childType, child, false);
        return new MyChildViewHolder(itemView);
    }

    @Override
    public void onBindParentViewHolder(MyParentViewHolder pvh, int parentPosition) {
        TitleBean parent = getParent(parentPosition);
        pvh.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder cvh, int parentPosition, int childPosition) {
        ContentBean child = getChild(parentPosition, childPosition);
        cvh.bind(child);
    }

    @Override
    public int getParentType(int parentPosition) {
        TitleBean myParent = mData.get(parentPosition);
        return myParent.getType();
    }

    @Override
    public int getChildType(int parentPosition, int childPosition) {
        ContentBean myChild = mData.get(parentPosition).getChildren().get(childPosition);
        return myChild.getType();
    }

}
