package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.CityH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityH2Repository extends CrudRepository<CityH2, Long> {
    public List<CityH2> getAllByCountry(String country);
}
