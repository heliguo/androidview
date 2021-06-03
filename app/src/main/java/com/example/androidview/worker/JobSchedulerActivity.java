package com.example.androidview.worker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

import java.util.List;

import static com.example.androidview.worker.JobSchedulerTest.WORK_DURATION_KEY;

/**
 * @author lgh on 2020/11/13 16:26
 * @description
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerActivity extends AppCompatActivity {

    JobSchedulerTest mSchedulerTest;
    private int mJobId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_scheduler);

    }

    public void jobScheduler(View view) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this, JobSchedulerTest.class));

        //设置至少延迟多久后执行，单位毫秒
        builder.setMinimumLatency(3 * 1000);

        //设置最多延迟多久后执行，单位毫秒。
        builder.setMinimumLatency(5 * 1000);

        //设置需要的网络条件，有三个取值：
        //JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
        //JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
        //JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);

        //是否在空闲时执行
        builder.setRequiresDeviceIdle(true);

        //是否在充电时执行
        builder.setRequiresCharging(true);

        builder.setBackoffCriteria(1000, JobInfo.BACKOFF_POLICY_EXPONENTIAL);//退避策略 , 可以设置等待时间以及重连策略

        // Extras, work duration.
        PersistableBundle extras = new PersistableBundle();
        extras.putLong("WORK_DURATION_KEY", 5 * 1000);
        builder.setExtras(extras);

        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());
    }


    /**
     * Executed when user clicks on SCHEDULE JOB.
     */
    public void scheduleJob(View v) {
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++, new ComponentName(this,JobSchedulerTest.class));

        builder.setMinimumLatency(5 * 1000);
        builder.setOverrideDeadline(2 * 1000);
        boolean requiresUnmetered = false;
        boolean requiresAnyConnectivity = true;
        if (requiresUnmetered) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }

        // Extras, work duration.
        PersistableBundle extras = new PersistableBundle();
        extras.putLong(WORK_DURATION_KEY, 5 * 1000);

        builder.setExtras(extras);

        // Schedule job
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());
    }

    public void cancelAllJobs(View v) {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
        Toast.makeText(this, "R.string.all_jobs_cancelled", Toast.LENGTH_SHORT).show();
    }

    /**
     * Executed when user clicks on FINISH LAST TASK.
     */
    public void finishJob(View v) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        if (allPendingJobs.size() > 0) {
            // Finish the last one
            int jobId = allPendingJobs.get(0).getId();
            jobScheduler.cancel(jobId);
            Toast.makeText(
                    this, "" + jobId,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(
                    this, " getString",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
