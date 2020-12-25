package com.example.androidview.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

import java.util.concurrent.TimeUnit;

/**
 * @author lgh on 2020/12/17 17:01
 * @description
 */
public class FloatViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_view);
        FloatView floatView = findViewById(R.id.float_view);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) floatView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(0, 10);
        animator.setDuration(1000);
        floatView.setOnClickListener(v -> Toast.makeText(FloatViewActivity.this, "55", Toast.LENGTH_SHORT).show());
        floatView.setOnSmoothScrollListener(dx -> {
            animator.addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                Log.e("+++++", "onCreate: " + value);
                                layoutParams.leftMargin = floatView.getLeft() + dx * value / 10;
                                floatView.requestLayout();
                //                floatView.setLayoutParams(layoutParams);
            });
            animator.start();
//            layoutParams.leftMargin = floatView.getLeft() + dx;
//            floatView.setLayoutParams(layoutParams);

        });
    }
}
