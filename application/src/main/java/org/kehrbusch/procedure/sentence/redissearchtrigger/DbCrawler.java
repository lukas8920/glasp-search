package org.kehrbusch.procedure.sentence.redissearchtrigger;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.database.Element;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.procedure.sentence.BaseSearch;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.ZipStreetRepository;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;

@Deprecated
public class DbCrawler extends BaseSearch {
    private static final Logger logger = Logger.getLogger(DbCrawler.class.getName());

    private final ZipStreetRepository zipStreetRepository;
    private final  CityRepository cityRepository;
    private final DbCrawlerThread thread;

    private Element startElement;

    public DbCrawler(ZipStreetRepository zipStreetRepository, Element startElement, Result result,
                     CityRepository cityRepository, DbCrawlerThread thread, int falseChars, int index) {
        super(result, falseChars, index);

        this.cityRepository = cityRepository;
        this.zipStreetRepository = zipStreetRepository;
        this.thread = thread;
        this.startElement = startElement;
    }

    @Override
    public void crawl2(LevenshteinImpl levenshtein) {
        String c = startElement.getData();
        List<String> ids = startElement.getChildren();

        levenshtein.addChar(c, ids.isEmpty());

        LevenshteinImpl.Result result = levenshtein.calculate(this.result::isValidInputWrapper);

        boolean isEmpty = ids.isEmpty();
        if (!result.isCancelled() && result.hasReachedValidatableLength()){

            String[] segments;
            if (ids.isEmpty()){
                segments = result.getFinalString().split(" ", 2);
                //Todo - Region Identifier needs to be adjusted in case of usage
                addToResults(result, isEmpty, segments, "");
            } else {
                String match = result.getFinalString();
                //long startTime = System.currentTimeMillis();
                getFinalStrings(ids, match, m -> {
                    String[] sgs = m.split(" ", 2);
                    //Todo - Region Identifier needs to be adjusted in case of usage
                    return addToResults(result, isEmpty, sgs, "");
                });
                //long endTime = System.currentTimeMillis() - startTime;
                //logger.info("Time: " + endTime);
            }
        }
        if (!result.isCancelled() && !isEmpty){
            DbCrawler crawler = overrideCrawler(zipStreetRepository.getElement(ids.get(0)));

            int tmpIndex = index;
            int tmpFalseChars = falseChars;

            LevenshteinImpl tmpLevenshtein = new LevenshteinImpl(levenshtein);
            crawler.crawl2(levenshtein);
            generateChildCrawlers(ids, tmpLevenshtein, tmpFalseChars, tmpIndex);
        }
    }

    @Override
    protected BiFunction<String, String, City> provideCityFinder() {
        return this.cityRepository::findByZipAndCountry;
    }

    boolean getFinalStrings(List<String> list, String match, Function<String, Boolean> callback){
        for (int counter = 0; counter < list.size(); counter++){
            Element element = zipStreetRepository.getElement(list.get(counter));
            boolean flag = getFinalStrings(element.getChildren(), match + element.getData(), callback);
            if (!flag) return false;
        }
        if (list.isEmpty()){
            return callback.apply(match);
        }
        return true;
    }

    private void generateChildCrawlers(List<String> list, LevenshteinImpl levenshtein,
                                       int falseChars, int index) {
        for (int i = 1; i < list.size(); i++){
            Element element = zipStreetRepository.getElement(list.get(i));
            DbCrawler crawler = new DbCrawler(zipStreetRepository, element, result, cityRepository, thread, falseChars, index);
            if (!this.thread.hasInterruptedSignal()){
                crawler.crawl2(new LevenshteinImpl(levenshtein));
            } else {
                return;
            }
        }
    }

    protected DbCrawler overrideCrawler(Element element) {
        this.startElement = element;
        return this;
    }
}
