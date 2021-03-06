package com.example.androidview.recyclerview.swipemenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/4/9 17:24
 * @description
 */
public class SwipeMenuObserver {

    private final List<SwipeMenuLayout> mSwipeMenuLayouts = new ArrayList<>();

    private SwipeMenuObserver() {
    }

    private static class SingleHolder {
        private static final SwipeMenuObserver OBSERVER = new SwipeMenuObserver();
    }

    public static SwipeMenuObserver getInstance() {
        return SingleHolder.OBSERVER;
    }

    public boolean closeMenu() {
        if (mSwipeMenuLayouts.isEmpty()) {
            return false;
        }
        mSwipeMenuLayouts.remove(mSwipeMenuLayouts.size() - 1).smoothCloseMenu();
        return true;
    }

    public List<SwipeMenuLayout> getSwipeMenuLayouts() {
        return mSwipeMenuLayouts;
    }
}
