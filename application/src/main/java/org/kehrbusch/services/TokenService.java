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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TokenService {
    private static final Logger logger = Logger.getLogger(TokenService.class.getName());

    private final JwtRepository jwtRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApiAccessService apiAccessService;
    private final Publisher publisher;

    @Autowired
    public TokenService(ApiAccessService apiAccessService, JwtRepository jwtRepository, JwtTokenProvider jwtTokenProvider, Publisher publisher){
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtRepository = jwtRepository;
        this.apiAccessService = apiAccessService;
        this.publisher = publisher;
    }

    public Jwt getJwt(String username, String userPassword) throws BadRequestException {
        if (username == null || userPassword == null){
            this.publisher.publishAuthorizationFailure();
            throw new BadRequestException("Internal Server Error");
        }

        ApiAccess apiAccess;
        try {
            apiAccess = apiAccessService.findUserApiAccess(new ApiAccess(0L, username, userPassword));
            if (apiAccess == null) {
                this.publisher.publishAuthorizationFailure();
                logger.info("API Access is null.");
                throw new BadRequestException("Internal Server Error");
            }
        } catch (IOException ioException){
            this.publisher.publishAuthorizationFailure();
            logger.info("Connection to Glasp Server failed.");
            ioException.printStackTrace();
            throw new BadRequestException("Internal Server Error");
        }

        this.publisher.publishAuthorizationSuccess();
        Jwt jwt = this.jwtRepository.findJwtById(apiAccess.getId());
        if (jwt == null) {
            Jwt newJwt = jwtTokenProvider.createToken(apiAccess.getId(), List.of(Roles.CLIENT));
            this.jwtRepository.save(newJwt);
            return newJwt;
        } else if (jwt.hasExpired()){
            Jwt newJwt = jwtTokenProvider.createToken(apiAccess.getId(), List.of(Roles.CLIENT));
            this.jwtRepository.updateJwt(newJwt);
            return newJwt;
        } else {
            return jwt;
        }
    }

    public void revoke() throws BadRequestException {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (id == null) throw new BadRequestException("Internal Server Error");
        Jwt newJwt = jwtTokenProvider.createToken(id, List.of(Roles.CLIENT));
        this.jwtRepository.updateJwt(newJwt);
    }
}
