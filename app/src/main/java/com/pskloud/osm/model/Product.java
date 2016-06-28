package com.pskloud.osm.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class Product implements Serializable{

    private String code;
    private String name;
    private String group;
    private List<String> price;
    private int stock;

    public Product(String code, String name, String group, List<String> price, int stock) {
        this.code = code;
        this.name = name;
        this.group = group;
        this.price = price;
        this.stock = stock;
    }

    private Product(Builder builder) {
        code = builder.code;
        name = builder.name;
        group = builder.group;
        price = builder.price;
        stock = builder.stock;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public List<String> getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public static final class Builder {
        private String code;
        private String name;
        private String group;
        private List<String> price;
        private int stock;

        public Builder() {
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder group(String val) {
            group = val;
            return this;
        }

        public Builder price(List<String> val) {
            price = val;
            return this;
        }

        public Builder stock(int val) {
            stock = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
