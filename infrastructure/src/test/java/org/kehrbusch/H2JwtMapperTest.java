package org.kehrbusch;

import org.junit.Test;
import org.kehrbusch.entities.Jwt;
import org.kehrbusch.entities.JwtH2;
import org.kehrbusch.entities.Roles;
import org.kehrbusch.mappers.H2JwtMapper;
import java.util.List;

public class H2JwtMapperTest {
    @Test
    public void testThatRolesAreMappedCorrectly(){
        Jwt jwt = new Jwt(1L, "token", "20230101", List.of(Roles.ADMIN));
        H2JwtMapper h2JwtMapper = new H2JwtMapper();
        JwtH2 jwtH2 = h2JwtMapper.map(jwt);
        System.out.println(jwtH2.getRoles());
    }
}
