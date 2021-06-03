package com.example.androidview.svg;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;

/**
 * @author lgh on 2021/4/28 16:41
 * @description
 */
public class SVGActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SVGView(this));
    }
}
