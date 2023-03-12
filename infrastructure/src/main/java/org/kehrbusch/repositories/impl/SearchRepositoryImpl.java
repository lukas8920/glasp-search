package org.kehrbusch.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.kehrbusch.entities.AddressDomain;
import org.kehrbusch.entities.AddressH2;
import org.kehrbusch.entities.loader.CharElement;
import org.kehrbusch.entities.loader.LastElement;
import org.kehrbusch.mappers.H2AddressMapper;
import org.kehrbusch.repositories.SearchRepository;
import org.kehrbusch.repositories.queries.AddressH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SearchRepositoryImpl implements SearchRepository {
    private final H2AddressMapper h2AddressMapper;
    private final AddressH2Repository addressH2Repository;

    @PersistenceContext
    private EntityManager entityManager;

    private final CharElement[] roots = {
            new CharElement(true, null, "0"),
            new CharElement(true, null, "1"),
            new CharElement(true, null, "2"),
            new CharElement(true, null, "3"),
            new CharElement(true, null, "4"),
            new CharElement(true, null, "5"),
            new CharElement(true, null, "6"),
            new CharElement(true, null, "7"),
            new CharElement(true, null, "8"),
            new CharElement(true, null, "9")
    };

    @Autowired
    public SearchRepositoryImpl(AddressH2Repository addressH2Repository, H2AddressMapper h2AddressMapper){
        this.h2AddressMapper = h2AddressMapper;
        this.addressH2Repository = addressH2Repository;
    }

    @Override
    public void persist(AddressDomain addressDomain) {
        AddressH2 addressH2 = this.h2AddressMapper.map(addressDomain);
        this.addressH2Repository.save(addressH2);
    }

    @Override
    public void persistAll(List<AddressDomain> addressDomains) {
        this.addressH2Repository.saveAll(addressDomains
                .stream()
                .map(this.h2AddressMapper::map)
                .collect(Collectors.toList()));
    }

    @Override
    public List<CharElement> getRoots() {
        long count = this.addressH2Repository.count();
        int incrementer = 100000;
        for(int i = 0; i < count; i+= incrementer){
            List<AddressH2> addressH2s = this.entityManager
                    .createNativeQuery("SELECT * " +
                            "FROM addressh2 a " +
                            "ORDER BY a.id", AddressH2.class)
                    .setFirstResult(i)
                    .setMaxResults(incrementer)
                    .getResultList();
            addressH2s.forEach(addressH2 -> {
                String zipStreet = addressH2.getZip() + " " + addressH2.getStreet();

                char firstChar = zipStreet.charAt(0);
                CharElement node = Arrays.stream(roots)
                        .filter(tmpNode -> tmpNode.getNodeValue().equals(String.valueOf(firstChar)))
                        .findFirst().orElse(null);

                if (node != null){
                    for (int j = 1; j < zipStreet.length(); j++){
                        char current = zipStreet.charAt(j);
                        List<CharElement> children = node.getChildNodes();

                        CharElement childNode = children.stream()
                                .filter(tmpNode -> Objects.equals(tmpNode.getNodeValue(), String.valueOf(current)))
                                .findFirst()
                                .orElse(null);
                        if (childNode != null){
                            node = childNode;
                        } else if (j == (zipStreet.length() - 1)){
                            node = new LastElement(false, addressH2.getRegionIdentifier(), node, String.valueOf(current));
                        } else {
                            node = new CharElement(false, node, String.valueOf(current));
                        }
                    }
                }
            });
        }
        return List.of(roots);
    }
}
