package com.example.androidview.rootview;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author lgh on 2020/10/29 17:16
 * @description 静默添加组件
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CreateWidget extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppWidgetManager appWidgetManager =
                getSystemService(AppWidgetManager.class);
        ComponentName myProvider =
                new ComponentName(this, NewAppWidget.class);

        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the widget to be pinned. Note that, if the pinning
            // operation fails, your app isn't notified.
            Intent pinnedWidgetCallbackIntent = new Intent();

            // Configure the intent so that your app's broadcast receiver gets
            // the callback successfully. This callback receives the ID of the
            // newly-pinned widget (EXTRA_APPWIDGET_ID).
            PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                    pinnedWidgetCallbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            appWidgetManager.requestPinAppWidget(myProvider, null, successCallback);
        }

        //        Intent resultValue = new Intent();
        //        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        //
        //        setResult(RESULT_OK, resultValue);
        //        finish();
    }
}
