package org.kehrbusch.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.kehrbusch.jackson.ZipStreetApiSerializer;
import org.kehrbusch.jackson.ZipStreetApiDeserializer;

import java.util.List;

public class ZipStreetInsert {
    @JsonSerialize(using = ZipStreetApiSerializer.class)
    @JsonDeserialize(using = ZipStreetApiDeserializer.class)
    private List<ZipStreetApi> zipStreetApis;

    public ZipStreetInsert(){}

    public ZipStreetInsert(List<ZipStreetApi> zipStreetApis) {
        this.zipStreetApis = zipStreetApis;
    }

    public List<ZipStreetApi> getZipStreetApis() {
        return zipStreetApis;
    }

    public void setZipStreetApis(List<ZipStreetApi> zipStreetApis) {
        this.zipStreetApis = zipStreetApis;
    }
}
