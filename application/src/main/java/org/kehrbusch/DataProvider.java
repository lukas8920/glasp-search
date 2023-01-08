package org.kehrbusch;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.loader.CharElement;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class DataProvider {
    private static final Logger logger = Logger.getLogger(DataProvider.class.getName());

    private static List<CharElement> zipStreets;
    private static Map<String, City> cityMap;

    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    @Qualifier("cityDatabase")
    private CityRepository cityDatabase;

    @PostConstruct
    private void init(){
        DataProvider.zipStreets = searchRepository.getRoots();
        DataProvider.cityMap = cityDatabase.getAllCities();
        logger.info("Initialized search data in RAM.");
    }

    public void initCityData(){
        DataProvider.cityMap = cityDatabase.getAllCities();
        logger.info("City size " + cityMap.size());
    }

    public void initZipStreetData(){
        DataProvider.zipStreets = searchRepository.getRoots();
    }

    public static List<CharElement> provideStreets(){
        return DataProvider.zipStreets;
    }

    public static Map<String, City> provideCities(){
        return DataProvider.cityMap;
    }
}
