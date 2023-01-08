package org.kehrbusch.mappers;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityH2;
import org.springframework.stereotype.Component;

@Component
public class H2CityMapper {
    public City map(CityH2 cityH2){
        City city = new City();
        city.setCity(cityH2.getCity());
        city.setRegionIdentifier(cityH2.getRegionIdentifier());
        city.setCountry(cityH2.getCountry());
        city.setZip(cityH2.getZip());
        city.setSubdistricts(cityH2.getSubdistricts());
        return city;
    }

    public CityH2 map(City city){
        CityH2 cityH2 = new CityH2();
        cityH2.setCity(city.getCity());
        cityH2.setRegionIdentifier(city.getRegionIdentifier());
        cityH2.setCountry(city.getCountry());
        cityH2.setZip(city.getZip());
        cityH2.addAllSubdistricts(city.getSubdistricts());
        return cityH2;
    }
}
