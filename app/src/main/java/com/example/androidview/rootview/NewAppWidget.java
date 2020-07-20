package com.example.androidview.rootview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.androidview.R;

import java.util.Objects;

/**
 * @author lgh
 * Implementation of App Widget functionality.
 * intent 需要加上 intentClick.setClass(context,NewAppWidget.class);
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String TAG = "android.appwidget.action.CLICK";

    public NewAppWidget() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // 这里判断是自己的action，做自己的事情，比如小部件被单击了要干什么，这里是做
        //一个动画效果
        if (Objects.equals(intent.getAction(), TAG)) {
            Toast.makeText(context, "clicked it", Toast.LENGTH_SHORT).show();

            new Thread(() -> {

                Bitmap srcBitmap = BitmapFactory.decodeResource(
                        context.getResources(), R.drawable.icon_float);

                AppWidgetManager appWidgetManager = AppWidgetManager.
                        getInstance(context);

                for (int i = 0; i < 37; i++) {
                    float degree = (i * 10) % 360;
                    RemoteViews remoteViews = new RemoteViews(context
                            .getPackageName(), R.layout.new_app_widget);
                    remoteViews.setImageViewBitmap(R.id.appwidget_iv,
                            rotateBitmap(context, srcBitmap, degree));

                    Intent intentClick = new Intent();
                    intentClick.setClass(context, NewAppWidget.class);
                    intentClick.setAction(TAG);
                    PendingIntent pendingIntent = PendingIntent
                            .getBroadcast(context, 0, intentClick, 0);
                    remoteViews.setOnClickPendingIntent(R.id.appwidget_iv,
                            pendingIntent);
                    appWidgetManager.updateAppWidget(new ComponentName(
                                    context, NewAppWidget.class),
                            remoteViews);
                    SystemClock.sleep(30);
                }

            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void onWidgetUpdate(Context context,
                                AppWidgetManager appWidgeManger, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.new_app_widget);
        // “桌面小部件”单击事件发送的Intent广播
        Intent intentClick = new Intent();
        intentClick.setClass(context, NewAppWidget.class);
        intentClick.setAction(TAG);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_iv, pendingIntent);
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcbBitmap, float
            degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(srcbBitmap, 0, 0,
                srcbBitmap.getWidth(), srcbBitmap.getHeight(), matrix, true);
    }
}

