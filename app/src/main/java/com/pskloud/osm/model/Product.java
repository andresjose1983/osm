package com.pskloud.osm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andres on 09/06/16.
 */
public class Product implements Parcelable{

    private String code;

    private String description;

    private double price;

    private int quantity;

    private String picture;

    public Product(String code, String description, double price, int quantity, String picture) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.picture = picture;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.description);
        dest.writeDouble(this.price);
        dest.writeInt(this.quantity);
        dest.writeString(this.picture);
    }

    protected Product(Parcel in) {
        this.code = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.quantity = in.readInt();
        this.picture = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
