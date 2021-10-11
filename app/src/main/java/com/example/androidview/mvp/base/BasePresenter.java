package com.example.androidview.mvp.base;

import java.lang.ref.WeakReference;

/**
 * @author lgh on 2021/10/11 17:21
 * @description
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel, CONTRACT> {

    private M m;

    public BasePresenter() {
        m = getModel();
    }

    private WeakReference<V> mVWeakReference;

    public void bindView(V v) {
        mVWeakReference = new WeakReference<>(v);
    }

    public void unbindView() {
        if (mVWeakReference != null) {
            mVWeakReference.clear();
            mVWeakReference = null;
            System.gc();
        }
    }

    public V getView() {
        if (mVWeakReference != null) {
            return mVWeakReference.get();
        }
        return null;
    }

    public abstract M getModel();

    public abstract CONTRACT getContract();

}
