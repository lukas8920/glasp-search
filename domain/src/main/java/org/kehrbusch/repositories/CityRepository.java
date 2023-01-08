package org.kehrbusch.repositories;

import org.kehrbusch.entities.City;

import java.util.List;
import java.util.Map;

public interface CityRepository {
    City findByZipAndCountry(String zip, String country);
    void persist(City city);
    void persistAll(List<City> cities);
    Map<String, City> getAllCities();
}
