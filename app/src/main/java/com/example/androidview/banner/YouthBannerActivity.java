package com.example.androidview.banner;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/5/29 14:17
 * @description
 */
public class YouthBannerActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_youth_banner);
        BannerView bannerView = findViewById(R.id.home_banner);
        bannerView.show();

    }
}
