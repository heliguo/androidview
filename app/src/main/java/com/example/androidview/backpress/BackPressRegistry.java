package com.example.androidview.backpress;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.LinkedList;

/**
 * @author lgh on 2021/4/1 11:35
 * @description
 */
public class BackPressRegistry {

    private final LinkedList<BackPressObserver> mBackPressObservers = new LinkedList<>();

    public void registerBackPress(LifecycleOwner owner, BackPressObserver backPressObserver) {
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        mBackPressObservers.add(backPressObserver);
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event== Lifecycle.Event.ON_DESTROY){
                    mBackPressObservers.remove(backPressObserver);
                    lifecycle.removeObserver(this);
                }
            }
        });
    }

    public boolean dispatchBackPress() {
        int size = mBackPressObservers.size();
        for (int i = size - 1; i >= 0; i--) {
            BackPressObserver backPressObserver = mBackPressObservers.get(i);
            if (backPressObserver.onBackPress())
                return true;
        }
        return false;
    }

}
