package org.kehrbusch.webservices.services;

import org.kehrbusch.entities.AccessApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizeService {
    @POST("/web/app/authorize/user")
    public Call<AccessApi> authorizeUser(@Body AccessApi accessApi);

    @POST("/web/app/authorize/admin")
    public Call<AccessApi> authorizeAdmin(@Body AccessApi accessApi);
}
