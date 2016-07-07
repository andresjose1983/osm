package com.osm.soft.sf.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.OsmApplication;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.model.OrderResponse;
import com.osm.soft.sf.rest.RestClient;
import com.osm.soft.sf.util.Functions;
import com.osm.soft.sf.util.OrderSqlHelper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 07/07/2016.
 */
public class OrderJobService extends Service {

    public static final long NOTIFY_INTERVAL = 11250;
    private OrderSqlHelper mOrderSqlHelper;
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
        mOrderSqlHelper = new OrderSqlHelper(this);
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
                if(Functions.getStatus(OsmApplication.getInstance()) &&
                        Functions.checkInternetConnection(OsmApplication.getInstance())){

                    if(!ProductsService.isRunning(OrderJobService.this) &&
                            !CustomersService.isRunning(OrderJobService.this) &&
                            !TaxTypesService.isRunning(OrderJobService.this) &&
                            !LocalitiesService.isRunning(OrderJobService.this)){

                        List<Order> orders = mOrderSqlHelper.GET_PENDING.execute();
                        for (Order order : orders) {
                            Log.i(OrderJobService.class.getCanonicalName(), order.getId() + "");
                            update(order);
                        }
                    }
                }
            });
        }
    }

    private void update(Order order){
        new Thread(() -> {
            Response execute = RestClient.CREATE_ORDER.execute(new OrderResponse(order));
            if(execute != null) {
                if(BuildConfig.DEBUG)
                    Log.e(OrderJobService.class.getCanonicalName(), "Code " + execute.getStatus());
                if(execute.getStatus() == 201) {
                    synced(order);
                }
            }
        }).start();
    }

    private void synced(Order order){
        order.setSync(true);
        mOrderSqlHelper.UPDATE.execute(order);
    }
}
