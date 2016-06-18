package com.pskloud.osm.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.pskloud.osm.MainActivity;
import com.pskloud.osm.R;
import com.pskloud.osm.service.CustomersService;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class NotificationHelper {

    private static String NOTIFICATION_NAME = NotificationHelper.class.getCanonicalName();

    public static final int NOTIFICATION_DOWNLOADING_CUSTOMER = 1;
    public static final int NOTIFICATION_DOWNLOADED_CUSTOMER = 2;
    // classes using for creating notification

    private static NotificationManagerCompat mNotificationManager;
    private static NotificationCompat.Builder mNotificationBuilder;

    public static void show(final Context context, final int content, final int id){

        Intent open_activity_intent = new Intent(context, MainActivity.class);

        open_activity_intent.putExtra(NOTIFICATION_NAME, id);

        PendingIntent pending_intent = PendingIntent.getActivity(context, 0,
                open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);

        mNotificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_file_download)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(content))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(pending_intent);
        mNotificationManager = NotificationManagerCompat.from(context);

        mNotificationManager.notify(id, mNotificationBuilder.build());
    }

    public static void close(int id){
        if(mNotificationManager != null)
            mNotificationManager.cancel(id);
    }

}
