package org.kehrbusch.webservices;

import org.kehrbusch.entities.ApiAccess;

import java.io.IOException;

public interface ApiAccessService {
    public ApiAccess findUserApiAccess(ApiAccess apiAccess) throws IOException;

    public ApiAccess findAdminApiAccess(ApiAccess apiAccess) throws IOException;
}
