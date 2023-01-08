package org.kehrbusch.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.kehrbusch.entities.ElementApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElementApiDeserializer extends JsonDeserializer<List<ElementApi>> {
    @Override
    public List<ElementApi> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        List<ElementApi> elementApis = new ArrayList<>();
        if (jsonNode.isArray()){
            for (JsonNode node : jsonNode){
                elementApis.add(objectCodec.treeToValue(node, ElementApi.class));
            }
        } else {
            elementApis.add(objectCodec.treeToValue(jsonNode, ElementApi.class));
        }
        return elementApis;
    }
}
