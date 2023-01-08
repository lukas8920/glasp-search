package org.kehrbusch.entities;

public class CityWrapper {
    private String city;
    private String country;
    private int falseChars;

    public CityWrapper(String city, String country, int falseChars) {
        this.city = city;
        this.country = country;
        this.falseChars = falseChars;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getFalseChars() {
        return falseChars;
    }

    public void setFalseChars(int falseChars) {
        this.falseChars = falseChars;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
