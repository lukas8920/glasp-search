package org.kehrbusch.mapper;

import org.kehrbusch.entities.ElementApi;
import org.kehrbusch.entities.database.Element;
import org.springframework.stereotype.Component;

@Component
public class ElementMapper {
    public Element map(ElementApi elementApi){
        Element element = new Element();
        element.setParent(elementApi.getParent() == null ? "" : elementApi.getParent());
        element.setKey(elementApi.getKey());
        element.setData(elementApi.getData());
        element.addAllChildren(elementApi.getChildren());
        return element;
    }
}
