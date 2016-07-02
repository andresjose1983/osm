package com.osm.soft.sf.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.MainActivity;
import com.osm.soft.sf.OsmApplication;
import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Locality;
import com.osm.soft.sf.rest.RestClient;
import com.osm.soft.sf.util.LocalitySqlHelper;
import com.osm.soft.sf.util.NotificationHelper;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class LocalitiesService extends IntentService {

    private static final String SERVICE_NAME = "LocalitiesService";

    private LocalitySqlHelper localitySqlHelper;

    public LocalitiesService(){
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localitySqlHelper = new LocalitySqlHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationHelper.show(this, MainActivity.class,  R.string.notification_text_localities,
                NotificationHelper.NOTIFICATION_DOWNLOADING_LOCALITY, true);
        RestClient.GET_LOCALITIES.execute(new Callback<List<Locality>>() {
            @Override
            public void success(List<Locality> localities, Response response) {
                new Thread(() -> {
                    localitySqlHelper.DELETE.execute();
                    for (Locality locality: localities) {
                        if(localitySqlHelper.ADD.execute(locality))
                            if(BuildConfig.DEBUG)
                                Log.i("Inserted",  locality.getName());
                    }
                    NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_LOCALITY);
                    NotificationHelper.show(LocalitiesService.this, MainActivity.class,
                            R.string.notification_locality_downloaded,
                            NotificationHelper.NOTIFICATION_DOWNLOADED_LOCALITY, true);
                }).start();
            }

            @Override
            public void failure(RetrofitError error) {
                NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_LOCALITY);
                NotificationHelper.sendBroadcastError(OsmApplication.getInstance(), error);
            }
        });
    }

    public static boolean isRunning(final Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_NAME.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
