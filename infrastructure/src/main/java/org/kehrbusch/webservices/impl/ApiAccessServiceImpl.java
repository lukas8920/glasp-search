package org.kehrbusch.webservices.impl;

import org.kehrbusch.entities.AccessApi;
import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.mappers.AccessApiMapper;
import org.kehrbusch.webservices.ApiAccessService;
import org.kehrbusch.webservices.services.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class ApiAccessServiceImpl implements ApiAccessService {
    private static final Logger logger = Logger.getLogger(ApiAccessServiceImpl.class.getName());

    private final AuthorizeService authorizeService;
    private final AccessApiMapper accessApiMapper;

    @Autowired
    public ApiAccessServiceImpl(AuthorizationGenerator authorizationGenerator, AccessApiMapper accessApiMapper){
        this.authorizeService = authorizationGenerator.generateService();
        this.accessApiMapper = accessApiMapper;
    }

    @Override
    public ApiAccess findUserApiAccess(ApiAccess apiAccess) throws IOException {
        AccessApi accessApi = this.authorizeService
                .authorizeUser(this.accessApiMapper.map(apiAccess)).execute().body();
        return accessApi != null ? this.accessApiMapper.map(accessApi) : null;
    }

    @Override
    public ApiAccess findAdminApiAccess(ApiAccess apiAccess) throws IOException {
        AccessApi accessApi = this.authorizeService
                .authorizeAdmin(this.accessApiMapper.map(apiAccess)).execute().body();
        return accessApi != null ? this.accessApiMapper.map(accessApi) : null;
    }
}
