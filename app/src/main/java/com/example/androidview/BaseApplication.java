package com.example.androidview;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.androidview.screenadapter.ScreenAutoAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author lgh on 2020/12/11 11:26
 * @description
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAutoAdapter.setup(this);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("555.realm").build();
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
