package org.kehrbusch.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public class ZipStreetRequest {
    @Schema(description = "The zip code which you would like to search.",
            example = "43032", minLength = 1, maxLength = 8)
    private String zip;
    @Schema(description = "The street which you would like to search.",
            example = "Strada Cascina", minLength = 1, maxLength = 30)
    private String street;
    @Schema(description = "The country code which has to follow ISO 3166-1 alpha-2 (only allowed value is IT).",
            example = "IT", minLength = 1, maxLength = 2)
    private String country;
    @Schema(description = "The number of allowed errors in the search string.",
            example = "4", minimum = "1", maximum = "6")
    private int maxErrors;
    @Schema(description = "The number of possible matches which the response should return.",
            example = "4", minimum = "1", maximum = "10")
    private int noOfResults;

    public ZipStreetRequest(){}

    public ZipStreetRequest(String zip, String street, String country, int maxErrors, int noOfResults) {
        this.zip = zip;
        this.street = street;
        this.country = country;
        this.maxErrors = maxErrors;
        this.noOfResults = noOfResults;
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

    public int getNoOfResults() {
        return noOfResults;
    }

    public void setNoOfResults(int noOfResults) {
        this.noOfResults = noOfResults;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }
}
