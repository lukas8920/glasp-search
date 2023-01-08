package org.kehrbusch.entities;

import java.util.ArrayList;
import java.util.List;

public class CityApi {
    private String zip;
    private String city;
    private String country;
    private final List<String> subdistricts;

    public CityApi(){
        this.subdistricts = new ArrayList<>();
    }

    public CityApi(String zip, String city, String country, List<String> subdistricts) {
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.subdistricts = subdistricts;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getSubdistricts() {
        return subdistricts;
    }

    public void addSubdistrict(String subdistrict){this.subdistricts.add(subdistrict);}

    public void setSubdistricts(List<String> subdistricts){this.subdistricts.addAll(subdistricts);}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
