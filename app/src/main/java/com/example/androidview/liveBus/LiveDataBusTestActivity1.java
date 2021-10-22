package com.example.androidview.liveBus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.databinding.ActivityLivedataBusBinding;

/**
 * @author lgh on 2021/10/22 14:43
 * @description
 */
public class LiveDataBusTestActivity1 extends BaseActivity {

    ActivityLivedataBusBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLivedataBusBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.jump.setOnClickListener(v -> startActivity(new Intent(this, LiveDataBusTestActivity2.class)));

        LiveDataBus.get().with("555", Integer.class).observe(this, integer -> finish());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LiveEvent", "onStop1111");
    }
}
