package com.example.androidview.worker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.androidview.R;

import java.util.List;

/**
 * @author lgh on 2020/11/13 17:09
 * @description
 */
public class WorkerManagerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_manager);
        WorkManager.getInstance(this).getWorkInfosByTagLiveData("tag").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {

                for (WorkInfo workInfo : workInfos) {

                    String string = workInfo.getOutputData().getString("output_data");
                }

            }
        });
        WorkManager.getInstance(this).cancelAllWork();
        WorkManager.getInstance(this).cancelAllWorkByTag("tag");


    }
}
