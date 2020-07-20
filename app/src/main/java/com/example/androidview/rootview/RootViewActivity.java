
package com.example.androidview.rootview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.androidview.HorizontalScrollActivity;
import com.example.androidview.R;

/**
 * @author lgh
 * @description Notification + RootView
 */
public class RootViewActivity extends AppCompatActivity {

    private static final String TAG = "RootViewActivity";
    private static final String DEFAULT_CHANNEL_ID = "default_id";
    private static final String DEFAULT_CHANNEL_NAME = "default_channel_name";
    private static final int DEFAULT_NOTIFY_ID = 1;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_view);

        findViewById(R.id.button).setOnClickListener(v -> {

            Intent intent = new Intent(this, HorizontalScrollActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 20, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.rootview);
            remoteView.setTextViewText(R.id.rootview_tv, getPackageName());
            remoteView.setImageViewResource(R.id.rootview_iv, R.drawable.back_out_b);
            remoteView.setOnClickPendingIntent(R.id.rootview_ll, pi);

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName());
            builder.setSmallIcon(R.drawable.back_out_b)
                    .setContentTitle("title")
                    .setContentText("text")
                    .setContent(remoteView)
                    .setAutoCancel(true)//滑动取消
                    .setContentIntent(pi)
                    .setWhen(System.currentTimeMillis())
                    .setChannelId(getPackageName()) //必须添加（Android 8.0） 【唯一标识】
                    .build();
            //android 9.0 必须
            NotificationChannel channel = new NotificationChannel(
                    getPackageName(),
                    "会话消息",
                    NotificationManager.IMPORTANCE_DEFAULT

            );

            Notification notification = builder.build();
            nm.createNotificationChannel(channel);
            nm.notify(DEFAULT_NOTIFY_ID, notification);
        });


    }
}