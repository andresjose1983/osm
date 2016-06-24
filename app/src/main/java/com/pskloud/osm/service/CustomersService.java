package com.pskloud.osm.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.MainActivity;
import com.pskloud.osm.OsmApplication;
import com.pskloud.osm.R;
import com.pskloud.osm.model.Customer;
import com.pskloud.osm.rest.RestClient;
import com.pskloud.osm.util.CustomerSqlHelper;
import com.pskloud.osm.util.NotificationHelper;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class CustomersService extends IntentService {

    private static final String SERVICE_NAME = "CustomersService";

    private CustomerSqlHelper customerSqlHelper;

    public CustomersService(){
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        customerSqlHelper = new CustomerSqlHelper(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationHelper.show(this, MainActivity.class, R.string.notification_text_customer,
                NotificationHelper.NOTIFICATION_DOWNLOADING_CUSTOMER, true);
        RestClient.GET_CUSTOMERS.execute(new Callback<List<Customer>>() {
            @Override
            public void success(List<Customer> customers, Response response) {
                new Thread(() -> {
                    customerSqlHelper.DELETE.execute();
                    for (Customer customer: customers) {
                        customer.setNew(false);
                        customer.setSync(true);
                        if(customerSqlHelper.ADD.execute(customer)>0)
                            if(BuildConfig.DEBUG)
                                Log.i("Inserted",  customer.getName());
                    }
                    NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_CUSTOMER);
                    NotificationHelper.show(CustomersService.this, MainActivity.class,
                            R.string.notification_customer_downloaded,
                            NotificationHelper.NOTIFICATION_DOWNLOADED_CUSTOMER,
                            true);
                }).start();
            }

            @Override
            public void failure(RetrofitError error) {
                NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_CUSTOMER);
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
