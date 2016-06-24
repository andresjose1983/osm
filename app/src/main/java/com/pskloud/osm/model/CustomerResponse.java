package com.pskloud.osm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez Fernandez on 23/06/2016.
 */
public class CustomerResponse implements Serializable {

    private String code;
    private String name;
    private String address;
    private List<String> phones = new ArrayList<>();
    private String tin;
    private String zone;
    private int taxType;
    private String identification = "";

    public CustomerResponse(String code, String name, String address, List<String> phones,
                            String tin, String zone, int taxType) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.tin = tin;
        this.zone = zone;
        this.taxType = taxType;
    }

    public CustomerResponse(Customer customer){
        this(customer.getCode(), customer.getName(), customer.getAddress(), customer.getPhones(), customer.getTin(),
                customer.getZone(), customer.getTaxType());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getTaxType() {
        return taxType;
    }

    public void setTaxType(int taxType) {
        this.taxType = taxType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
