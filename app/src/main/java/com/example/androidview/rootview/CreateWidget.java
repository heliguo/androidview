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
 * @author lgh on 2020/10/29 17:42
 * @description 向桌面添加组件，需要Android8.0以上版本
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateWidget extends Activity {

    int mAppWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

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

        // return OK
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

        setResult(RESULT_OK, resultValue);
        finish();


    }

}
