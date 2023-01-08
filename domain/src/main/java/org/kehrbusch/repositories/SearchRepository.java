package org.kehrbusch.repositories;

import org.kehrbusch.entities.Address;
import org.kehrbusch.entities.loader.CharElement;

import java.util.List;

public interface SearchRepository {
    void persist(Address address);
    void persistAll(List<Address> addresses);
    List<CharElement> getRoots();
}
