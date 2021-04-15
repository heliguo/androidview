package com.example.event;

/**
 * @author lgh on 2021/4/14 17:14
 * @description
 */
public class View {

    private int left, top, right, bottom;

    public View(){

    }


    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isContainer(int x, int y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public boolean dispatchTransformTargetTouchEvent(MotionEvent event, View child) {
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }

}
