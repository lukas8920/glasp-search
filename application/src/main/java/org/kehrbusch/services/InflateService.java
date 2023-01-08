package org.kehrbusch.services;

import org.kehrbusch.DataProvider;
import org.kehrbusch.InitialCityLoader;
import org.kehrbusch.entities.Address;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.Roles;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InflateService {
    private static final Logger logger = Logger.getLogger(InflateService.class.getName());

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${initial.street.data.path}")
    private String pathToInitialStreetData;

    @Value("${initial.city.data.path}")
    private String pathToInitialCityData;

    private final SearchRepository searchRepository;
    private final CityRepository cityRepository;
    private final DataProvider dataProvider;

    @Autowired
    public InflateService(SearchRepository searchRepository, @Qualifier("cityDatabase") CityRepository cityDatabase,
                          DataProvider dataProvider) {
        this.searchRepository = searchRepository;
        this.cityRepository = cityDatabase;
        this.dataProvider = dataProvider;
    }

    public void initZipStreet() throws InternalServerError {
        if (!profile.equals("dev") && !getAuthorities().contains(Roles.ADMIN.toString())) throw new InternalServerError("Internal server error");

        try {
            File file = new File(pathToInitialStreetData);
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<Address> addresses = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] segments = line.split(",");
                Address address = new Address(segments[0], segments[3], segments[4], null, segments[1], 0, false);
                addresses.add(address);
            }
            this.searchRepository.persistAll(addresses);
            addresses.clear();
        } catch (IOException e){
            logger.warning("Error during loading of initial data.");
        }
        logger.info("Finished persisting the initial zipstreet data.");

        this.dataProvider.initZipStreetData();
        logger.info("Finished initializing the street data.");
    }

    public void initCity() throws InternalServerError {
        if (!profile.equals("dev") && !getAuthorities().contains(Roles.ADMIN.toString())) throw new InternalServerError("Internal Server error");

        List<City> cities = InitialCityLoader.load(pathToInitialCityData);
        this.cityRepository.persistAll(cities);
        logger.info("Finished persisting the city data.");

        this.dataProvider.initCityData();
        logger.info("Finished initializing the city data.");
    }

    private List<String> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
