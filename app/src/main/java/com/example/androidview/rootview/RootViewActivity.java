
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.androidview.R;
import com.example.androidview.view.HorizontalScrollActivity;

/**
 * @author lgh
 * @description Notification + RootView
 */
public class RootViewActivity extends AppCompatActivity {

    private static final String TAG = "RootViewActivity";
    private static final String DEFAULT_CHANNEL_ID = "default_id";
    private static final String DEFAULT_CHANNEL_NAME = "default_channel_name";
    private static final int DEFAULT_NOTIFY_ID = 1;
    private RemoteViews mRemoteView;
    private Notification mNotification;
    private NotificationManager nm;


    //    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_view);

        findViewById(R.id.button).setOnClickListener(v -> {
            startService(new Intent(this,ResidentNotificationService.class));

            Intent intent = new Intent(this, HorizontalScrollActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 20, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mRemoteView = new RemoteViews(getPackageName(), R.layout.rootview);
            mRemoteView.setTextViewText(R.id.rootview_tv, getPackageName());
            mRemoteView.setImageViewResource(R.id.rootview_iv, R.drawable.icon_float);
            //根据不同id传入不同PendingIntent即可
            mRemoteView.setOnClickPendingIntent(R.id.rootview_ll, pi);


            nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName());
            builder.setSmallIcon(R.drawable.icon_float)
                    .setContentTitle("title")
                    .setContentText("text")
                    .setContent(mRemoteView)

                    //                    .setAutoCancel(true)//滑动取消
                    .setContentIntent(pi)
                    .setWhen(System.currentTimeMillis())
                    .setChannelId(getPackageName()) //必须添加（Android 8.0） 【唯一标识】
                    .build();
            mNotification = builder.build();
            mNotification.flags = Notification.FLAG_ONGOING_EVENT;
            //android 9.0 必须
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        getPackageName(),
                        "会话消息",
                        NotificationManager.IMPORTANCE_DEFAULT

                );

                nm.createNotificationChannel(channel);
            }
            nm.notify(DEFAULT_NOTIFY_ID, mNotification);
        });

        findViewById(R.id.button7).setOnClickListener(v -> {
            mRemoteView.setTextViewText(R.id.rootview_tv, "888888888");
            nm.notify(DEFAULT_NOTIFY_ID, mNotification);
        });


    }
}