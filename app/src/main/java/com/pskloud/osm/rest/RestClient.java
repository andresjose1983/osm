package com.pskloud.osm.rest;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.Customer;

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
        mOsmServices.get(callback);
    };

    @FunctionalInterface
    public interface GetCustomers{
        void getResponse(Callback<List<Customer>> callback);
    }
}
