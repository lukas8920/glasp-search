package org.kehrbusch.mapper;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityApi;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public City map(CityApi cityApi){
        City city = new City();
        city.setCity(cityApi.getCity());
        city.setZip(cityApi.getZip());
        city.setSubdistricts(cityApi.getSubdistricts());
        city.setCountry(cityApi.getCountry());
        return city;
    }
}
