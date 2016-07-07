package com.osm.soft.sf.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class Order implements Serializable{

    private int id;
    private Date date;
    private boolean isView;
    private boolean sync;
    private List<ProductOrder> products;
    private Customer customer;

    private Order(Builder builder) {
        customer = builder.customer;
        products = builder.products;
        setSync(builder.sync);
        setView(builder.isView);
        date = builder.date;
        id = builder.id;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public boolean isView() {
        return isView;
    }

    public boolean isSync() {
        return sync;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public static final class Builder {
        private Customer customer;
        private List<ProductOrder> products;
        private boolean sync;
        private boolean isView;
        private Date date;
        private int id;

        public Builder() {
        }

        public Builder customer(Customer val) {
            customer = val;
            return this;
        }

        public Builder products(List<ProductOrder> val) {
            products = val;
            return this;
        }

        public Builder sync(boolean val) {
            sync = val;
            return this;
        }

        public Builder isView(boolean val) {
            isView = val;
            return this;
        }

        public Builder date(Date val) {
            date = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public void setId(int id) {
        this.id = id;
    }
}
