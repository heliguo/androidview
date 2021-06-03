package com.example.androidview.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/4/15 10:24
 * @description
 */
public class ParallaxFragment extends Fragment {

    private List<View> parallaxViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int layoutId = arguments.getInt("layoutId");
        ParallaxLayoutInflater parallaxInflater = new ParallaxLayoutInflater(inflater, getContext(),this);
        return parallaxInflater.inflate(layoutId,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public List<View> getParallaxViews() {
        return parallaxViews;
    }
}
