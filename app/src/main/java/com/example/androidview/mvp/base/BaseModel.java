package com.example.androidview.mvp.base;

/**
 * @author lgh on 2021/10/11 17:08
 * @description 接收Presenter交给他的需求
 */
public abstract class BaseModel<P extends BasePresenter, CONTRACT> {//抽象契约类

    protected P p;

    //业务结束，通过Presenter处理结果

    public BaseModel(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}
