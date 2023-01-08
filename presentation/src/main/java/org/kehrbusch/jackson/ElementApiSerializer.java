package org.kehrbusch.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.kehrbusch.entities.ElementApi;

import java.io.IOException;
import java.util.List;

public class ElementApiSerializer extends JsonSerializer<List<ElementApi>> {
    @Override
    public void serialize(List<ElementApi> elementApis, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(elementApis);
    }
}
