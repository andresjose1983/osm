package com.osm.soft.sf.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 05/07/2016.
 */
public class ProductOrder implements Serializable {

    private int id;
    private Order order;
    private Product product;
    private int quantity;

    public ProductOrder(int id, Order order, Product product, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    private ProductOrder(Builder builder) {
        quantity = builder.quantity;
        product = builder.product;
        order = builder.order;
        id = builder.id;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }


    public static final class Builder {
        private int quantity;
        private Product product;
        private Order order;
        private int id;

        public Builder() {
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder order(Order val) {
            order = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public ProductOrder build() {
            return new ProductOrder(this);
        }
    }
}
