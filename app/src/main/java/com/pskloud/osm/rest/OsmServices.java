package com.pskloud.osm.rest;

import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.CustomerResponse;
import com.pskloud.osm.model.Locality;
import com.pskloud.osm.model.Product;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by Mendez Fernandez on 17/06/2016.
 */
public interface OsmServices {

    @GET("/customers")
    void getCustomers(Callback<List<Customer>> listCallback);

    @GET("/customers/zones")
    void getLocalities(Callback<List<Locality>> listCallback);

    @GET("/customers/tax-types")
    void getTaxTypes(Callback<Map<String, Integer>> mapCallback);

    @POST("/customers")
    retrofit.client.Response createCustomer(@Body CustomerResponse customerResponse);

    @PUT("/customers")
    retrofit.client.Response updateCustomer(@Body CustomerResponse customerResponse);

    @GET("/items")
    void getProducts(Callback<List<Product>> callback);
}
