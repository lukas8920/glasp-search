package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.JwtH2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface JwtH2Repository extends CrudRepository<JwtH2, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update JwtH2 jwtH2 set jwtH2.accessToken =:token where jwtH2.id =:id")
    public void updateToken(@Param("token") String token, @Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update JwtH2 jwtH2 set jwtH2.expiryDate =:expiryDate where jwtH2.id =:id")
    public void updateExpiryDate(@Param("expiryDate") String expiryDate, @Param("id") Long id);
}
