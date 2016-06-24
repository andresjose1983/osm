package com.pskloud.osm.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 23/06/2016.
 */
public class TypesOfTax implements Serializable {

    private String name;
    private int code;

    public TypesOfTax(String name, int code) {
        this.name = name;
        this.code = code;
    }

    private TypesOfTax(Builder builder) {
        name = builder.name;
        code = builder.code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static final class Builder {
        private String name;
        private int code;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder code(int val) {
            code = val;
            return this;
        }

        public TypesOfTax build() {
            return new TypesOfTax(this);
        }
    }
}
