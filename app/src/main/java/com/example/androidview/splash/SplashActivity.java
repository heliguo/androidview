package com.example.androidview.splash;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/15 10:24
 * @description
 */
public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ParallaxContainer container = findViewById(R.id.parallax_container);

        container.setUp(new int[]{
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_intro_6,
                R.layout.view_intro_7,
                R.layout.view_intro_1
        });
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.man_run);
        container.setStableView(imageView);

    }
}
