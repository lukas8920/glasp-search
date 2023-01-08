package org.kehrbusch.mappers;

import org.kehrbusch.entities.Jwt;
import org.kehrbusch.entities.JwtH2;
import org.kehrbusch.entities.Roles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class H2JwtMapper {
    public Jwt map(JwtH2 jwtH2){
        Jwt jwt = new Jwt();
        jwt.setAccessToken(jwtH2.getAccessToken());
        jwt.setExpiryDate(jwtH2.getExpiryDate());
        jwt.setId(jwtH2.getId());
        jwt.setRoles(map(jwtH2.getRoles()));
        return jwt;
    }

    public JwtH2 map(Jwt jwt){
        JwtH2 jwtH2 = new JwtH2();
        jwtH2.setAccessToken(jwt.getAccessToken());
        jwtH2.setExpiryDate(jwt.getExpiryDate());
        jwtH2.setId(jwt.getId());
        jwtH2.setRoles(mapRoles(jwt.getRoles()));
        return jwtH2;
    }

    private List<Roles> map(List<String> roles){
        List<Roles> tmpRoles = new ArrayList<>();
        roles.forEach(role -> {
            switch (role){
                case "CLIENT":
                    tmpRoles.add(Roles.CLIENT);
                case "ADMIN":
                    tmpRoles.add(Roles.ADMIN);
            }
        });
        return tmpRoles;
    }

    private List<String> mapRoles(List<Roles> roles){
        List<String> tmpRoles = new ArrayList<>();
        roles.forEach(role -> {
            switch (role){
                case CLIENT:
                    tmpRoles.add("CLIENT");
                case ADMIN:
                    tmpRoles.add("ADMIN");
            }
        });
        return tmpRoles;
    }
}
