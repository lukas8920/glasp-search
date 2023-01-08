package org.kehrbusch;

import org.kehrbusch.entities.City;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class InitialCityLoader {
    private static final Logger logger = Logger.getLogger(InitialCityLoader.class.getName());

    public static List<City> load(String path){
        File file = new File(path);
        List<City> cities = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                String[] segments = line.split(":");
                String regionIdentifier = segments[0];
                String zip = segments[1];
                String city = segments[2];
                String country = segments[3];
                List<String> subdistricts = Arrays.asList(segments[4].split(","));

                City tmpCity = new City(zip, regionIdentifier, city, country, subdistricts);
                cities.add(tmpCity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
