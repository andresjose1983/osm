package com.osm.soft.sf.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 20/06/2016.
 */
public class Locality implements Serializable {

    private String code;
    private String name;

    public Locality(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private Locality(Builder builder) {
        code = builder.code;
        name = builder.name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static final class Builder {

        private String code;
        private String name;

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

        public Locality build() {
            return new Locality(this);
        }
    }
}
