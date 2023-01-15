package org.kehrbusch.procedure.sentence;

import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.City;
import org.kehrbusch.entities.CityWrapper;
import org.kehrbusch.entities.Result;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.procedure.Mapper;

import java.util.function.BiFunction;
import java.util.logging.Logger;

public abstract class BaseSearch {
    private static final Logger logger = Logger.getLogger(BaseSearch.class.getName());

    protected Result result;

    protected final int falseChars;
    protected final int index;

    public BaseSearch(Result result, int falseChars, int index){
        this.result = result;
        this.falseChars = falseChars;
        this.index = index;
    }

    protected abstract void crawl2(LevenshteinImpl levenshtein);

    protected boolean addToResults(LevenshteinImpl.Result result, boolean isEmpty, String[] segments, String regionIdentifier) {
        if (segments.length > 1){
            //System.out.println(result.getFinalString() + " " + result.getDistance());
            //Maybe move mapper after insertion to save time
            String zipRegionIdentifier = segments[0] + "_" + regionIdentifier;
            Mapper mapper = new Mapper(provideCityFinder(), zipRegionIdentifier, this.result.getSearchCountry());
            //logger.info("Base Search zip " + segments[0] + " - street " + segments[1]);
            CityWrapper wrapper = mapper.identify(this.result.getSearchCity());

            return this.result.addAddress(new AddressDomain(regionIdentifier, segments[0], segments[1], wrapper.getCity(),
                    wrapper.getCountry(), result.getDistance(), isEmpty));
        }
        return false;
    }

    protected abstract BiFunction<String, String, City> provideCityFinder();
}
