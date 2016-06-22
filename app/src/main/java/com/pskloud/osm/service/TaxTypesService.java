package com.pskloud.osm.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.OsmApplication;
import com.pskloud.osm.R;
import com.pskloud.osm.model.Locality;
import com.pskloud.osm.rest.RestClient;
import com.pskloud.osm.util.LocalitySqlHelper;
import com.pskloud.osm.util.NotificationHelper;
import com.pskloud.osm.util.TaxTypesSqlHelper;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class TaxTypesService extends IntentService {

    private static final String SERVICE_NAME = "TaxTypesService";

    private TaxTypesSqlHelper taxTypesSqlHelper;

    public TaxTypesService(){
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        taxTypesSqlHelper = new TaxTypesSqlHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationHelper.show(this, R.string.notification_text_tax_types,
                NotificationHelper.NOTIFICATION_DOWNLOADING_TAX_TYPES);
        RestClient.GET_TAX_TYPES.getResponse(new Callback<Map<String, Integer>>() {
            @Override
            public void success(Map<String, Integer> map, Response response) {
                new Thread(() -> {
                    taxTypesSqlHelper.DELETE.execute();
                    for (Map.Entry<String, Integer> entry : map.entrySet()){
                        if(taxTypesSqlHelper.ADD.execute(entry.getValue(), entry.getKey()))
                            if(BuildConfig.DEBUG)
                                Log.i("Inserted", entry.getKey());
                    }
                    NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_TAX_TYPES);
                    NotificationHelper.show(OsmApplication.getInstance(),
                            R.string.notification_locality_tax_types, NotificationHelper.NOTIFICATION_DOWNLOADED_TAX_TYPES);
                }).start();
            }

            @Override
            public void failure(RetrofitError error) {
                if(BuildConfig.DEBUG)
                    Log.e("Se jodio", error.getMessage());
                NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_TAX_TYPES);
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