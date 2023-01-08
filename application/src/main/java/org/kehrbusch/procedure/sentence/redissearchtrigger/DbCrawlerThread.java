package org.kehrbusch.procedure.sentence.redissearchtrigger;

import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.database.Element;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.procedure.sentence.Stoppable;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.ZipStreetRepository;

@Deprecated
public class DbCrawlerThread extends Thread implements Stoppable {
    private final DbCrawler dbCrawler;
    private final Result result;

    private boolean hasInterruptedSignal = false;

    public DbCrawlerThread(ZipStreetRepository zipStreetRepository, Element element, CityRepository cityRepository, Result result){
        this.dbCrawler = new DbCrawler(zipStreetRepository, element, result, cityRepository, this,
                0, 0);
        this.result = result;
    }

    @Override
    public void run() {
        dbCrawler.crawl2(new LevenshteinImpl(result.getSearchZipStreet()));
    }

    @Override
    public boolean hasInterruptedSignal() {
        return hasInterruptedSignal;
    }

    @Override
    public void setInterruptedSignal() {
        this.hasInterruptedSignal = true;
    }

    @Override
    public boolean isRunning(){
        return super.isAlive();
    }
}
