package com.example.androidview.animator.interpolator;

/**
 * @author lgh on 2021/4/14 12:03
 * @description 线性插值器
 */
public class LinearInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolator(float input) {
        return input;
    }
}
