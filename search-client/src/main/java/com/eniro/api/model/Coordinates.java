package com.eniro.api.model;

/**
 * Created by andrew on 2015-04-18.
 */
public class Coordinates {
    private String use;
    private float longitude;

    private float latitude;

    private Coordinates() {
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getUse() {
        return use;
    }
}

