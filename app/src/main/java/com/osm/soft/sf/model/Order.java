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
    private boolean isSynced;
    private List<ProductOrder> products;
    private Customer customer;

    private Order(Builder builder) {
        id = builder.id;
        date = builder.date;
        setView(builder.isView);
        setSynced(builder.isSynced);
        products = builder.products;
        customer = builder.customer;
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

    public boolean isSynced() {
        return isSynced;
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

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final class Builder {
        private int id;
        private Date date;
        private boolean isView;
        private boolean isSynced;
        private List<ProductOrder> products;
        private Customer customer;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder number(String val) {
            return this;
        }

        public Builder date(Date val) {
            date = val;
            return this;
        }

        public Builder totalItem(int val) {
            return this;
        }

        public Builder isView(boolean val) {
            isView = val;
            return this;
        }

        public Builder isSynced(boolean val) {
            isSynced = val;
            return this;
        }

        public Builder products(List<ProductOrder> val) {
            products = val;
            return this;
        }

        public Builder customer(Customer val) {
            customer = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }
}
