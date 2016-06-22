package com.pskloud.osm.model;

import java.io.Serializable;

/**
 * Created by Mendez Fernandez on 21/06/2016.
 */
public enum Tax implements Serializable {

    TAXPAYER("", 1),OTHERS("", 6), EXEMPT("", 3), EXPORTER("", 4),FORMAL_TAXPAYER("", 5),NO_TAXPAYER("", 2);

    private String description;
    private int id;

    Tax(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
