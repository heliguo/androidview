package com.example.androidview.rootview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.androidview.R;

/**
 * @author lgh on 2020/10/19:19:54
 * @description
 */
public class ResidentNotificationService extends Service {

    public static final int RESIDENT_NOTIFICATION_REQUEST_CODE = 1001;
    private static final int DEFAULT_NOTIFY_ID = 1000;
    private static final String DEFAULT_CHANNEL_NAME = "RESIDENT_NOTIFICATION";

    private RemoteViews mRemoteViews;

    private NotificationManager mManager;
    private Notification mNotification;

    private int num;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 10001) {
                if (num<10000){
                    num++;
                    mRemoteViews.setTextViewText(R.id.text11,String.valueOf(num));
                }
                refresh();//不断刷新，保证通知不被销毁
                mHandler.sendEmptyMessageDelayed(10001, 1000);
            }
            return false;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        startForeground(DEFAULT_NOTIFY_ID, mNotification);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        Context packageContext = getApplicationContext();
        mRemoteViews = new RemoteViews(packageContext.getPackageName(), R.layout.notification_resident);
        Intent intent = new Intent(packageContext, RootViewActivity.class);

        PendingIntent pi = PendingIntent.getActivity(packageContext, RESIDENT_NOTIFICATION_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.notification_resident_root, pi);
        mManager = (NotificationManager) packageContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(packageContext, packageContext.getPackageName());
        mNotification = builder.setCustomBigContentView(mRemoteViews)
                .setSmallIcon(R.drawable.icon_float)
                .setContentText("content")
                .setShowWhen(true)
                .setContentTitle("title")
                .setGroup("group")
                .setAutoCancel(false)
                .setContentIntent(pi)
                .setChannelId(packageContext.getPackageName())
                .build();
        mNotification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        mNotification.bigContentView = mRemoteViews;//解决自适应布局不能完全展示问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    packageContext.getPackageName(),
                    DEFAULT_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            mManager.createNotificationChannel(channel);
        }
//        mManager.notify(DEFAULT_NOTIFY_ID, mNotification);

        mHandler.sendEmptyMessage(10001);
    }

    private void refresh() {
        mManager.notify(DEFAULT_NOTIFY_ID, mNotification);
    }

}
