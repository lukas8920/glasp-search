package org.kehrbusch.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kehrbusch.services.redis.DbInflateService;
import org.kehrbusch.entities.database.Element;
import org.kehrbusch.entities.database.ZipStreet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbInflateServiceTest {
    @Autowired
    DbInflateService dbInflateService;

    @Test
    public void testThatAddZipStreetWorks(){
        List<Element> elements = dbInflateService.getTestElements("ITS*");
        if (elements.size() > 0)
        dbInflateService.deleteTestElements(elements.stream().map(Element::getKey).collect(Collectors.toList()));

        ZipStreet zipStreet = new ZipStreet("123", "test");
        dbInflateService.add(zipStreet);

        elements = dbInflateService.getTestElements("ITS1*");
        System.out.println("Test elements inside db");
        //elements.forEach(Element::toString);
        assertThat(elements.size(), is(8));

        System.out.println("#######");
        System.out.println("Clear from database");
        System.out.println("#######");

        dbInflateService.deleteTestElements(elements.stream().map(Element::getKey).collect(Collectors.toList()));

        System.out.println("#######");
        System.out.println("Validate that db is empty");
        System.out.println("#######");

        elements = dbInflateService.getTestElements("ITS1*");
        assertThat(elements.size(), is(0));
    }

    @Test
    public void testZipStreetWithMultipleChildren(){
        ZipStreet zipStreet = new ZipStreet("12", "te");
        ZipStreet zipStreet1 = new ZipStreet("12","se");

        dbInflateService.add(zipStreet);
        dbInflateService.add(zipStreet1);

        List<Element> elements = dbInflateService.getTestElements("ITS1*");
        assertThat(elements.size(), is(7));

        Element element = elements.stream().filter(elm -> elm.getKey().equals("ITS12")).findFirst().get();
        assertThat(element.getData(), is("2"));
        assertThat(element.getParent(), is("ITS11"));
        assertThat(element.getChildren().size(), is(1));

        Element element1 = elements.stream().filter(elm -> elm.getKey().equals("ITS13")).findFirst().get();
        assertThat(element1.getData(), is(" "));
        assertThat(element1.getParent(), is("ITS12"));
        assertThat(element1.getChildren().size(), is(2));

        dbInflateService.deleteTestElements(elements.stream().map(Element::getKey).collect(Collectors.toList()));
        elements = dbInflateService.getTestElements("ITS1*");
        assertThat(elements.size(), is(0));
    }

    @Test
    public void testThatFilteringMaxWorks(){
        List<String> keys = new ArrayList<>();
        keys.add("ITS11");
        keys.add("ITS12");
        keys.add("ITS13");
        keys.add("ITS14");
        keys.add("ITS15");
        keys.add("ITS16");
        keys.add("ITS17");
        keys.add("ITS18");
        keys.add("ITS19");
        keys.add("ITS110");

        Double max = keys.stream()
                .filter(test -> test.matches("ITS[0-9].*"))
                .map(test -> test.replace(":children", ""))
                .map(test -> Double.parseDouble(test.substring(4)))
                .max(Double::compareTo)
                .orElse(1.0);

        System.out.println(max);
    }
}
