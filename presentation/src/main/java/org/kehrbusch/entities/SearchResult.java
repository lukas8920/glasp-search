package org.kehrbusch.entities;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private final List<Addresses> addresses;

    public SearchResult() {
        this.addresses = new ArrayList<>();
    }

    public List<Addresses> getAddressApis() {
        return addresses;
    }

    public void addAddresses(List<Addresses> addresses){
        this.addresses.addAll(addresses);
    }
}
