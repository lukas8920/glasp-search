package org.kehrbusch.entities.loader;

public class LastElement extends CharElement {
    private String regionIdentifier;

    public LastElement(Boolean isRoot, String regionIdentifier, CharElement parent, String data) {
        super(isRoot, parent, data);
        this.regionIdentifier = regionIdentifier;
    }

    public String getRegionIdentifier() {
        return regionIdentifier;
    }

    public void setRegionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
    }
}
