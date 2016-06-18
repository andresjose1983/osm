package com.pskloud.osm.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.OsmApplication;
import com.pskloud.osm.util.Functions;

/**
 * Created by Mendez Fernandez on 18/06/2016.º
 */
public class CustomerJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if(BuildConfig.DEBUG)
            Log.i("Hola", "Se ejecuto");
        if(Functions.getStatus(OsmApplication.getInstance()) &&
                Functions.checkInternetConnection(OsmApplication.getInstance())){
            if(BuildConfig.DEBUG) {
                Log.i("Hola", "Llamar al servidor");
            }
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i("Hola", "Se termino");
        return false;
    }
}
