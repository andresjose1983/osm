package com.osm.soft.sf.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 07/07/2016.
 */
public class CustomerOrderResponse implements Serializable{

    private String code;

    public CustomerOrderResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
