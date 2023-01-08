package org.kehrbusch.mapper;

import org.kehrbusch.entities.ZipStreetApi;
import org.kehrbusch.entities.database.ZipStreet;
import org.springframework.stereotype.Component;

@Component
public class ZipStreetMapper {
    public ZipStreet map(ZipStreetApi zipStreetApi){
        ZipStreet zipStreet = new ZipStreet();
        zipStreet.setZip(zipStreetApi.getZip());
        zipStreet.setStreet(zipStreetApi.getStreet());
        return zipStreet;
    }
}
