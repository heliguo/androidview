package com.example.androidview.screenadapter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

/**
 * @author lgh on 2020/12/11 11:27
 * @description
 */
public class ScreenAutoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenAutoAdapter.match(this,375.0f);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_auto_adapter);

    }
}
