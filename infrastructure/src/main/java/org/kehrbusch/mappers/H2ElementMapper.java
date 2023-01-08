package org.kehrbusch.mappers;

import org.kehrbusch.entities.database.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class H2ElementMapper {
    public Element map(Map<String, String> element, List<String> children){
        String parent = element.get("parent");
        String key = element.get("key");
        String data = element.get("data");
        return new Element(parent, data, key, children);
    }

    public Map<String, String> map(Element element){
        Map<String, String> map = new HashMap<>();
        map.put("parent", element.getParent());
        map.put("key", element.getKey());
        map.put("data", element.getData());
        return map;
    }
}
