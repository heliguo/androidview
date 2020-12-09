package com.example.androidview.hencoder.animation_6_7;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author lgh on 2020/12/9 11:13
 * @description
 */
public class PointFEvaluator implements TypeEvaluator<PointF> {

    PointF newPoint = new PointF();

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.x + (fraction * (endValue.x - startValue.x));
        float y = startValue.y + (fraction * (endValue.y - startValue.y));

        newPoint.set(x, y);

        return newPoint;
    }
}

