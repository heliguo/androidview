package com.example.androidview.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/4/14 11:37
 * @description
 */
public class VSNYCManager {

    private static final VSNYCManager VSNYC_MANAGER = new VSNYCManager();

    private VSNYCManager() {
        new Thread(mRunnable).start();
    }

    public static VSNYCManager getInstance() {
        return VSNYC_MANAGER;
    }

    private List<AnimationFrameCallBack> mCallBacks = new ArrayList<>();

    private Runnable mRunnable = () -> {
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (AnimationFrameCallBack callBack : mCallBacks) {
                callBack.doAnimationFrame(System.currentTimeMillis());
            }

        }
    };

    public void add(AnimationFrameCallBack callBack) {
        if (!mCallBacks.contains(callBack)) {
            mCallBacks.add(callBack);
        }
    }

    public void remove(AnimationFrameCallBack callBack) {
        if (mCallBacks.contains(callBack)) {
            mCallBacks.remove(callBack);
        }
    }

    public interface AnimationFrameCallBack {
        boolean doAnimationFrame(long currentTime);
    }
}
