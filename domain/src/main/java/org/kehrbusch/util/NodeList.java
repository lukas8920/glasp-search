package org.kehrbusch.util;

import org.kehrbusch.entities.loader.Node;

import java.util.List;

public interface NodeList<T extends Node> extends List<T> {
    boolean isEmpty();
    int getLength();
}
