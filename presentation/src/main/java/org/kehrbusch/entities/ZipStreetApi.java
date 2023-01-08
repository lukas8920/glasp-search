package org.kehrbusch.entities;

public class ZipStreetApi {
    private String zip;
    private String street;

    public ZipStreetApi(){}

    public ZipStreetApi(String zip, String street) {
        this.zip = zip;
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
