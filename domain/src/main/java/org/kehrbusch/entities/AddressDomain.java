package org.kehrbusch.entities;

public class AddressDomain {
    private String regionIdentifier;
    private String zip;
    private String street;
    private String city;
    private String country;
    //only contains zip + street
    private int falseChars;
    //contains zip + street + city
    private boolean hasEnded;

    public AddressDomain(){}

    public AddressDomain(String regionIdentifier, String zip, String street, String city, String country, int falseChars, boolean hasEnded){
        this.regionIdentifier = regionIdentifier;
        this.zip = zip;
        this.street = street;
        this.city = city;
        this.country = country;
        this.falseChars = falseChars;
        this.hasEnded = hasEnded;
    }

    @Override
    public boolean equals(Object object){
        AddressDomain addressDomain = (AddressDomain) object;
        return addressDomain.getZip().equals(this.zip) && addressDomain.getStreet().equals(this.street);
    }

    @Override
    public int hashCode(){
        return 0;
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

    public int getFalseChars() {
        return falseChars;
    }

    public void setFalseChars(int falseChars) {
        this.falseChars = falseChars;
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

    public boolean isHasEnded() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    public String getRegionIdentifier() {
        return regionIdentifier;
    }

    public void setRegionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
    }
}
