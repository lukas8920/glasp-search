package org.kehrbusch.mappers;

import org.kehrbusch.entities.Address;
import org.kehrbusch.entities.AddressH2;
import org.springframework.stereotype.Component;

@Component
public class H2AddressMapper {
    public AddressH2 map(Address address){
        AddressH2 addressH2 = new AddressH2();
        addressH2.setStreet(address.getStreet());
        addressH2.setZip(address.getZip());
        addressH2.setRegionIdentifier(address.getRegionIdentifier());
        addressH2.setCountry(address.getCountry());
        addressH2.setCreateTimestamp(String.valueOf(System.currentTimeMillis()));
        addressH2.setChangeTimestamp(String.valueOf(System.currentTimeMillis()));
        return addressH2;
    }
}
