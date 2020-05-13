package com.example.androidview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.ntp.SntpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("LOG", "requesting time..");

                Date dt = SntpUtils.getUTCDate();
                Log.e("LOG", "dt: " + dt.toString());

                long ts = SntpUtils.getUTCTimestamp();
                SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                String format1 = format.format(ts);
                String format2 = format.format(System.currentTimeMillis());
                Log.e("LOG", "时间: " + format1);
                Log.e("LOG", "时间2: " + format2);
                Log.e("LOG", "ts: " + ts);
            }
        }).start();

    }
}
