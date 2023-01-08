package org.kehrbusch.repositories;

import org.kehrbusch.entities.Jwt;

public interface JwtRepository {
    public void save(Jwt jwt);
    public Jwt findJwtById(Long id);
    public void updateJwt(Jwt jwt);
}
