package com.example.androidview.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

/**
 * @author lgh
 */
public class FrameAnimationActivity extends AppCompatActivity {

    private Button mButton;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        mButton = findViewById(R.id.button2);
        //        button.setBackgroundResource(R.drawable.frame_animation);
        //        AnimationDrawable background = (AnimationDrawable) button.getBackground();
        //        background.start();
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.property_animator);
        animatorSet.setTarget(mButton);
        animatorSet.start();
        ListView listView = (ListView) findViewById(R.id.list);
        //        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        //        LayoutAnimationController controller = new LayoutAnimationController(animation);
        //        controller.setDelay(0.5f);
        //        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //        listView.setLayoutAnimation(controller);
        /**
         * activity 入场和出场动画
         */
        //        overridePendingTransition();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator.ofInt(mButton, "width", 500).setDuration(5000).start();
                //                performAnimate();
            }
        });

    }

    private void performAnimate() {
        ViewWrapper wrapper = new ViewWrapper(mButton);
        ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getHeight() {
            return mTarget.getLayoutParams().height;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }
}