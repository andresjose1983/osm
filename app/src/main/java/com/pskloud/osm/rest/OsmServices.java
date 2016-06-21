package com.pskloud.osm.rest;

import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.Locality;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public interface OsmServices {

    @GET("/customers")
    void getCustomers(Callback<List<Customer>> listCallback);

    // TODO esperar por jonathan
    @GET("/localities")
    void getLocalities(Callback<List<Locality>> listCallback);
}
