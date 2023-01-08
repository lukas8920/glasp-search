package org.kehrbusch.mapper;

import org.kehrbusch.entities.Address;
import org.kehrbusch.entities.Addresses;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address map(Addresses addresses){
        Address address = new Address();
        address.setZip(addresses.getZip());
        address.setStreet(addresses.getStreet());
        address.setCity(addresses.getCity());
        address.setCountry(addresses.getCountry());
        return address;
    }

    public Addresses map(Address address){
        Addresses addresses = new Addresses();
        addresses.setZip(address.getZip());
        addresses.setStreet(address.getStreet());
        addresses.setCity(address.getCity());
        addresses.setCountry(address.getCountry());
        return addresses;
    }
}
