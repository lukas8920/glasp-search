package org.kehrbusch.webservices.impl;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.kehrbusch.webservices.services.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class AuthorizationGenerator {
    private static final Logger logger = Logger.getLogger(AuthorizationGenerator.class.getName());

    @Autowired
    @Qualifier("properties")
    private Properties properties;

    public AuthorizeService generateService(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        "Bearer " + properties.getProperty("access.token"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(properties.getProperty("webservice.api.access.path"))
                .client(okHttpClient)
                .build();
        return retrofit.create(AuthorizeService.class);
    }
}

