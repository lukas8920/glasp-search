package org.kehrbusch.procedure.sentence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.loader.CharElement;
import org.kehrbusch.entities.loader.LastElement;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.entities.Result;
import org.kehrbusch.procedure.sentence.searchtrigger.Crawler;
import org.kehrbusch.repositories.CityRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerTest {
    private final Result result = new Result("123,Test", "IT");

    private final CharElement rootNode = new CharElement(true, null, "1");
    private final CharElement node1 = new CharElement(false, rootNode, "2");
    private final CharElement node2 = new CharElement(false, node1, "4");
    private final CharElement node3 = new CharElement(false, node2, " ");
    private final CharElement node4 = new CharElement(false, node3, "T");
    private final CharElement node5 = new CharElement(false, node4, "e");
    private final CharElement node6 = new CharElement(false, node5, "s");
    private final CharElement node7 = new LastElement(false,"123", node6, "t");

    CityRepository cityRepository;

    Stoppable stoppable = new Stoppable() {
        @Override
        public void setInterruptedSignal() {

        }

        @Override
        public boolean hasInterruptedSignal() {
            return false;
        }

        @Override
        public boolean isRunning() {
            return false;
        }
    };

    @Test
    public void testThatCrawlWorks(){
        Map<String, City> cities = new HashMap<>();
        cities.put("124", new City("124", "123", "city", "country", new ArrayList<>()));
        Crawler crawler = new Crawler(stoppable, result, rootNode, cities, 0, 0);
        crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));

        assertThat(result.getMinFalseChars(), is(6));
        assertThat(result.getAddresses().size(), is(1));
        assertThat(result.getAddresses().get(0).getZip(), is("124"));
        assertThat(result.getAddresses().get(0).getStreet(), is("Test"));
    }

    private final CharElement rootNode3 = new CharElement(true, null, "1");
    private final CharElement node8 = new CharElement(false, rootNode3, "2");
    private final CharElement node9 = new CharElement(false, node8, "4");
    private final CharElement node10 = new CharElement(false, node9, " ");
    private final CharElement node11 = new CharElement(false, node10, "T");
    private final CharElement node12 = new CharElement(false, node11, "e");
    private final CharElement node13 = new CharElement(false, node12, "s");
    private final CharElement node14 = new LastElement(false,"123", node13, "t");
    private final CharElement node15 = new CharElement(false, node8, "3");
    private final CharElement node16 = new CharElement(false, node15, " ");
    private final CharElement node17 = new CharElement(false, node16, "T");
    private final CharElement node18 = new CharElement(false, node17, "e");
    private final CharElement node19 = new CharElement(false, node18, "s");
    private final CharElement node20 = new LastElement(false, "123", node19, "t");
    private final CharElement node21 = new CharElement(false, node11, "e");
    private final CharElement node22 = new LastElement(false, "123", node21, "t");

    @Test
    public void testThatCrawlWorksWithMultipleHits(){
        Map<String, City> cities = new HashMap<>();
        cities.put("123", new City("124", "123", "city", "country", new ArrayList<>()));
        cities.put("124", new City("124", "123", "city", "country", new ArrayList<>()));

        Crawler crawler = new Crawler(stoppable, result, rootNode3, cities, 6, 0);
        crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));

        assertThat(result.getMinFalseChars(), is(6));
        assertThat(result.getAddresses().size(), is(3));
        assertThat(result.getAddresses().get(0).getZip(), is("123"));
        assertThat(result.getAddresses().get(0).getStreet(), is("Test"));
        assertThat(result.getAddresses().get(1).getZip(), is("124"));
        assertThat(result.getAddresses().get(1).getStreet(), is("Test"));
        assertThat(result.getAddresses().get(2).getZip(), is("124"));
        assertThat(result.getAddresses().get(2).getStreet(), is("Tet"));
    }

    private final CharElement rootNode2 = new LastElement(true,"123", null, "d");

    @Test
    public void testThatCrawlWorksWithNoHit(){
        Map<String, City> cities = new HashMap<>();

        Crawler crawler = new Crawler(stoppable, result, rootNode2, cities, 6, 0);
        crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));

        assertThat(result.getMinFalseChars(), is(6));
        assertThat(result.getAddresses().size(), is(0));
    }

    private final CharElement rootNode4 = new CharElement(true, null, "2");
    private final CharElement node23 = new CharElement(false, rootNode4, "5");
    private final CharElement node24 = new CharElement(false, node23, "0");
    private final CharElement node25 = new CharElement(false, node24, "1");
    private final CharElement node26 = new CharElement(false, node25, "2");
    private final CharElement node27 = new CharElement(false, node26, " ");
    private final CharElement node28 = new LastElement(false,"123", node27, "c");

    @Test
    public void testCrawlerWithExceedingSearchString(){
        Map<String, City> cities = new HashMap<>();
        cities.put("53012", new City("53012", "123", "city", "country", new ArrayList<>()));

        Result result = new Result("53012,Stradk Cbscina", "IT");
        Crawler crawler = new Crawler(stoppable, result, rootNode4, cities, 6, 0);
        crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));

        assertThat(result.getMinFalseChars(), is(6));
        assertThat(result.getAddresses().size(), is(1));
    }

    @Test
    public void testThatCrawlInParallelWorks() {
        Map<String, City> cities = new HashMap<>();
        cities.put("124", new City("124", "123", "city", "country", new ArrayList<>()));


        Crawler crawler = new Crawler(stoppable, result, rootNode, cities, 6, 0);
        crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));

        assertThat(result.getMinFalseChars(), is(6));
        assertThat(result.getAddresses().size(), is(1));
        assertThat(result.getAddresses().get(0).getZip(), is("124"));
        assertThat(result.getAddresses().get(0).getStreet(), is("Test"));
    }

    private final CharElement rootNode1 = new CharElement(true, null, "2");
    private final CharElement element1 = new CharElement(false, rootNode1, "1");
    private final CharElement element2 = new CharElement(false, rootNode1, "2");
    private final CharElement element3 = new CharElement(false, rootNode1, "3");
    private final CharElement element4 = new LastElement(false,"x", element1, "1");
    private final CharElement element5 = new LastElement(false,"x", element1, "2");
    private final CharElement element6 = new LastElement(false,"x", element2, "3");
    private final CharElement element7 = new LastElement(false, "x",  element3, "3");

    @Test
    public void testThatGetFinalStringsWorks(){
        Crawler crawler = new Crawler(stoppable, new Result("xxx,xxx",""), null, null, 6, 0);
        List<String> strings = crawler.getFinalStrings(rootNode1, "2");

        assertThat(strings.size(), is(4));
        List<String> converted = strings.stream().map(value -> value.split("_")[0]).collect(Collectors.toList());
        converted.forEach(System.out::println);
        assertThat(converted.containsAll(Arrays.asList("211", "212", "223", "233")), is(true));
    }

    @Test
    public void testThatGetFinalStringsCancelsAfterMaxResults(){
        Crawler crawler = new Crawler(stoppable, new Result("test", "test", "IT", 6, 2 ), null, null, 0, 0);
        List<String> strings = crawler.getFinalStrings(rootNode1, "2");

        assertThat(strings.size(), is(2));
        List<String> converted = strings.stream().map(value -> value.split("_")[0]).collect(Collectors.toList());
        assertThat(converted.containsAll(Arrays.asList("211", "212")), is(true));
    }
}
