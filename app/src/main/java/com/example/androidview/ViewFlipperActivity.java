package com.example.androidview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

/**
 * @author lgh
 * @description ViewFlipper示例
 */
public class ViewFlipperActivity extends AppCompatActivity {

    private static final String TAG = "ViewFlipperActivity";

    ViewFlippers mViewFlipper;

    Stack<String> mStack = new Stack<>();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if (msg.what == 1) {
                View view = LayoutInflater.from(ViewFlipperActivity.this).inflate(R.layout.item_flipper_text, null);
                TextView textView = view.findViewById(R.id.task_flipper_text);
                textView.setText(mStack.pop());
                if (mStack.size() <= 2) {
                    randomContents();
                }
                mViewFlipper.addView(view);
                if (!mViewFlipper.isFlipping()) {
                    mViewFlipper.setFlipInterval(2000);
                    mViewFlipper.startFlipping();
                }
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        initView();
    }

    private void initView() {
        mViewFlipper = findViewById(R.id.view_filpper);
        mStack.push("1");
        mStack.push("2");
        mStack.push("3");
        mStack.push("4");
        mStack.push("5");
//        mHandler.sendEmptyMessage(1);
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
    }

    private void randomContents() {
        mStack.push("8888");
        mStack.push("9999");
        mStack.push("6666");
        View view = mViewFlipper.getCurrentView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            initStatusBar();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    private View statusBarView;

    /**
     * 颜色渐变
     */
    private void initStatusBar() {
        if (statusBarView == null) {
            //利用反射机制修改状态栏背景
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }

        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.bg_title_gradient);
        } else {
            Log.e(TAG, "initStatusBar: ");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(1);
            mHandler = null;
        }
    }
}