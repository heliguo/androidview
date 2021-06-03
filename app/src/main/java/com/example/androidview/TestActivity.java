package com.example.androidview;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author lgh on 2020/9/4 11:58
 * @description int 转 char[]
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.e(TAG, "onCreate: +" + Integer.MAX_VALUE);
        int c = 152322;
        char[] chars = toChar(new char[stringSize(c)], c, 0);
        for (char aChar : chars) {
            Log.e(TAG, "onCreate: " + aChar);
        }

    }

    private char[] toChar(char[] v, int src, int i) {
        if (src == 0)
            return v;
        int a = src / 10;
        int b = src % 10;
        if (a >= 0) {
            v[i] = (char) (b + '0');
            i++;
            toChar(v, a, i);
        }
        return v;
    }

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    // Requires positive x
    static int stringSize(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "onKeyDown: ");
        }
        return super.onKeyDown(keyCode, event);
    }


}
