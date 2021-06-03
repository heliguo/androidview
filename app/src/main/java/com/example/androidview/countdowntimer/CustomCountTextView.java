package com.example.androidview.countdowntimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.androidview.R;

import java.util.List;

/**
 * author:lgh on 2020-04-30 16:56
 * 正向计时 TextView，text 均为 long 类型
 * <p>
 * 判断view是否可见 getLocalVisibleRect(new Rect())
 */
public class CustomCountTextView extends AppCompatTextView implements LifecycleObserver {

    private static final long DEFAULT_INTERVAL = 1000;//默认计时间隔为 1s

    private CountDownTimer mCountDownTimer;

    private OnCountDownStartListener mOnCountDownStartListener;

    private long mInitTime;//开始计时的时间 long

    private boolean isRunning;

    private long mTimeInterval = DEFAULT_INTERVAL;

    private long currentTime;

    private boolean mShowType;//false time true 计数

    public CustomCountTextView(Context context) {
        this(context, null);
    }

    public CustomCountTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCountTextView);
        mShowType = typedArray.getInt(R.styleable.CustomCountTextView_showType, 0) != 0;
        typedArray.recycle();
        autoBindLifecycle(context);
    }

    public void count() {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        /**
         * 倒计时
         */
        if (mInitTime == 0) {
            setText("请设置初始计时时间");
            return;
        }
        setText(generateTime(mInitTime));
        currentTime = mInitTime;

        mCountDownTimer = new CountDownTimer(mInitTime, mTimeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                setRunning(true);
                if (currentTime <= 0) {
                    mCountDownTimer.cancel();
                    if (mOnCountDownStartListener != null) {
                        mOnCountDownStartListener.onFinish();
                    }
                    setText(generateTime(0));
                } else {
                    currentTime = currentTime - mTimeInterval;
                    setText(generateTime(currentTime));
                }

            }

            @Override
            public void onFinish() {
                mCountDownTimer.cancel();
                mCountDownTimer = null;
                if (mOnCountDownStartListener != null) {
                    mOnCountDownStartListener.onFinish();
                }

            }
        };

        mCountDownTimer.start();

    }

    public CustomCountTextView setTimeInterval(long timeInterval) {
        mTimeInterval = timeInterval;
        return this;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void setRunning(boolean running) {
        isRunning = running;
    }

    public void setOnCountDownStartListener(OnCountDownStartListener onCountDownStartListener) {
        mOnCountDownStartListener = onCountDownStartListener;
    }

    /**
     * 设置初始开始计时时间
     *
     * @param time long
     */
    public CustomCountTextView setInitTime(long time) {
        this.mInitTime = time;
        return this;
    }

    /**
     * 获取当前时间与初始值的差值
     *
     * @return long
     */
    private long getIntervalTime() {
        return mInitTime - mTimeInterval;
    }

    public CustomCountTextView setShowType(boolean showType) {
        mShowType = showType;
        return this;
    }

    /**
     * 回调接口
     */
    public interface OnCountDownStartListener {

        void onTicker(long time);

        void onFinish();

    }

    /**
     * 将毫秒转时分秒
     */
    @SuppressLint("DefaultLocale")
    private String generateTime(long time) {
        String format;

        int totalSeconds = (int) (time / 1000);
        if (mShowType) {
            format = String.valueOf(totalSeconds);
        } else {
            int seconds = totalSeconds % 60;
            int minutes = (totalSeconds / 60) % 60;
            int hours = (totalSeconds / 3600) % 24;
            int days = totalSeconds / 3600 / 24;
            if (days > 0) {
                format = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
            } else if (hours > 0) {
                format = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            } else if (minutes > 0) {
                format = String.format("%02d:%02d", minutes, seconds);
            } else {
                format = String.format("%02d:%02d", 0, seconds);
            }
        }
        return format;
    }

    /**
     * 控件自动绑定生命周期,宿主可以是activity或者fragment
     */
    private void autoBindLifecycle(Context context) {
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentManager fm = activity.getSupportFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            for (Fragment fragment : fragments) {
                View parent = fragment.getView();
                if (parent != null) {
                    View find = parent.findViewById(getId());
                    if (find == this) {
                        fragment.getLifecycle().addObserver(this);
                        return;
                    }
                }
            }
        }
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }


}
