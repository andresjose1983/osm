package com.pskloud.osm.rest;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.Locality;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;


/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public abstract class RestClient {

    static RestAdapter mRestAdapter;

    private static OsmServices mOsmServices;
    static{
        mRestAdapter =new RestAdapter.Builder().setEndpoint(BuildConfig.URL).build();
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

    @FunctionalInterface
    public interface GetCustomers{
        void getResponse(Callback<List<Customer>> callback);
    }

    @FunctionalInterface
    public interface GetLocalities{
        void getResponse(Callback<List<Locality>> callback);
    }
}
