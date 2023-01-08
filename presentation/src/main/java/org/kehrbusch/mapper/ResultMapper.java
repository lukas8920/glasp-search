package org.kehrbusch.mapper;

import org.kehrbusch.entities.Result;
import org.kehrbusch.entities.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ResultMapper {
    private final AddressMapper addressMapper;

    @Autowired
    public ResultMapper(AddressMapper addressMapper){
        this.addressMapper = addressMapper;
    }

    public SearchResult map(Result result){
        SearchResult searchResult = new SearchResult();
        searchResult.addAddresses(result.getAddresses()
                .stream()
                .map(addressMapper::map)
                .collect(Collectors.toList()));
        return searchResult;
    }
}
