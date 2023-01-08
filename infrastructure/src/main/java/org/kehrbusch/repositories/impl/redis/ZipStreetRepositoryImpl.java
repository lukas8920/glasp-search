package org.kehrbusch.repositories.impl.redis;

import org.kehrbusch.entities.database.Element;
import org.kehrbusch.mappers.H2ElementMapper;
import org.kehrbusch.repositories.ZipStreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Deprecated
public class ZipStreetRepositoryImpl implements ZipStreetRepository {
    private static final Logger logger = Logger.getLogger(ZipStreetRepositoryImpl.class.getName());

    private final ReactiveStringRedisTemplate template;
    private final ReactiveHashOperations<String, String, String> hashOperations;
    private final ReactiveListOperations<String, String> listOperations;
    private final H2ElementMapper mapper;

    @Autowired
    public ZipStreetRepositoryImpl(ReactiveStringRedisTemplate redisTemplate, H2ElementMapper mapper) {
        this.template = redisTemplate;
        this.mapper = mapper;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public Element find(List<String> keys, String searchKey) {
        Validity validity = new Validity();
        for (String key : keys) {
            hashOperations.get(key, "data")
                    .doOnNext(tmpKey -> {
                        //System.out.println("Data: " + tmpKey + " - searchData: " + searchKey);
                        if (tmpKey.equals(searchKey)){
                            validity.setValid(true);
                            validity.setKey(key);
                        }
                    })
                    .block();
            if (validity.isValid){
                return getElement(validity.key);
            }
        }
        return null;
    }

    @Override
    public String getNextFreeKey(String firstElement) {
        List<String> keys = new ArrayList<>();
        template.keys("ITS" + firstElement + "*")
                .doOnNext(keys::add)
                .blockLast();
        Double max = keys.stream()
                .filter(test -> test.matches("ITS[0-9].*"))
                .map(test -> test.replace(":children", ""))
                .map(test -> Double.parseDouble(test.substring(4)))
                .max(Double::compareTo)
                .orElse(1.0);
        String maxValue = new DecimalFormat("#")
                .format(max + 1);
        return String.valueOf(maxValue);
    }

    @Override
    public void add(Element element) {
        Map<String, String> map = mapper.map(element);
        hashOperations.putAll(element.getKey(), map).block();
    }

    @Override
    public void addChild(String parent, String child) {
        listOperations.leftPush(parent + ":children", child).block();
    }

    @Override
    public List<Element> getTestElements(String pattern){
        List<String> keys = new ArrayList<>();
        template.keys(pattern)
                .doOnNext(keys::add)
                .blockLast();
        keys = keys.stream().filter(key -> !key.endsWith(":children")).collect(Collectors.toList());

        List<Element> elements = new ArrayList<>();
        for (String key : keys){
            elements.add(getElement(key));
        }

        return elements;
    }

    @Override
    public void deleteTestElements(List<String> keys){
        String[] tmpKeys = new String[keys.size()];
        keys.toArray(tmpKeys);
        template.delete(tmpKeys).block();
    }

    @Override
    public Element getElement(String key){
        Element element = new Element();
        hashOperations.entries(key)
                .doOnNext(entry -> {
                    switch (entry.getKey()) {
                        case "key":
                            element.setKey(entry.getValue());
                            break;
                        case "data":
                            element.setData(entry.getValue());
                            break;
                        case "parent":
                            element.setParent(entry.getValue());
                            break;
                    }
                })
                .blockLast();

        listOperations.range(key + ":children", 0, -1)
                .doOnNext(element::addChild)
                .blockLast();

        return element;
    }

    @Override
    public List<String> getKeys(String pattern){
        List<String> keys = new ArrayList<>();
        template.keys(pattern).doOnNext(keys::add).blockLast();
        return keys;
    }

    @Override
    public List<Element> getRootNodes(String pattern) {
        List<Element> elements = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            elements.add(this.getElement("ITS" + i + "1"));
        }
        return elements;
    }

    static class Validity {
        private boolean isValid = false;
        private String key;

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
