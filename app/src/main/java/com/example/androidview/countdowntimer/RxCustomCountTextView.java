package com.example.androidview.countdowntimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * author:lgh on 2020-04-30 16:56
 * RxJava 计时
 */
public class RxCustomCountTextView extends AppCompatTextView implements LifecycleObserver {

    private static final long DEFAULT_INTERVAL = 1000;//默认计时间隔为 1s

    private OnCountDownStartListener mOnCountDownStartListener;

    private long mInitTime;//开始计时的时间 long

    private boolean isRunning;

    private long mTimeInterval = DEFAULT_INTERVAL;

    private long currentTime;

    private boolean mCountType;//false s true ms

    private boolean mShowType;//false time true 计数

    private Disposable mSubscribe;

    public RxCustomCountTextView(Context context) {
        this(context, null);
    }

    public RxCustomCountTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxCustomCountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxCustomCountTextView);
        mCountType = typedArray.getInt(R.styleable.RxCustomCountTextView_rxCountType, 10) != 10;
        mShowType = typedArray.getInt(R.styleable.RxCustomCountTextView_RxShowType, 20) != 20;
        typedArray.recycle();
        autoBindLifecycle(context);
    }

    public void count() {
        /**
         * 倒计时
         */
        if (mInitTime == 0) {
            setText("请设置初始计时时间");
            return;
        }
        if (mTimeInterval % 1000 == 0) {
            mCountType = false;
        }

        setText(generateTime(mInitTime));

        currentTime = mInitTime;
        mSubscribe = Observable.interval(mTimeInterval, TimeUnit.MILLISECONDS)
                .take(mInitTime / mTimeInterval + 1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (currentTime <= 0) {
                        mSubscribe.dispose();
                        if (mOnCountDownStartListener != null) {
                            mOnCountDownStartListener.onFinish();
                        }
                        setText(generateTime(0));
                    } else {
                        currentTime = currentTime - mTimeInterval;
                        setText(generateTime(currentTime));
                    }
                });

    }

    public RxCustomCountTextView setTimeInterval(long timeInterval) {
        mTimeInterval = timeInterval;
        return this;
    }

    public RxCustomCountTextView setCountType(boolean countType) {
        mCountType = countType;
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
    public RxCustomCountTextView setInitTime(long time) {
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

    public RxCustomCountTextView setShowType(boolean showType) {
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
            if (mCountType) {
                int ms = (int) (time % 1000 / mTimeInterval);
                format = format + ":" + (ms == 0 ? "00" : ms);
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
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.isDisposed();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }


}
