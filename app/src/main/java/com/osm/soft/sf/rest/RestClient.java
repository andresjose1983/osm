package com.osm.soft.sf.rest;

import android.util.Log;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.model.CustomerResponse;
import com.osm.soft.sf.model.Locality;
import com.osm.soft.sf.model.OrderResponse;
import com.osm.soft.sf.model.Product;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public abstract class RestClient {

    static RestAdapter mRestAdapter;

    private static OsmServices mOsmServices;
    static{
        mRestAdapter =new RestAdapter.Builder()
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        return cause;
                    }
                })
                .setLogLevel(BuildConfig.DEBUG?RestAdapter.LogLevel.FULL: RestAdapter.LogLevel.NONE)
                .setEndpoint(BuildConfig.URL).build();
        init();
    }

    private static void init(){
        mOsmServices = mRestAdapter.create(OsmServices.class);
    }

    public static final GetCustomers GET_CUSTOMERS = (callback) -> {
        mOsmServices.getCustomers(callback);
    };

    public static final GetLocalities GET_LOCALITIES = (callback) -> {
        mOsmServices.getLocalities(callback);
    };

    public static final GetTaxTypes GET_TAX_TYPES = (callback) -> {
        mOsmServices.getTaxTypes(callback);
    };

    public static final CreateCustomer CREATE_CUSTOMER = (customerResponse) -> {
        try {
            return mOsmServices.createCustomer(customerResponse);
        }catch (Exception e){
            if(BuildConfig.DEBUG)
                Log.e(RestClient.class.getCanonicalName(), e.getMessage());
        }
        return null;
    };

    public static final CreateOrder CREATE_ORDER = (orderResponse) -> {
        try {
            return mOsmServices.createOrder(orderResponse);
        }catch (Exception e){
            if(BuildConfig.DEBUG)
                Log.e(RestClient.class.getCanonicalName(), e.getMessage());
        }
        return null;
    };

    public static final UpdateCustomer UPDATE = (customerResponse) -> {
        try {
            return mOsmServices.updateCustomer(customerResponse);
        }catch (Exception e){
            if(BuildConfig.DEBUG)
                Log.e(RestClient.class.getCanonicalName(), e.toString());
        }
        return null;
    };

    public static final GetProducts GET_PRODUCTS = (callback) -> {
        mOsmServices.getProducts(callback);
    };

    @FunctionalInterface
    public interface GetCustomers{
        void execute(Callback<List<Customer>> callback);
    }

    @FunctionalInterface
    public interface GetLocalities{
        void execute(Callback<List<Locality>> callback);
    }

    @FunctionalInterface
    public interface GetTaxTypes{
        void execute(Callback<Map<String, Integer>> callback);
    }

    @FunctionalInterface
    public interface CreateCustomer{
        Response execute(CustomerResponse customerResponse);
    }

    @FunctionalInterface
    public interface CreateOrder{
        Response execute(OrderResponse orderResponse);
    }

    @FunctionalInterface
    public interface UpdateCustomer{
        Response execute(CustomerResponse customerResponse);
    }

    @FunctionalInterface
    public interface GetProducts{
        void execute(Callback<List<Product>> callback);
    }
}
