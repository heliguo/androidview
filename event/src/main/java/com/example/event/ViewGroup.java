package com.example.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/4/14 17:15
 * @description
 */
public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();

    private View[] mChildView = new View[0];

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildView = childList.toArray(new View[childList.size()]);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        boolean intercept = onInterceptTouchEvent(event);

        final View[] views = mChildView;
        int actionMasked = event.getActionMasked();

        if (actionMasked!=MotionEvent.ACTION_CANCEL&&!intercept){
            for (int i = views.length - 1; i >= 0; i--) {
                View child = views[i];
                if (child.isContainer(event.getX(),event.getY())){
                    dispatchTransformTargetTouchEvent(event,child);
                }
            }
        }


        if (!intercept) {
            return true;
        }

        return super.dispatchTouchEvent(event);
    }


    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }


}
