package com.example.androidview;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.animation.FrameAnimationActivity;
import com.example.androidview.animation.Rotate3dActivity;
import com.example.androidview.databinding.ActivityMainBinding;
import com.example.androidview.dialog.DialogFragmentActivity;
import com.example.androidview.ntp.SntpUtils;
import com.example.androidview.rootview.RootViewActivity;
import com.example.androidview.view.HorizontalScrollActivity;
import com.example.androidview.windows.WindowsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lgh
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        recyclerviewAnimation();
        mTextView = findViewById(R.id.test_draw_view);
        new Thread(() -> {
            Log.e("LOG", "requesting time..");

            Date dt = SntpUtils.getUTCDate();
            Log.e("LOG", "dt: " + dt.toString());

            long ts = SntpUtils.getUTCTimestamp();
            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String format1 = format.format(ts);
            String format2 = format.format(System.currentTimeMillis());
            Log.e("LOG", "时间: " + format1);
            Log.e("LOG", "时间2: " + format2);
            Log.e("LOG", "ts: " + ts);
        }).start();

        Looper.myQueue().addIdleHandler(() -> {
            Log.e("addIdleHandler", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);

            return false;
        });

    }

    /**
     * recyclerview item动画
     */
    private void recyclerviewAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(2000);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mBinding.mainRv.setLayoutAnimation(controller);
    }

    /**
     * 获取view宽高的四种方式，第四种方式忽略
     * 1.onWindowFocusChanged
     * 当activity失去或获得焦点该方法均会被调用
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mMeasuredWidth = mTextView.getMeasuredWidth();
            mMeasuredHeight = mTextView.getMeasuredHeight();
            Log.e("onWindowFocusChanged", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);
        }
        /**
         * 非粘性沉浸
         * 使用Flag SYSTEM_UI_FLAG_IMMERSIVE，
         * 隐藏系统栏。当用户在系统栏区域向内滑动时，系统栏会重新显示并保持可见。
         * 粘性沉浸
         * 当使用Flag SYSTEM_UI_FLAG_IMMERSIVE_STICKY，
         * 在系统栏的区域中向内滑动会引起系统栏出现一个半透明的状态，
         * 但是 flags 不会被清除，监听系统界面可见性变化的listeners并不会被调用。
         * 系统栏会在几秒之后再次动态隐藏，同样当用户点击了屏幕，系统栏也会隐藏。
         */
        //判断4.4以上版本
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //获得DecorView
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE //来帮助你的app来维持一个稳定的布局
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //确保appUI的主要部分不会因为被系统导航栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //确保appUI的主要部分不会因为被系统状态栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                            | View.SYSTEM_UI_FLAG_FULLSCREEN //表示全屏，会将状态栏隐藏，只会隐藏状态栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);//粘性沉浸

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: ");
        /**
         * 获取view宽高的四种方式，第四种方式忽略
         * 2.view.post()
         * 通过post可以将一个runnable投递到消息队列的尾部，
         * 然后等待Looper调用此runnable的时候，View也已经初始化好了
         */
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                mMeasuredWidth = mTextView.getMeasuredWidth();
                mMeasuredHeight = mTextView.getMeasuredHeight();
                Log.e("post", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);
            }
        });

        /**
         * 获取view宽高的四种方式，第四种方式忽略
         * 3.ViewTreeObserver
         */
        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMeasuredWidth = mTextView.getMeasuredWidth();
                mMeasuredHeight = mTextView.getMeasuredHeight();
                Log.e("getViewTreeObserver", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);

                //onGLobalLayout会被多次调用，再获取到之后要移除观察者
                if (mMeasuredHeight != 0 || mMeasuredWidth != 0) {
                    mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void floatingWindows(View view) {
        startActivity(new Intent(this, WindowsActivity.class));
    }

    public void viewFlipper(View view) {
        startActivity(new Intent(this, ViewFlipperActivity.class));
    }

    public void horizontalScroll(View view) {
        startActivity(new Intent(this, HorizontalScrollActivity.class));
    }

    public void dialogFragment(View view) {
        startActivity(new Intent(this, DialogFragmentActivity.class));
    }

    public void rootView(View view) {
        startActivity(new Intent(this, RootViewActivity.class));

    }

    public void frameAnimation(View view) {
        startActivity(new Intent(this, FrameAnimationActivity.class));
    }

    public void rotate(View view) {
        startActivity(new Intent(this, Rotate3dActivity.class));
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("++++++++", "onDestroy: " );
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("==========", "finish: " );
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }
}
