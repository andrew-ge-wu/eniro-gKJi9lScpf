package com.eniro.api.model;

/**
 * Created by andrew on 2015-04-18.
 */
public class PhoneNumbers {
    private String phoneNumber;

    private String label;

    private String type;

    private PhoneNumbers() {
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLabel() {
        return label;
    }
}

