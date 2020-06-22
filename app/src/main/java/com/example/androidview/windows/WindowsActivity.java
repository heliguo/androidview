package com.example.androidview.windows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.androidview.MainActivity;
import com.example.androidview.R;

import java.security.Policy;

/**
 * @author lgh
 * @description 悬浮窗
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class WindowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows);
        if (!Settings.canDrawOverlays(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否允许前往设置开启悬浮窗权限");
            builder.setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            premisson();
                        }
                    }).show();
        }
        if (Settings.canDrawOverlays(this)) {
            showWidows();
        }

    }

    private void premisson() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                Toast.makeText(this, "not granted", Toast.LENGTH_SHORT).show();
            } else {
                showWidows();
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void showWidows() {
        WindowManager windowManager = getWindowManager();
        Button button = new Button(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE |
                LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.x = 300;
        layoutParams.y = 300;
        button.setText("windows");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = LayoutParams.TYPE_PHONE;
        }
        windowManager.addView(button, layoutParams);
        button.setOnTouchListener((v, event) -> {
            int rawX = (int) event.getRawX();
            int rawY = (int) event.getRawY();
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                layoutParams.x = rawX;
                layoutParams.y = rawY;
                windowManager.updateViewLayout(button, layoutParams);
            }
            return false;
        });


    }

    @Override
    protected void onDestroy() {
        Log.e("TAG", "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void createWindows() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        AppCompatDelegate delegate = getDelegate();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void test(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }
}