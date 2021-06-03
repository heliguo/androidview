package com.example.androidview.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidview.R;

/**
 * @author lgh on 2021/4/15 10:51
 * @description
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    private final ParallaxFragment mFragment;


    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        this.mFragment = fragment;
        setFactory2(new ParallaxFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext, mFragment);
    }

    class ParallaxFactory implements Factory2 {

        private final String[] prefixs = new String[]{
                "android.widget.",
                "android.view."
        };

        LayoutInflater mLayoutInflater;

        private final int[] attrIds = new int[]{
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out,
                R.attr.a_in,
                R.attr.a_out
        };

        public ParallaxFactory(LayoutInflater layoutInflater) {
            mLayoutInflater = layoutInflater;
        }

        @SuppressLint("ResourceType")
        @Nullable
        @Override
        public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            View view = createMyView(name, attrs);
            if (view != null) {
                TypedArray array = context.obtainStyledAttributes(attrs, attrIds);
                if (array != null && array.length() > 0) {
                    ParallaxViewTag tag = new ParallaxViewTag();
                    tag.xIn = array.getFloat(0, 0);
                    tag.xOut = array.getFloat(1, 0);
                    tag.yIn = array.getFloat(2, 0);
                    tag.yOut = array.getFloat(3, 0);
                    tag.alphaIn = array.getFloat(4, 0);
                    tag.alphaOut = array.getFloat(5, 0);
                    view.setTag(R.id.parallax_view_tag, tag);
                }
                mFragment.getParallaxViews().add(view);
                array.recycle();
            }
            return view;
        }

        private View createMyView(String name, AttributeSet attrs) {
            if (name.contains(".")) {
                return reflectView(name, null, attrs);
            } else {
                for (String prefix : prefixs) {
                    View view = reflectView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            }
        }

        private View reflectView(String name, String prefix, AttributeSet attrs) {
            try {
                return mLayoutInflater.createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Nullable
        @Override
        public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return null;
        }
    }


}
