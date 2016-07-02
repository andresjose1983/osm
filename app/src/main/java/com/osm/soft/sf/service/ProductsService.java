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
import com.osm.soft.sf.model.Product;
import com.osm.soft.sf.rest.RestClient;
import com.osm.soft.sf.util.NotificationHelper;
import com.osm.soft.sf.util.ProductsSqlHelper;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public class ProductsService extends IntentService {

    private static final String SERVICE_NAME = "ProductsService";

    private ProductsSqlHelper productsSqlHelper;

    public ProductsService(){
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        productsSqlHelper = new ProductsSqlHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationHelper.show(this, MainActivity.class, R.string.notification_text_product,
                NotificationHelper.NOTIFICATION_DOWNLOADING_PRODUCT, true);
        RestClient.GET_PRODUCTS.execute(new Callback<List<Product>>() {
            @Override
            public void success(List<Product> products, Response response) {
                new Thread(() -> {
                    productsSqlHelper.DELETE.execute();
                    for (Product product: products) {
                        if(productsSqlHelper.ADD.execute(product)>0)
                            if(BuildConfig.DEBUG)
                                Log.i("Inserted",  product.getName());
                    }
                    NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_PRODUCT);
                    NotificationHelper.show(ProductsService.this, MainActivity.class,
                            R.string.notification_product_downloaded,
                            NotificationHelper.NOTIFICATION_DOWNLOADED_PRODUCT,
                            true);
                }).start();
            }

            @Override
            public void failure(RetrofitError error) {
                NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADING_PRODUCT);
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
