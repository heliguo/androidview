package com.example.androidview.animator;

import android.view.View;

import com.example.androidview.animator.interpolator.TimeInterpolator;

import java.lang.ref.WeakReference;

/**
 * @author lgh on 2021/4/14 10:29
 * @description
 */
public class MyObjectAnimator implements VSNYCManager.AnimationFrameCallBack {

    private WeakReference<View> mTarget;

    private String mPropertyName;

    private MyFloatPropertyValuesHolder mValuesHolder;

    private boolean mAutoCancel = false;

    private long mStartTime;

    private float mDuration;

    private TimeInterpolator mTimeInterpolator;

    private float index;

    public MyObjectAnimator(View view, String propertyName, float... values) {
        mPropertyName = propertyName;
        mTarget = new WeakReference<>(view);
        mValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }

    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {

        return new MyObjectAnimator(view, propertyName, values);
    }

    public void start() {
        mValuesHolder.setupSetter(mTarget);
        mStartTime = System.currentTimeMillis();
        VSNYCManager.getInstance().add(this);
    }

    public void setTimeInterpolator(TimeInterpolator timeInterpolator) {
        mTimeInterpolator = timeInterpolator;
    }

    public void setDuration(float duration) {
        mDuration = duration;
    }

    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total = mDuration / 16f;
        //执行百分比 (index++)/total;
        float fraction = (index++) / total;
        if (mTimeInterpolator != null) {
            fraction = mTimeInterpolator.getInterpolator(fraction);
        }
        if (index >= total) {
            index =0;
//            VSNYCManager.getInstance().remove(this);
        }
        mValuesHolder.setAnimatedValue(mTarget.get(), fraction);
        return false;
    }
}
