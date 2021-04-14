package com.example.androidview.animator;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/14 10:28
 * @description
 */
public class AnimatorActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_animator);
        Button button = findViewById(R.id.btn_animator);
        MyObjectAnimator objectAnimator = MyObjectAnimator.ofFloat(button, "scaleX", 1f, 2f);

        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "scaleX", 1);
        animator.setInterpolator(new LinearInterpolator());

        objectAnimator.setDuration(2000);
        objectAnimator.setTimeInterpolator(new com.example.androidview.animator.interpolator.LinearInterpolator());
        objectAnimator.start();
    }
}
