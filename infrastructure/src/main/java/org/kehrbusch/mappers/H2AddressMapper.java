package org.kehrbusch.mappers;

import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.AddressH2;
import org.springframework.stereotype.Component;

@Component
public class H2AddressMapper {
    public AddressH2 map(AddressDomain addressDomain){
        AddressH2 addressH2 = new AddressH2();
        addressH2.setStreet(addressDomain.getStreet());
        addressH2.setZip(addressDomain.getZip());
        addressH2.setRegionIdentifier(addressDomain.getRegionIdentifier());
        addressH2.setCountry(addressDomain.getCountry());
        addressH2.setCreateTimestamp(String.valueOf(System.currentTimeMillis()));
        addressH2.setChangeTimestamp(String.valueOf(System.currentTimeMillis()));
        return addressH2;
    }
}
