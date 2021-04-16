package com.example.androidview.toobar;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.screenadapter.UIUtils;
import com.example.androidview.screenadapter.WindowInsetUtils;

/**
 * @author lgh on 2021/4/16 14:36
 * @description
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class ToolbarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(getApplicationContext());
        setContentView(R.layout.activity_toolbar);
        initView();
    }


    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_bar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        StatusBarUtils.setStatusBar(this,toolbar,R.color.colorPrimary);
        StatusBarUtils.setAndroidNativeLightStatusBar(this,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
