package com.example.androidview.screenadapter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2020/12/11 11:27
 * @description
 */
public class ScreenAutoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //        ScreenAutoAdapter.match(this,375.0f);
        DensityUtils.setDensity(getApplication(), this, 720);
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.getDecorView().post(() -> WindowInsetUtils.setFullScreen(window));
        }
        setContentView(R.layout.activity_screen_auto_adapter);
        //跳过刘海
        Button button = findViewById(R.id.btn_1);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        params.topMargin = UIUtils.getStatusBarHeight(getApplicationContext());

    }

}
