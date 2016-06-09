package com.pskloud.osm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by andres on 09/06/16.
 */
public class Order implements Parcelable{

    private String number;

    private Date date;

    private int totalItem;

    public Order(String number, Date date, int totalItem) {
        this.number = number;
        this.date = date;
        this.totalItem = totalItem;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.totalItem);
    }

    protected Order(Parcel in) {
        this.number = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.totalItem = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
