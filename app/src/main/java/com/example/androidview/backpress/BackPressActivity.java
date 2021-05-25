package com.example.androidview.backpress;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/1 15:36
 * @description
 */
public class BackPressActivity extends BaseActivity {

    private boolean enableBack=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_press);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl, BackPressHomeFragment.newInstance(), "BackPressHomeFragment");
        transaction.commit();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                Log.e("TAGTAGTAGTAGTAG", "BackPressActivity handleOnBackPressed: " );
                if (!enableBack){
                    finish();
                }
                enableBack = false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("TAGTAGTAGTAGTAG", "onWindowFocusChanged: "+hasFocus+"\n"+ getSupportFragmentManager().getFragments().size() );
    }
}
