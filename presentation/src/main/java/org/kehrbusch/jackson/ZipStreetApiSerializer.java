package org.kehrbusch.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.kehrbusch.entities.ZipStreetApi;

import java.io.IOException;
import java.util.List;

public class ZipStreetApiSerializer extends JsonSerializer<List<ZipStreetApi>> {
    @Override
    public void serialize(List<ZipStreetApi> zipStreetApis, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(zipStreetApis);
    }
}
