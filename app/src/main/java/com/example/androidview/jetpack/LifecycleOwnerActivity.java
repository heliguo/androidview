package com.example.androidview.jetpack;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author lgh on 2021/3/24 10:43
 * @description
 */
public class LifecycleOwnerActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        getLifecycle().addObserver(new TestObserver());
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    private static class TestObserver implements LifecycleObserver{
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreated(LifecycleOwner owner) {
            //            owner.getLifecycle().addObserver(anotherObserver);
            //            owner.getLifecycle().getCurrentState();
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        void onAny(LifecycleOwner owner, Lifecycle.Event event) {
            //            event.name()
        }
    }
}
