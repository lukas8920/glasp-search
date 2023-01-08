package org.kehrbusch.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public class Addresses {
    @Schema(description = "The zip code of the search result.",
            example = "43032", minLength = 1, maxLength = 8)
    private String zip;
    @Schema(description = "The street of the search result.",
            example = "Strada Cascina", minLength = 1, maxLength = 30)
    private String street;
    @Schema(description = "The city of the search result.",
            example = "Busseto", minLength = 1, maxLength = 30)
    private String city;
    @Schema(description = "ISO 3166-1 alpha-2 country code (only allowed value is IT).",
            example = "IT", minLength = 1, maxLength = 2)
    private String country;

    public Addresses(){}

    public Addresses(String zip, String street, String city, String country) {
        this.zip = zip;
        this.street = street;
        this.city = city;
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
