package com.example.androidview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/28 9:34
 * @description
 */
public class SystemExitService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);//该服务会重启
        /**
         * 假设我们延迟启动app，如下，延迟10秒
         *     Intent mStartActivity = new Intent(activity, XXXActivity.class);
         *     int mPendingIntentId = 123;
         *     PendingIntent mPendingIntent = PendingIntent.getActivity(activity, mPendingIntentId,
         *     mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
         *     AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
         *     mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, mPendingIntent);
         *
         * 如果没有START_STICKY的Service，那么application也是在10秒后启动的，如果有START_STICKY的Service，
         * 那么application是立马启动，并且把Service也重启，之后才是等待10秒加载XXXActivity的。
         *
         * 另一种杀进程的方法
         * android.os.Process.killProcess(android.os.Process.myPid());
         *等同于
         * finishAffinity();
         * System.exit(0);
         */
    }
}
