package com.example.androidview.TabLayout.Listener;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @创建者 lgh
 * @创建时间 2019-08-20 11:36
 * @描述
 */
public abstract class CollapsedStateListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State{

        EXPANDED,//展开
        COLLAPSED,//折叠
        IDLE //中间状态

    }

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

   public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
