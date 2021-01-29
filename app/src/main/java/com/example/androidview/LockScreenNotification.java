package com.example.androidview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * @author lgh on 2021/1/28 19:15
 * @description
 */
public class LockScreenNotification {

    private NotificationManager mManager;
    private Context mContext;

    public LockScreenNotification(Context context) {
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mContext = context;
    }


    public void send(String title, String content, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, mContext.getPackageName());
        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mContext.getString(R.string.app_name))
                .setShowWhen(true)
                .setContentTitle(title)
                .setGroup(String.valueOf(notificationId))
                .setSound(null)//去除声音
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setChannelId(String.valueOf(notificationId))
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    String.valueOf(notificationId),
                    mContext.getPackageName(),
                    NotificationManager.IMPORTANCE_HIGH
            );
            mManager.createNotificationChannel(channel);
        }
        mManager.notify(notificationId, notification);
    }

}
