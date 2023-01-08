package org.kehrbusch.repositories;

import org.kehrbusch.entities.loader.CharElement;

import java.util.Set;

public interface NodeRepository {
    Set<CharElement> getRootNodes();
}
