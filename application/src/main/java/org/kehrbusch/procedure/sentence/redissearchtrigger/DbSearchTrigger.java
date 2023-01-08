package org.kehrbusch.procedure.sentence.redissearchtrigger;

import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.database.Element;
import org.kehrbusch.procedure.sentence.TimeoutTask;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.ZipStreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Timer;

//Deprecated as Redis is too slow for production environment
@Component
@Deprecated
public class DbSearchTrigger {
    private static final int TIMEOUT_MS = 3000;

    private final ZipStreetRepository zipStreetRepository;
    private final CityRepository cityRepository;

    @Autowired
    public DbSearchTrigger(ZipStreetRepository zipStreetRepository, @Qualifier("redisCityRepository") CityRepository cityRepository){
        this.zipStreetRepository = zipStreetRepository;
        this.cityRepository = cityRepository;
    }

    public Result identify(String zip, String street, String country, int maxErrors, int noOfResults){
        Result result = new Result(zip, street, country, maxErrors, noOfResults);
        return identify(result, zipStreetRepository, cityRepository);
    }

    public Result identify(String zip, String street, String city, String country, int maxErrors, int noOfResults){
        Result result = new Result(zip, street, city, country, maxErrors, noOfResults);
        return identify(result, zipStreetRepository, cityRepository);
    }

    private Result identify(Result result, ZipStreetRepository repository, CityRepository cityRepository){
        DbCrawlerThread[] threads = new DbCrawlerThread[10];
        int j = 0;

        for (Element element : repository.getRootNodes(result.getSearchCountry() + "S")){
            threads[j] = new DbCrawlerThread(zipStreetRepository, element, cityRepository, result);
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
