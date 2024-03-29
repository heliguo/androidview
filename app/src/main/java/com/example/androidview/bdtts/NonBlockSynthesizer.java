package com.example.androidview.bdtts;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * 在新线程中调用initTTs方法。防止UI柱塞
 * <p>
 * Created by fujiayi on 2017/5/24.
 */

public class NonBlockSynthesizer extends MySynthesizer {

    private static NonBlockSynthesizer INSTANCE;

    private static final int INIT = 1;

    private static final int RELEASE = 11;
    private HandlerThread hThread;
    private Handler tHandler;


    private static final String TAG = "NonBlockSynthesizer";

    public static NonBlockSynthesizer getInstance(Context context, InitConfig initConfig, Handler mainHandler) {
        if (isInitied) {
            return INSTANCE;
        }
        INSTANCE = new NonBlockSynthesizer(context, initConfig, mainHandler);
        return INSTANCE;
    }

    public static NonBlockSynthesizer getInstance() {
        if (isInitied) {
            return INSTANCE;
        }
        throw new IllegalStateException("you should call the getInstance() with params");
    }

    private NonBlockSynthesizer(Context context, InitConfig initConfig, Handler mainHandler) {
        super(context, mainHandler);
        initThread();
        runInHandlerThread(INIT, initConfig);
    }


    protected void initThread() {
        hThread = new HandlerThread("NonBlockSynthesizer-thread");
        hThread.start();
        tHandler = new Handler(hThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case INIT:
                        InitConfig config = (InitConfig) msg.obj;
                        boolean isSuccess = init(config);
                        if (isSuccess) {
                            // speak("初始化成功");
                            sendToUiThread("NonBlockSynthesizer 初始化成功");
                        } else {
                            sendToUiThread("合成引擎初始化失败, 请查看日志");
                        }
                        break;
                    case RELEASE:
                        NonBlockSynthesizer.super.release();
                        if (Build.VERSION.SDK_INT < 18) {
                            getLooper().quit();
                        }
                        break;
                    default:
                        break;
                }

            }
        };
    }

    @Override
    public void release() {
        runInHandlerThread(RELEASE);
        if (Build.VERSION.SDK_INT >= 18) {
            hThread.quitSafely();
        }
    }


    private void runInHandlerThread(int action) {
        runInHandlerThread(action, null);
    }

    private void runInHandlerThread(int action, Object obj) {
        Message msg = Message.obtain();
        msg.what = action;
        msg.obj = obj;
        tHandler.sendMessage(msg);
    }

}
