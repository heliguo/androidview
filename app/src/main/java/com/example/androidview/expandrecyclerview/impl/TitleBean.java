package com.example.androidview.expandrecyclerview.impl;

import com.example.androidview.expandrecyclerview.ExpandableItemType;
import com.example.androidview.expandrecyclerview.Parent;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgh on 2021/2/23 14:54
 * @description
 */
public class TitleBean implements Serializable, Parent<ContentBean>, ExpandableItemType {

    private String time;

    private String title;

    private List<ContentBean> mMyChildren;

    private int type;

    private boolean isExpandable = true;

    private boolean isInitiallyExpanded = false;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<ContentBean> getChildren() {
        return getMyChildren();
    }

    public void setMyChildren(List<ContentBean> myChildren) {
        mMyChildren = myChildren;
    }


    public List<ContentBean> getMyChildren() {
        return mMyChildren;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public void setInitiallyExpanded(boolean initiallyExpanded) {
        isInitiallyExpanded = initiallyExpanded;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean isInitiallyExpandable() {
        return isExpandable;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return isInitiallyExpanded;
    }
}
