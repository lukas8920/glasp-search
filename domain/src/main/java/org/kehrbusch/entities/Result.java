package org.kehrbusch.entities;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class Result {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Logger logger = Logger.getLogger(Result.class.getName());

    private final String searchZipStreet;
    private final String searchCountry;
    private int sizeOfResults = 5;
    private String searchCity;
    private final List<AddressDomain> addressDomains = new ArrayList<>();

    private AtomicInteger minFalseChars = new AtomicInteger(6);
    private final AtomicBoolean hasFreeSpace = new AtomicBoolean(true);

    /*
    * SearchValue has to be in the format zip + street + city
    * Todo: Bring zip to the front
    * Todo: make algorithm adaptive, so that it accepts any order of street
    */
    public Result(String searchValue, String country){
        String[] segments = searchValue.split(",");
        this.searchZipStreet = segments[0] + " " + segments[1];
        if (segments.length > 2){
            this.searchCity = segments[2];
        }
        this.searchCountry = country;
    }

    //Advice for better performance split longer search Strings into smaller result Counts, e.g. > 14 = 2 and > 20 = 1
    public Result(String zip, String street, String country, int maxErrors, int resultCount){
        this.searchZipStreet = zip + " " + street;
        this.searchCountry = country;
        this.minFalseChars = new AtomicInteger(maxErrors);
        this.sizeOfResults = resultCount;
    }

    public Result(String zip, String street, String city, String country, int maxErrors, int resultCount){
        this.searchZipStreet = zip + " " + street;
        this.searchCity = city;
        this.searchCountry = country;
        this.minFalseChars = new AtomicInteger(maxErrors);
        this.sizeOfResults = resultCount;
    }

    public int getMinFalseChars(){
        return this.minFalseChars.get();
    }

    public String getSearchZipStreet(){
        return this.searchZipStreet;
    }

    public String getSearchCity(){return this.searchCity;}

    public String getSearchCountry(){return this.searchCountry;}

    public List<AddressDomain> getAddresses(){
        return this.addressDomains;
    }

    public int getSpaceForAddresses(){
        return this.sizeOfResults;
    }

    public boolean isValidInputWrapper(int falseChars){
        return falseChars >= this.getMinFalseChars() && !this.hasFreeSpace();
    }

    private boolean hasFreeSpace(){
        return hasFreeSpace.get();
    }

    public boolean addAddress(AddressDomain addressDomain){
        boolean flag = false;
        lock.writeLock().lock();
        try {
            if (addressDomain.getFalseChars() < this.getMinFalseChars() || this.hasFreeSpace()
                && !this.addressDomains.contains(addressDomain)){
                //System.out.println(address.getZip() + "," + address.getStreet() + "," + address.getFalseChars() + "," + minFalseChars + "," + freeSpace);
                flag = this.addressDomains.add(addressDomain);
                this.hasFreeSpace.set(addressDomains.size() < sizeOfResults);
                this.addressDomains.sort(comparator);
                if (addressDomains.size() > sizeOfResults){
                    this.addressDomains.remove(this.addressDomains.size() - 1);
                    this.minFalseChars.set(this.addressDomains.get(this.addressDomains.size() - 1).getFalseChars());
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
        return flag;
    }

    Comparator<AddressDomain> comparator = (a1, a2) -> {
        if (a1.getFalseChars() < a2.getFalseChars()) {
            return -1;
        } else if (a1.getFalseChars() == a2.getFalseChars()) {
            if (a1.isHasEnded() && !a2.isHasEnded()){
                return -1;
            } else if (!a1.isHasEnded() && a2.isHasEnded()){
                return 1;
            } else {
                return 0;
            }
        } else if (a1.getFalseChars() > a2.getFalseChars()) {
            return 1;
        }
        return 0;
    };
}
