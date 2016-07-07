package com.osm.soft.sf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez Fernandez on 07/07/2016.
 */
public class OrderResponse implements Serializable {

    private CustomerOrderResponse customer;

    private long created;

    private long expiration;

    private List<OrderItemResponse> items = new ArrayList<>();

    public OrderResponse(Order order) {
        customer = new CustomerOrderResponse(order.getCustomer().getCode());
        created = order.getDate().getTime();
        expiration = created;
        for (ProductOrder productOrder :order.getProducts()) {
            items.add(new OrderItemResponse(productOrder));
        }
    }

    public CustomerOrderResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerOrderResponse customer) {
        this.customer = customer;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
