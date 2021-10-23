package com.example.androidview.liveBus;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.databinding.ActivityLivedataBusBinding;

/**
 * @author lgh on 2021/10/22 14:43
 * @description
 */
public class LiveDataBusTestActivity2 extends BaseActivity {

    ActivityLivedataBusBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLivedataBusBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.jump.setOnClickListener(v -> {
            LiveDataBus.get().with("555", Integer.class).postValue(22);
            finish();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LiveEvent", "onStop2222");
    }
}
