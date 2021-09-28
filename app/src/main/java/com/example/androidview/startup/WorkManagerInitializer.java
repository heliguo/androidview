package com.example.androidview.startup;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/9/27 16:40
 * @description
 */
public class WorkManagerInitializer implements Initializer<WorkManager> {

    @NonNull
    @NotNull
    @Override
    public WorkManager create(@NonNull @NotNull Context context) {
        Configuration configuration = new Configuration.Builder().build();
        WorkManager.initialize(context, configuration);
//        AppInitializer.getInstance(context).initializeComponent(WorkManagerInitializer.class);
        return WorkManager.getInstance(context);
    }

    @NonNull
    @NotNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList<>();
    }
}
