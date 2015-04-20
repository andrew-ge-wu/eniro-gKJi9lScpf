package com.eniro.api.model;

/**
 * Created by andrew on 2015-04-18.
 */
public class Address {
    private String postArea;

    private String postCode;

    private String streetName;

    private String postBox;

    private Address() {
    }

    public String getPostArea() {
        return postArea;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostBox() {
        return postBox;
    }
}

