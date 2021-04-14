package com.example.androidview.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.util.Arrays;
import java.util.List;

/**
 * @author lgh on 2021/4/14 10:44
 * @description 关键帧集合
 */
public class MyKeyframeSet {


    TypeEvaluator mTypeEvaluator;//估值器

    MyFloatKeyframe mFirstKeyframe;

    List<MyFloatKeyframe> mKeyframes;


    private MyKeyframeSet(MyFloatKeyframe... keyframes) {
        mKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        mTypeEvaluator = new FloatEvaluator();
    }

    public static MyKeyframeSet ofFloat(float... values) {
        int keyframes = values.length;
        MyFloatKeyframe[] floatKeyframes = new MyFloatKeyframe[keyframes];
        floatKeyframes[0] = new MyFloatKeyframe(0, values[0]);//第0帧
        for (int i = 1; i < keyframes; i++) {
            floatKeyframes[i] = new MyFloatKeyframe((float) i / (keyframes - 1), values[i]);
        }
        return new MyKeyframeSet(floatKeyframes);
    }

    public Object getValue(float fraction) {

        MyFloatKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 1; i < mKeyframes.size(); i++) {
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);
            if (fraction < nextKeyframe.getFraction()) {
                return mTypeEvaluator.evaluate(fraction, prevKeyframe.getValue(), nextKeyframe.getValue());
            }
            prevKeyframe = nextKeyframe;
        }
        return null;
    }
}
