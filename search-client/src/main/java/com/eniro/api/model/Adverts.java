package com.eniro.api.model;

/**
 * Created by andrew on 2015-04-18.
 */
public class Adverts {
    private String eniroId;

    private String infoPageLink;

    private Location location;

    private Address address;

    private String facebook;

    private String companyReviews;

    private PhoneNumbers[] phoneNumbers;

    private CompanyInfo companyInfo;

    private String homepage;

    private Adverts() {
    }

    public String getEniroId() {
        return eniroId;
    }

    public String getInfoPageLink() {
        return infoPageLink;
    }

    public Location getLocation() {
        return location;
    }

    public Address getAddress() {
        return address;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getCompanyReviews() {
        return companyReviews;
    }

    public PhoneNumbers[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public String getHomepage() {
        return homepage;
    }
}
