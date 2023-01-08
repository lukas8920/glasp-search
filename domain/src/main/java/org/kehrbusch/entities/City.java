package org.kehrbusch.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class City {
    private String zip;
    private String regionIdentifier;
    private String city;
    private String country;
    private final List<String> subdistricts;

    public City(){
        this.subdistricts = new ArrayList<>();
    }

    public City(String zip, String regionIdentifier, String city, String country, List<String> subdistricts) {
        this.zip = zip;
        this.regionIdentifier = regionIdentifier;
        this.city = city;
        this.country = country;
        this.subdistricts = subdistricts;
    }

    public void setRegionIdentifier(String regionIdentifier){
        this.regionIdentifier = regionIdentifier;
    }

    public String getRegionIdentifier(){
        return this.regionIdentifier;
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

    public static City buildFromString(String line){
        String[] parentSegments = line.split(",");
        List<String> children;
        if (parentSegments.length >= 4){
            String[] childSegments = parentSegments[3].split(":");
            children = Arrays.asList(childSegments);
        } else {
            children = new ArrayList<>();
        }
        return new City(parentSegments[0], "", parentSegments[1], parentSegments[2], children);
    }
}
