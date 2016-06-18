package com.pskloud.osm;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.pskloud.osm.service.CustomerJobService;

/**
 * Created by andres on 08/06/16.
 */
public class OsmApplication extends Application{

    private static OsmApplication sInstance;


    public static OsmApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        JobInfo job = new JobInfo.Builder(1, new ComponentName(this, CustomerJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setPeriodic(11250)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(job);
    }

}
