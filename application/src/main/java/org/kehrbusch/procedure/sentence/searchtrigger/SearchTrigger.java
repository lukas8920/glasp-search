package org.kehrbusch.procedure.sentence.searchtrigger;

import org.kehrbusch.DataProvider;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.loader.CharElement;
import org.kehrbusch.entities.loader.Node;
import org.kehrbusch.procedure.sentence.TimeoutTask;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Logger;

@Component
public class SearchTrigger {
    private static final Logger logger = Logger.getLogger(SearchTrigger.class.getName());

    private static final int TIMEOUT_MS = 3000;

    public Result identify(String zip, String street, String country, int maxErrors, int noOfResults){
        Result result = new Result(zip, street, country, maxErrors, noOfResults);
        return identify(result);
    }

    public Result identify(String zip, String street, String city, String country, int maxErrors, int noOfResults){
        Result result = new Result(zip, street, city, country, maxErrors, noOfResults);
        return identify(result);
    }

    private Result identify(Result result){
        List<CharElement> zipStreets = DataProvider.provideStreets();
        Map<String, City> cities = DataProvider.provideCities();
        if (cities == null || zipStreets == null) {
            logger.info("Search data is not initialized.");
            return result;
        }

        CrawlerThread[] threads = new CrawlerThread[10];
        int j = 0;

        for (Node root : zipStreets){
            threads[j] = new CrawlerThread(root, cities, result);
            threads[j].start();
            j += 1;
        }

        Timer timer = new Timer();
        TimeoutTask timeoutTask = new TimeoutTask(threads, timer);
        timer.schedule(timeoutTask, TIMEOUT_MS);

        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timeoutTask.stopTimer();
        return result;
    }
}
