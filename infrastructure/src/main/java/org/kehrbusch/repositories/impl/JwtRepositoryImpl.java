package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.Jwt;
import org.kehrbusch.entities.JwtH2;
import org.kehrbusch.mappers.H2JwtMapper;
import org.kehrbusch.repositories.JwtRepository;
import org.kehrbusch.repositories.queries.JwtH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class JwtRepositoryImpl implements JwtRepository {
    private final JwtH2Repository jwtH2Repository;
    private final H2JwtMapper h2JwtMapper;

    @Autowired
    public JwtRepositoryImpl(JwtH2Repository jwtH2Repository, H2JwtMapper h2JwtMapper) {
        this.jwtH2Repository = jwtH2Repository;
        this.h2JwtMapper = h2JwtMapper;
    }

    @Override
    public void save(Jwt jwt) {
        JwtH2 jwtH2 = this.h2JwtMapper.map(jwt);
        this.jwtH2Repository.save(jwtH2);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Override
    public Jwt findJwtById(Long id) {
        Optional<JwtH2> jwtH2 = this.jwtH2Repository.findById(id);
        return jwtH2.isEmpty() ? null : this.h2JwtMapper.map(jwtH2.get());
    }

    @Override
    public void updateJwt(Jwt jwt) {
        this.jwtH2Repository.updateToken(jwt.getAccessToken(), jwt.getId());
        this.jwtH2Repository.updateExpiryDate(jwt.getExpiryDate(), jwt.getId());
    }
}
