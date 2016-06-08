package com.pskloud.osm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andres on 08/06/16.
 */
public class Customer implements Parcelable{

    private String identification;

    private String name;

    private String address;

    private String contactPerson;

    private String telephone;

    private boolean isView;

    public Customer(String identification, String name, String address, String contactPerson, String telephone) {
        this.identification = identification;
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.identification);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.contactPerson);
        dest.writeString(this.telephone);
    }

    protected Customer(Parcel in) {
        this.identification = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.contactPerson = in.readString();
        this.telephone = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };


}
