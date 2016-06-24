package com.pskloud.osm.model;

/**
 * Created by Mendez Fernandez on 24/06/2016.
 */
public class Error {

    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
