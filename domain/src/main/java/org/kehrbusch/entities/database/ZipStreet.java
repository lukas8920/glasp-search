package org.kehrbusch.entities.database;

public class ZipStreet {
    private String zip;
    private String street;

    public ZipStreet(){}

    public ZipStreet(String zip, String street) {
        this.zip = zip;
        this.street = street;
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    public String getZip(){
        return this.zip;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getStreet(){
        return this.street;
    }

    @Override
    public String toString(){
        return this.zip + " " + this.street;
    }
}
