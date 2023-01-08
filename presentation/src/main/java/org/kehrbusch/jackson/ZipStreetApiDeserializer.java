package org.kehrbusch.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.kehrbusch.entities.ZipStreetApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZipStreetApiDeserializer extends JsonDeserializer<List<ZipStreetApi>> {
    @Override
    public List<ZipStreetApi> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        List<ZipStreetApi> zipStreetApis = new ArrayList<>();
        if (jsonNode.isArray()){
            for (JsonNode node : jsonNode){
                zipStreetApis.add(objectCodec.treeToValue(node, ZipStreetApi.class));
            }
        } else {
            zipStreetApis.add(objectCodec.treeToValue(jsonNode, ZipStreetApi.class));
        }
        return zipStreetApis;
    }
}
