package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityH2;
import org.kehrbusch.mappers.H2CityMapper;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.queries.CityH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Qualifier("cityDatabase")
public class CityRepositoryImpl implements CityRepository {
    private static final Logger logger = Logger.getLogger(CityRepositoryImpl.class.getName());

    private final H2CityMapper h2CityMapper;
    private final CityH2Repository cityH2Repository;

    @Autowired
    public CityRepositoryImpl(H2CityMapper h2CityMapper, CityH2Repository cityH2Repository) {
        this.h2CityMapper = h2CityMapper;
        this.cityH2Repository = cityH2Repository;
    }

    @Override
    public City findByZipAndCountry(String zip, String country) {
        return null;
    }

    @Override
    public void persist(City city) {
        CityH2 cityH2 = this.h2CityMapper.map(city);
        this.cityH2Repository.save(cityH2);
    }

    @Override
    public void persistAll(List<City> cities) {
        this.cityH2Repository.saveAll(cities
                .stream()
                .map(this.h2CityMapper::map)
                .collect(Collectors.toList()));
    }

    @Override
    public Map<String, City> getAllCities() {
        return this.cityH2Repository.getAllByCountry("IT").stream()
                .map(this.h2CityMapper::map)
                .collect(Collectors.toMap(city -> city.getZip() + "_" + city.getRegionIdentifier(), city -> city, (city1, city2) -> {
                    logger.info("Dupplicate key for zip " + city1.getZip());
                    return city2;
                }));
    }
}
