package org.kehrbusch.procedure.sentence.searchtrigger;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.loader.CharElement;
import org.kehrbusch.entities.loader.LastElement;
import org.kehrbusch.entities.loader.Node;
import org.kehrbusch.procedure.LevenshteinImpl;
import org.kehrbusch.entities.Result;
import org.kehrbusch.procedure.sentence.BaseSearch;
import org.kehrbusch.procedure.sentence.Stoppable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class Crawler extends BaseSearch {
    private static final Logger logger = Logger.getLogger(Crawler.class.getName());

    protected Node rootNode;
    protected Map<String, City> cities;

    private final Stoppable stoppable;

    public Crawler(Stoppable stoppable, Result result, Node node, Map<String, City> cities, int falseChars, int index) {
        super(result, falseChars, index);

        this.stoppable = stoppable;
        this.result = result;
        this.cities = cities;
        this.rootNode = node;
    }

    public void crawl2(LevenshteinImpl levenshtein){
        String c = rootNode.getNodeValue();
        List<CharElement> list = rootNode.getChildNodes();

        levenshtein.addChar(c, list.isEmpty());

        LevenshteinImpl.Result result = levenshtein.calculate(this.result::isValidInputWrapper);

        boolean isEmpty = list.isEmpty();
        /*maxValidLength equals length of search string, any further letter is assumptive
        because we do not know whether the user intends further input*/
        if (!result.isCancelled() && result.hasReachedValidatableLength()){
            String[] segments;
            if (isEmpty){
                segments = result.getFinalString().split(" ", 2);
                if(addToResults(result, isEmpty, segments, ((LastElement) rootNode).getRegionIdentifier())){
                    //not necessary to run crawler on children
                    result.setHasBeenAddedToResults();
                };
            } else {
                String match = result.getFinalString();
                for (String finalString : getFinalStrings(rootNode, match)){
                    segments = finalString.split("_");
                    String[] subSegments = segments[0].split(" ", 2);
                    if (addToResults(result, isEmpty, subSegments, segments[1])){
                        //not necessary to run crawler on children
                        result.setHasBeenAddedToResults();
                    };
                }
            }
        }
        if (!result.isCancelled() && !isEmpty && !result.hasBeenAddedToResults()){
            Crawler crawler = overrideCrawler(list.get(0));

            int tmpIndex = index;
            int tmpFalseChars = falseChars;

            LevenshteinImpl tmpLevenshtein = new LevenshteinImpl(levenshtein);
            crawler.crawl2(levenshtein);
            generateChildCrawlers(list, tmpLevenshtein, tmpFalseChars, tmpIndex);
        }
    }

    @Override
    protected BiFunction<String, String, City> provideCityFinder() {
        return (zip, country) -> this.cities.get(zip);
    }

    public List<String> getFinalStrings(Node baseElement, String match){
        List<CharElement> list = baseElement.getChildNodes();
        List<String> strings = new ArrayList<>();
        for (int counter = 0; counter < list.size(); counter++){
            Node tmpNode = list.get(counter);
            strings.addAll(getFinalStrings(tmpNode, match + tmpNode.getNodeValue()));
            if (strings.size() == result.getSpaceForAddresses()){
                return strings;
            }
        }
        if (list.isEmpty()){
            String regionIdentifier = ((LastElement) baseElement).getRegionIdentifier();
            strings.add(match + "_" + regionIdentifier);
        }
        return strings;
    }

    private void generateChildCrawlers(List<CharElement> list, LevenshteinImpl levenshtein,
                                       int falseChars, int index) {
        int counter = 1;
        int size = list.size() - 1;
        if (counter > size) return;
        Node targetNode = list.get(counter);

        while (targetNode != null && !this.stoppable.hasInterruptedSignal()) {
            Crawler crawler = new Crawler(stoppable, result, targetNode, cities, falseChars, index);
            crawler.crawl2(new LevenshteinImpl(levenshtein));

            counter += 1;
            targetNode = counter > size ? null : list.get(counter);
        }
    }

    protected Crawler overrideCrawler(Node node) {
        this.rootNode = node;
        return this;
    }
}
