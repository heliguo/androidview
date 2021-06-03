package com.example.androidview.backpress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/1 15:41
 * @description
 */
public class BackPressHomeFragment extends Fragment {

    private boolean backPress;
    private boolean enableBack=true;

    public static BackPressHomeFragment newInstance(){
        return new BackPressHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_home_back_press,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        BackPressSecondFragment backPressSecondFragment = new BackPressSecondFragment();
        transaction.replace(R.id.fl_second,backPressSecondFragment,"BackPressSecondFragment");
        transaction.commit();
        view.findViewById(R.id.disable_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress = true;
            }
        });
        view.findViewById(R.id.enable_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress = false;
            }
        });
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).registerBackPress(this, new BackPressObserver() {
                @Override
                public boolean onBackPress() {
                    return backPress;
                }
            });
        }
        view.findViewById(R.id.remove_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.remove(backPressSecondFragment);
                transaction1.commit();
            }
        });
        getActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                Log.e("TAGTAGTAGTAGTAG", "BackPressHomeFragment handleOnBackPressed: " );
            }
        });
    }
}
