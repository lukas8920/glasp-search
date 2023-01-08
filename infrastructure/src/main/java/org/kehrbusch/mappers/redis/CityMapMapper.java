package org.kehrbusch.mappers.redis;

import org.kehrbusch.entities.City;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Deprecated
public class CityMapMapper {
    public Map<String, String> map(City city){
        Map<String, String> map = new HashMap<>();
        map.put("city", city.getCity());
        map.put("country", city.getCountry());
        return map;
    }

    public City map(Map<String, String> map, String key){
        City city = new City();
        city.setZip(key.substring(3));
        city.setCity(map.get("city"));
        city.setCountry(map.get("country"));
        return city;
    }
}
