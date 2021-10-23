package com.example.androidview.aidl;

import android.os.IBinder;

/**
 * @author lgh on 2021/9/8 11:16
 * @description
 */
public interface IBinderPool {

    IBinder queryBinder(int binderCode);
}
