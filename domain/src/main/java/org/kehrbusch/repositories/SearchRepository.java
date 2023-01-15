package org.kehrbusch.repositories;

import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.loader.CharElement;

import java.util.List;

public interface SearchRepository {
    void persist(AddressDomain addressDomain);
    void persistAll(List<AddressDomain> addressDomains);
    List<CharElement> getRoots();
}
