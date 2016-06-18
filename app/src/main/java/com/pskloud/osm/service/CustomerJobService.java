package com.pskloud.osm.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.OsmApplication;
import com.pskloud.osm.util.Functions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mendez Fernandez on 18/06/2016.º
 */
public class CustomerJobService extends Service {

    public static final long NOTIFY_INTERVAL = 11250;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new Synchronize(), 0, NOTIFY_INTERVAL);
    }

    class Synchronize extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(() -> {
                if(BuildConfig.DEBUG)
                    Log.i("Hola", "Se ejecuto");
                if(Functions.getStatus(OsmApplication.getInstance()) &&
                        Functions.checkInternetConnection(OsmApplication.getInstance())){
                    if(BuildConfig.DEBUG) {
                        Log.i("Hola", "Llamar al servidor");
                    }
                }
            });
        }
    }
}
