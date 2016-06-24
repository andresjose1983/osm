package com.pskloud.osm.rest;

import android.os.Build;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.CustomerResponse;
import com.pskloud.osm.model.Locality;
import com.pskloud.osm.model.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
                        Log.e(RestClient.class.getCanonicalName(), cause.getResponse().getReason());
                        return null;
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

    public static final CreateCustomer CREATE = (customerResponse) -> {
        try {
            return mOsmServices.createCustomer(customerResponse);
        }catch (Exception e){
            return null;
        }
    };

    public static final UpdateCustomer UPDATE = (customerResponse) -> {
        try {
            return mOsmServices.updateCustomer(customerResponse);
        }catch (Exception e){
            return null;
        }
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
    public interface UpdateCustomer{
        Response execute(CustomerResponse customerResponse);
    }

    @FunctionalInterface
    public interface GetProducts{
        void execute(Callback<List<Product>> callback);
    }
}
