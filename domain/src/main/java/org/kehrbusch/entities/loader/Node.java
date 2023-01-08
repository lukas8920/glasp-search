package org.kehrbusch.entities.loader;

import java.util.List;

public interface Node {
    String getNodeValue();
    List<CharElement> getChildNodes();
    Node getFirstChild();
    void appendChild(CharElement node);
    Long getLength();
    void setLength(Long value);
}
