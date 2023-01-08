package org.kehrbusch.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.entities.Jwt;
import org.kehrbusch.entities.TokenRequest;
import org.kehrbusch.entities.TokenResponse;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.services.AdminService;
import org.kehrbusch.services.InflateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Tag(name = "data")
@RestController
@RequestMapping(path = "/data")
public class DatabaseController {
    private static final Logger logger = Logger.getLogger(DatabaseController.class.getName());

    private final InflateService inflateService;
    private final AdminService adminService;

    @Autowired
    public DatabaseController(InflateService inflateService, AdminService adminService) {
        this.inflateService = inflateService;
        this.adminService = adminService;
    }

    @PostMapping(value = "/login")
    public TokenResponse admin(@RequestBody TokenRequest tokenRequest) throws BadRequestException, IOException {
        Jwt jwt = this.adminService.login(tokenRequest.getUserName(), tokenRequest.getUserPassword());
        long seconds = (new Date(Long.parseLong(jwt.getExpiryDate())).getTime() - new Date().getTime()) / 1000;
        return new TokenResponse(jwt.getAccessToken(), String.valueOf(seconds));
    }

    @PostMapping(value = "/init/zipstreet")
    public Void triggerZipStreetInit() throws InternalServerError {
        this.inflateService.initZipStreet();
        return null;
    }

    @PostMapping(value = "/init/city")
    public Void triggerCityInit() throws InternalServerError {
        this.inflateService.initCity();
        return null;
    }
}
