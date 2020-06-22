package com.example.androidview;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.ntp.SntpUtils;
import com.example.androidview.view.TestDrawView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lgh
 */
public class MainActivity extends AppCompatActivity {

    private TestDrawView mTextView;
    private int          mMeasuredWidth;
    private int          mMeasuredHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                //onGLobalLayout会被多次调用，再获取到之后要移除观察者
                if (mMeasuredHeight != 0 || mMeasuredWidth != 0) {
                    mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}
