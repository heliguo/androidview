package com.example.androidview.liveBus;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.androidview.databinding.ActivityLivedataBusBinding;

/**
 * @author lgh on 2021/10/22 15:30
 * @description <p/>
 * <p></>switch (this) {
 * <p>       case ON_CREATE:
 * <p>       case ON_STOP:
 * <p>            return State.CREATED;
 * <p>       case ON_START:
 * <p>       case ON_PAUSE:
 * <p>            return State.STARTED;
 * <p>       case ON_RESUME:
 * <p>            return State.RESUMED;
 * <p>       case ON_DESTROY:
 * <p>            return State.DESTROYED;
 * <p>       case ON_ANY:
 * <p>            break;
 * <p>      }
 * <p/>
 *
 * <p>
 * postValue 只是把传进来的数据先存到 mPendingData，然后往主线程抛一个 Runnable，
 * 在这个 Runnable 里面再调用 setValue 来把存起来的值真正设置上去，并回调观察者们。
 * 而如果在这个 Runnable 执行前多次 postValue，其实只是改变暂存的值 mPendingData，
 * 并不会再次抛另一个 Runnable
 * <p/>
 */
public class LiveDataTestActivity extends AppCompatActivity {

    ActivityLivedataBusBinding mBinding;
    private static final String TAG = "LiveDataTestActivity";

    private MutableLiveData<String> mLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLivedataBusBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //liveData基本使用
        mLiveData = new MutableLiveData<>();
        mLiveData.observe(this, s -> Log.i(TAG, "onChanged: " + s));
        Log.i(TAG, "onCreate: ");
        mLiveData.setValue("onCreate");//activity是非活跃状态，不会回调onChanged。变为活跃时，value被onStart中的value覆盖
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        mLiveData.setValue("onStart");//活跃状态，会回调onChanged。并且value会覆盖onCreate、onStop中设置的value
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        mLiveData.setValue("onResume");//活跃状态，回调onChanged
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        mLiveData.setValue("onPause");//活跃状态，回调onChanged
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
        mLiveData.setValue("onStop");//非活跃状态，不会回调onChanged。后面变为活跃时，value被onStart中的value覆盖
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        mLiveData.setValue("onDestroy");//非活跃状态，且此时Observer已被移除，不会回调onChanged
    }
}
