package org.kehrbusch.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CityH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String zip;
    private String regionIdentifier;
    private String city;
    private String country;
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> subdistricts;

    public CityH2(){
        this.subdistricts = new ArrayList<>();
    }

    public CityH2(String zip, String regionIdentifier, String city, String country, List<String> subdistricts) {
        this.zip = zip;
        this.regionIdentifier = regionIdentifier;
        this.city = city;
        this.country = country;
        this.subdistricts = subdistricts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getSubdistricts() {
        return subdistricts;
    }

    public void addAllSubdistricts(List<String> subdistricts){
        this.subdistricts.addAll(subdistricts);
    }

    public String getRegionIdentifier() {
        return regionIdentifier;
    }

    public void setRegionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
    }
}
