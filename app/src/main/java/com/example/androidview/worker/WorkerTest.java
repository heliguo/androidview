package com.example.androidview.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lgh on 2020/11/13 17:38
 * @description
 */
public class WorkerTest extends Worker {

    Constraints mConstraints = new Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build();

    Data data = new Data.Builder().putString("kdkdk", "5222").build();

    //需要注意的是：周期性任务的间隔时间不能小于15分钟。
    PeriodicWorkRequest mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(WorkerTest.class, 15, TimeUnit.MINUTES)
            .setConstraints(mConstraints)
            .addTag("tag")
            .build();

    //重试该任务。可以在Worker的doWork()方法中返回Result.retry(),也可以通过setBackoffCriteria()方法
    OneTimeWorkRequest mOneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerTest.class)
            .setConstraints(mConstraints)//触发条件
            .setInitialDelay(10, TimeUnit.SECONDS)//符合触发条件后，延迟10秒执行
            .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)//设置指数退避算法
            .addTag("tag")//添加标签跟踪任务
            .setInputData(data)
            .build();

    public WorkerTest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        WorkManager.getInstance(context).enqueue(mOneTimeWorkRequest);
        WorkManager.getInstance(context).beginWith(mOneTimeWorkRequest).then(mOneTimeWorkRequest).enqueue();//任务链
        ListenableFuture<List<WorkInfo>> tag = WorkManager.getInstance(context).getWorkInfosByTag("tag");
        LiveData<List<WorkInfo>> tag1 = WorkManager.getInstance(context).getWorkInfosByTagLiveData("tag");

    }

    /**
     * 耗时的任务，在doWork()方法中执行
     * 执行成功返回Result.success()
     * 执行失败返回Result.failure()
     * 需要重新执行返回Result.retry()
     */

    @NonNull
    @Override
    public Result doWork() {
        //接收外面传递进来的数据
        String inputData = getInputData().getString("input_data");

        // 任务执行完成后返回数据
        Data outputData = new Data.Builder().putString("output_data", "Task Success!").build();

        return Result.success(outputData);
    }
}
