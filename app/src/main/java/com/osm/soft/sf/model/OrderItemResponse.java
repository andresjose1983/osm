package com.osm.soft.sf.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 07/07/2016.
 */
public class OrderItemResponse implements Serializable {

    private String code;

    private int quantity;

    public OrderItemResponse(ProductOrder productOrder) {
        code = productOrder.getProduct().getCode();
        quantity = productOrder.getQuantity();
    }

    public OrderItemResponse(String code, int quantity) {
        this.code = code;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
