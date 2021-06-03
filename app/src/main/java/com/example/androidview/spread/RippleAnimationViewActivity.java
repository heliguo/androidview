package com.example.androidview.spread;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/5/17 16:00
 * @description
 */
public class RippleAnimationViewActivity extends BaseActivity {

    private ImageView imageView;
    RippleAnimationView rippleAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_ripple_view);
        imageView = (ImageView) findViewById(R.id.ImageView);
//        ViewCalculateUtil.setViewLayoutParam(imageView, 300, 300, 0, 0, 0, 0);
        rippleAnimationView = (RippleAnimationView) findViewById(R.id.layout_RippleAnimation);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rippleAnimationView.isAnimationRunning()) {
                    rippleAnimationView.stopRippleAnimation();
                } else {
                    rippleAnimationView.startRippleAnimation();
                }
            }
        });
    }
}
