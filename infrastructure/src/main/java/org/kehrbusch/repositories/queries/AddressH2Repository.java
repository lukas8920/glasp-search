package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.AddressH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressH2Repository extends CrudRepository<AddressH2, Long> {

}
