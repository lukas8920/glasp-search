package org.kehrbusch.mapper;

import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDomain map(Address address){
        AddressDomain addressDomain = new AddressDomain();
        addressDomain.setZip(address.getZip());
        addressDomain.setStreet(address.getStreet());
        addressDomain.setCity(address.getCity());
        addressDomain.setCountry(address.getCountry());
        return addressDomain;
    }

    public Address map(AddressDomain addressDomain){
        Address address = new Address();
        address.setZip(addressDomain.getZip());
        address.setStreet(addressDomain.getStreet());
        address.setCity(addressDomain.getCity());
        address.setCountry(addressDomain.getCountry());
        return address;
    }
}
