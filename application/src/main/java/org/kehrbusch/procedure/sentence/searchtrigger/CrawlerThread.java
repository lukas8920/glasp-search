package org.kehrbusch.procedure.sentence.searchtrigger;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.loader.Node;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.procedure.sentence.Stoppable;

import java.util.Map;

public class CrawlerThread extends Thread implements Stoppable {
    private final Crawler crawler;
    private final Result result;

    private boolean hasInterruptedSignal = false;

    public CrawlerThread(Node zipStreets, Map<String, City> cities, Result result){
        this.crawler = new Crawler(this, result, zipStreets, cities, 0, 0);
        this.result = result;
    }

    @Override
    public void run(){
        this.crawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));
    }

    @Override
    public boolean hasInterruptedSignal(){
        return this.hasInterruptedSignal;
    }

    @Override
    public void setInterruptedSignal(){
        this.hasInterruptedSignal = true;
    }

    @Override
    public boolean isRunning(){
        return super.isAlive();
    }
}