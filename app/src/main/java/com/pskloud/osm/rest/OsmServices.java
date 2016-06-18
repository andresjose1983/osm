package com.pskloud.osm.rest;

import com.pskloud.osm.model.Customer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public interface OsmServices {

    @GET("/customers")
    void get(Callback<List<Customer>> listCallback);
}
