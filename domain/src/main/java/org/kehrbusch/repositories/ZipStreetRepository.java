package org.kehrbusch.repositories;

import org.kehrbusch.entities.database.Element;

import java.util.List;

public interface ZipStreetRepository {
    Element find(List<String> keys, String searchKey);
    String getNextFreeKey(String firstElement);
    void add(Element element);
    void addChild(String parent, String child);
    Element getElement(String key);
    List<Element> getRootNodes(String pattern);

    List<Element> getTestElements(String keyPattern);
    void deleteTestElements(List<String> keys);
    List<String> getKeys(String pattern);
}
