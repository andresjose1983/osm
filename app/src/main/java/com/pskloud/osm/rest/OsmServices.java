package com.pskloud.osm.rest;

import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.Locality;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public interface OsmServices {

    @GET("/customers")
    void getCustomers(Callback<List<Customer>> listCallback);

    @GET("/customers/zones")
    void getLocalities(Callback<List<Locality>> listCallback);

    @GET("/customer/tax-types")
    void getTaxTypes(Callback<Map<String, Integer>> mapCallback);
}
