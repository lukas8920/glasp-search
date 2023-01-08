package org.kehrbusch.procedure;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityWrapper;

import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class Mapper {
    private static final Logger logger = Logger.getLogger(Mapper.class.getName());

    private static final int mappingThreshold = 4;
    private static final int defaultSummand = 14;

    private final String zipRegionIdentifier;
    private final String country;
    private final BiFunction<String, String, City> cityProvider;

    public Mapper(BiFunction<String, String, City> cityProvider, String zipRegionIdentifier, String country){
        this.zipRegionIdentifier = zipRegionIdentifier;
        this.country = country;
        this.cityProvider = cityProvider;
    }

    public CityWrapper identify(String cityVal){
        //City city = this.cityRepository.findByZipAndCountry(zip, country);
        City city = this.cityProvider.apply(zipRegionIdentifier, country);
        if (city == null) return new CityWrapper("", "", defaultSummand);

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        if (cityVal == null) return new CityWrapper(city.getCity(), city.getCountry(), 0);

        CityWrapper minDistanceSubdistrict = city.getSubdistricts().stream()
                .map(subdistrict -> new CityWrapper(subdistrict, city.getCountry(), levenshteinDistance.apply(subdistrict, cityVal)))
                .min(Comparator.comparingInt(CityWrapper::getFalseChars))
                .orElse(new CityWrapper("", "", defaultSummand));
        return minDistanceSubdistrict.getFalseChars() > mappingThreshold
                ? new CityWrapper(city.getCity(), city.getCountry(), levenshteinDistance.apply(city.getCity(), cityVal))
                : minDistanceSubdistrict;
    }
}
