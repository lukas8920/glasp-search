package org.kehrbusch.procedure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityWrapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    public BiFunction<String, String, City> ciyFinder(Map<String, City> cityMap){
        return (zip, country) -> cityMap.get(zip);
    }

    @Test
    public void testThatIdentifyWorks(){
        Map<String, City> cities = new HashMap<>();
        cities.put("45138_123", new City("45138", "123", "Rom", "IT", List.of("Centurio")));
        String zip = "45138_123";

        Mapper mapper = new Mapper(ciyFinder(cities), zip, "IT");
        CityWrapper cityWrapper = mapper.identify("Roma");

        assertThat(cityWrapper.getCity(), is("Rom"));
        assertThat(cityWrapper.getFalseChars(), is(1));
        assertThat(cityWrapper.getCountry(), is("IT"));
    }

    @Test
    public void testThatIdentifyReturnsHighFalseCharsWhenNonIdentifiable(){
        Map<String, City> cityMap = new HashMap<>();
        String zip = "45138";

        Mapper mapper = new Mapper(ciyFinder(cityMap), zip, "IT");
        CityWrapper cityWrapper = mapper.identify("Roma");

        assertThat(cityWrapper.getFalseChars() > 10, is(true));
    }

    @Test
    public void testThatIdentifyRecognizesBetterSuitingSubdistrict(){
        Map<String, City> cities = new HashMap<>();
        cities.put("45138_123", new City("45138", "123", "Roma", "IT", List.of("Rom")));
        String zip = "45138_123";

        Mapper mapper = new Mapper(ciyFinder(cities), zip, "IT");
        CityWrapper cityWrapper = mapper.identify("Roma");

        assertThat(cityWrapper.getFalseChars(), is(1));
        assertThat(cityWrapper.getCity(), is("Rom"));
        assertThat(cityWrapper.getCountry(), is("IT"));
    }
}
