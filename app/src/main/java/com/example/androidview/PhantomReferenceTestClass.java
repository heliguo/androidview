package com.example.androidview;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author lgh on 2020/5/25 8:47
 * @description 强软弱虚
 */
public class PhantomReferenceTestClass {

    ReferenceQueue   mQueue            = new ReferenceQueue();
    PhantomReference mPhantomReference = new PhantomReference(this, mQueue);
    SoftReference    mSoftReference    = new SoftReference(this);
    WeakReference    mWeakReference    = new WeakReference(this);

}
