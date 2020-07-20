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
import android.util.DisplayMetrics;
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
import com.tencent.mmkv.MMKV;

/**
 * @author lgh
 * @description 悬浮窗
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class WindowsActivity extends AppCompatActivity {

    private MMKV mmkv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows);
        String rootDir = MMKV.initialize(this);
        mmkv = MMKV.defaultMMKV();
        Log.e("TAG", "onCreate: " + rootDir);
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
            showWindows();
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
                showWindows();
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void showWindows() {
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int winWidth = displayMetrics.widthPixels;

        button = ((Button) getLayoutInflater().inflate(R.layout.float_btn, null));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE |
                LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.width = px2dp(40);
        layoutParams.height = px2dp(40);
        layoutParams.x = mmkv.decodeInt("float_x", winWidth);
        layoutParams.y = mmkv.decodeInt("float_y", 500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = LayoutParams.TYPE_PHONE;
        }

        windowManager.addView(button, layoutParams);

        button.setOnClickListener(v -> {
            Toast.makeText(this, "you click the float window", Toast.LENGTH_SHORT).show();
        });

        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int rawX = (int) event.getRawX();
                    int rawY = (int) event.getRawY();
                    layoutParams.x = rawX;
                    layoutParams.y = rawY - v.getMeasuredHeight();
                    windowManager.updateViewLayout(button, layoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    int lastX = layoutParams.x + v.getMeasuredWidth() / 2;
                    if (lastX > winWidth / 2) {
                        layoutParams.x = winWidth;
                    } else {
                        layoutParams.x = 0;
                    }
                    mmkv.encode("float_y", layoutParams.y);
                    mmkv.encode("float_x", layoutParams.x);
                    mmkv.apply();
                    windowManager.updateViewLayout(button, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        });


    }

    @Override
    protected void onPause() {
        if (Settings.canDrawOverlays(this))
            getWindowManager().removeView(button);
        Log.e("TAG", "onPause: ");
        super.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.e("TAG", "onWindowFocusChanged: ");
        super.onWindowFocusChanged(hasFocus);
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
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。
     */
    public int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue * scale + 0.5f);
    }
}