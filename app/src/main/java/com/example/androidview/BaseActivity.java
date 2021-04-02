package com.example.androidview;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidview.backpress.BackPressObserver;
import com.example.androidview.backpress.BackPressRegistry;

/**
 * @author lgh on 2021/4/1 12:08
 * @description
 */
public class BaseActivity extends AppCompatActivity {

    private final BackPressRegistry mBackPressRegistry = new BackPressRegistry();
    private Toast mToast;

    public void registerBackPress(LifecycleOwner owner, BackPressObserver backPressObserver) {

        mBackPressRegistry.registerBackPress(owner, backPressObserver);

    }

    @Override
    public void onBackPressed() {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "you click back press", Toast.LENGTH_SHORT);
        mToast.show();
        if (mBackPressRegistry.dispatchBackPress())
            return;
        super.onBackPressed();
    }
}
