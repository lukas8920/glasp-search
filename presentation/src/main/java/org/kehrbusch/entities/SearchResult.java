package org.kehrbusch.entities;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private final List<Address> addresses;

    public SearchResult() {
        this.addresses = new ArrayList<>();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddresses(List<Address> addresses){
        this.addresses.addAll(addresses);
    }
}
