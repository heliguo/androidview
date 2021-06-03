package com.example.androidview.carview;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/28 11:13
 * @description
 */
public class CarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view);
    }
}
