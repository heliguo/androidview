package com.example.androidview.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lgh on 2021/4/14 10:37
 * @description
 */
public class MyFloatPropertyValuesHolder {

    String mPropertyName;

    Class mValueType;

    Method mSetter;

    MyKeyframeSet mKeyframeSet;

    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframeSet = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target) {
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;
        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setAnimatedValue(View view, float fraction) {
        Object value = mKeyframeSet.getValue(fraction);
        try {
            mSetter.invoke(view, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
