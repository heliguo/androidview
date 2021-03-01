package com.example.androidview.expandrecyclerview.impl;


import com.example.androidview.expandrecyclerview.ExpandableItemType;

import java.io.Serializable;

/**
 * @author lgh on 2021/1/14 11:27
 * @description
 */
public class ContentBean implements Serializable , ExpandableItemType {

    private String title;

    private String content;

    private boolean has;

    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

}
