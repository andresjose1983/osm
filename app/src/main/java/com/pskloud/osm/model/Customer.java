package com.pskloud.osm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 08/06/16.
 */
public class Customer implements Serializable{

    private String code;
    private String name;
    private String address;
    private List<String> phones = new ArrayList<>();
    private String tin;
    private String zone;
    private int taxType;
    private boolean sync = true;
    private int price;
    private boolean isNew;
    boolean isView;

    public Customer(String code, String name, String address, List<String> phones, String tin, String zone, int taxType, boolean sync, int price, boolean isNew, boolean isView) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.tin = tin;
        this.zone = zone;
        this.taxType = taxType;
        this.sync = sync;
        this.price = price;
        this.isNew = isNew;
        this.isView = isView;
    }

    private Customer(Builder builder) {
        code = builder.code;
        name = builder.name;
        address = builder.address;
        phones = builder.phones;
        tin = builder.tin;
        zone = builder.zone;
        taxType = builder.taxType;
        sync = builder.sync;
        price = builder.price;
        isNew = builder.isNew;
        isView = builder.isView;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public String getTin() {
        return tin;
    }

    public String getZone() {
        return zone;
    }

    public int getTaxType() {
        return taxType;
    }

    public boolean isSync() {
        return sync;
    }

    public int getPrice() {
        return price;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isView() {
        return isView;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public static final class Builder {
        private String code;
        private String name;
        private String address;
        private List<String> phones;
        private String tin;
        private String zone;
        private int taxType;
        private boolean sync;
        private int price;
        private boolean isNew;
        private boolean isView;

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

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder phones(List<String> val) {
            phones = val;
            return this;
        }

        public Builder tin(String val) {
            tin = val;
            return this;
        }

        public Builder zone(String val) {
            zone = val;
            return this;
        }

        public Builder taxType(int val) {
            taxType = val;
            return this;
        }

        public Builder sync(boolean val) {
            sync = val;
            return this;
        }

        public Builder price(int val) {
            price = val;
            return this;
        }

        public Builder isNew(boolean val) {
            isNew = val;
            return this;
        }

        public Builder isView(boolean val) {
            isView = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
