package org.kehrbusch.services;

import org.kehrbusch.JwtTokenProvider;
import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.entities.Jwt;
import org.kehrbusch.entities.Roles;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.repositories.JwtRepository;
import org.kehrbusch.webservices.ApiAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AdminService {
    private final ApiAccessService apiAccessService;
    private final JwtRepository jwtRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Publisher publisher;

    @Autowired
    public AdminService(ApiAccessService apiAccessService, JwtRepository jwtRepository,
                        JwtTokenProvider jwtTokenProvider, Publisher publisher){
        this.apiAccessService = apiAccessService;
        this.jwtRepository = jwtRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.publisher = publisher;
    }

    public Jwt login(String user, String password) throws BadRequestException, IOException {
        if (user == null || password == null){
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("Internal Server Error");
        }

        ApiAccess apiAccess = apiAccessService.findAdminApiAccess(new ApiAccess(0L, user, password));
        if (apiAccess == null) {
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("Internal Server Error");
        }

        this.publisher.publishAuthorizationSuccess();
        Jwt jwt = this.jwtRepository.findJwtById(apiAccess.getId());
        if (jwt == null) {
            Jwt newJwt = jwtTokenProvider.createToken(apiAccess.getId(), List.of(Roles.ADMIN, Roles.CLIENT));
            this.jwtRepository.save(newJwt);
            return newJwt;
        } else if (jwt.hasExpired()){
            Jwt newJwt = jwtTokenProvider.createToken(apiAccess.getId(), List.of(Roles.ADMIN, Roles.CLIENT));
            this.jwtRepository.updateJwt(newJwt);
            return newJwt;
        } else {
            return jwt;
        }
    }
}
