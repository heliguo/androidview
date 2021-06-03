package com.example.androidview.animator;

/**
 * @author lgh on 2021/4/14 10:46
 * @description 关键帧
 */
public class MyFloatKeyframe {

    float mFraction;//当前百分比
    Class mValueType;
    float mValue;

    public MyFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public float getFraction() {
        return mFraction;
    }
}
