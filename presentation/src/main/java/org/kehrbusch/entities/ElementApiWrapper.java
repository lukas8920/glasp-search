package org.kehrbusch.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.kehrbusch.jackson.ElementApiDeserializer;
import org.kehrbusch.jackson.ElementApiSerializer;

import java.util.List;

public class ElementApiWrapper {
    @JsonDeserialize(using = ElementApiDeserializer.class)
    @JsonSerialize(using = ElementApiSerializer.class)
    private List<ElementApi> elementApis;

    public ElementApiWrapper(){}

    public ElementApiWrapper(List<ElementApi> elementApis) {
        this.elementApis = elementApis;
    }

    public List<ElementApi> getElementApis() {
        return elementApis;
    }

    public void setElementApis(List<ElementApi> elementApis) {
        this.elementApis = elementApis;
    }
}
