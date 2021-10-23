package com.example.androidview.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;

/**
 * @author lgh on 2021/10/11 17:24
 * @description view 基层
 */
public abstract class BaseView<P extends BasePresenter, CONTRACT> extends BaseActivity {

    protected P mP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弱引用
        mP = getPresenter();
        //绑定
        mP.bindView(this);
    }

    public abstract CONTRACT getContract();

    public abstract P getPresenter();

    public void error(Exception e){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mP.unbindView();
    }
}
