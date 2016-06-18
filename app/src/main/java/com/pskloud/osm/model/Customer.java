package com.pskloud.osm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by andres on 08/06/16.
 */
public class Customer implements Serializable{

    String name;
    String address;
    List<String> phones = new ArrayList<>();
    String tin;
    String identification;//optional
    String code;//optional
    int price;//optional
    String type;//optional
    String tag;//optional
    boolean isView;
    boolean sync;

    public Customer(String name, String address, List<String> phones, String tin,
                    String identification, String code, int price, String type, String tag,
                    boolean sync) {
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.tin = tin;
        this.identification = identification;
        this.code = code;
        this.price = price;
        this.type = type;
        this.tag = tag;
        this.sync = sync;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
