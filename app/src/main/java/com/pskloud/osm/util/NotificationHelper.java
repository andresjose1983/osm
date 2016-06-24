package com.pskloud.osm.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.DefaultActivity;
import com.pskloud.osm.MainActivity;
import com.pskloud.osm.R;
import com.pskloud.osm.model.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.RetrofitError;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class NotificationHelper {

    private static String NOTIFICATION_NAME = NotificationHelper.class.getCanonicalName();

    public static final int NOTIFICATION_DOWNLOADING_CUSTOMER = 1;
    public static final int NOTIFICATION_DOWNLOADED_CUSTOMER = 2;
    public static final int NOTIFICATION_DOWNLOADING_LOCALITY = 3;
    public static final int NOTIFICATION_DOWNLOADED_LOCALITY = 4;
    public static final int NOTIFICATION_DOWNLOADING_TAX_TYPE = 5;
    public static final int NOTIFICATION_DOWNLOADED_TAX_TYPE = 6;
    public static final int NOTIFICATION_DOWNLOADING_PRODUCT = 7;
    public static final int NOTIFICATION_DOWNLOADED_PRODUCT = 8;


    // classes using for creating notification

    private static NotificationManagerCompat mNotificationManager;
    private static NotificationCompat.Builder mNotificationBuilder;

    public static void show(final Context context, final Class aClass, final int content, final int id,
                            final boolean ongoing){

        Intent open_activity_intent = new Intent(context, aClass);

        open_activity_intent.putExtra(NOTIFICATION_NAME, id);

        PendingIntent pending_intent = PendingIntent.getActivity(context, 0,
                open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);

        mNotificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_file_download)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(content))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(false)
                .setOngoing(ongoing)
                .setContentIntent(pending_intent);
        mNotificationManager = NotificationManagerCompat.from(context);

        mNotificationManager.notify(id, mNotificationBuilder.build());
    }

    public static void close(int...ids){
        if(mNotificationManager != null) {
            if (ids != null && ids.length > 0) {
                for (int id : ids) {
                    mNotificationManager.cancel(id);
                }
            }
        }
    }

    public static void sendBroadcastError(final Context context, final RetrofitError retrofitError){
        switch (retrofitError.getKind()) {
            case NETWORK:
                LocalBroadcastManager.getInstance(context).sendBroadcast(
                        new Intent(DefaultActivity.INTENT_ACTION_NETWORK).setAction(
                                DefaultActivity.INTENT_ACTION_NETWORK));
                break;
            case HTTP:
                try {
                    Error error = structure(retrofitError.getResponse().getBody().in());
                    switch (error.getCode()){
                        case -1:
                            //Error connection data bases
                            LocalBroadcastManager.getInstance(context).sendBroadcast(
                                    new Intent(DefaultActivity.INTENT_ACTION_SERVICES_UNAVAILABLE).setAction(
                                            DefaultActivity.INTENT_ACTION_SERVICES_UNAVAILABLE));
                            break;
                    }
                } catch (IOException e) {
                    if(BuildConfig.DEBUG)
                        Log.d(NOTIFICATION_NAME, e.getMessage());
                }
                break;
        }

    }

    private static Error structure(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String line = null;

            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            if(BuildConfig.DEBUG)
                Log.e(NOTIFICATION_NAME, e.getMessage());
        }
        return  new Gson().fromJson(stringBuffer.toString(), Error.class);
    }
}
