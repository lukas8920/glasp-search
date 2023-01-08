package org.kehrbusch.controllers.redis;

import org.kehrbusch.entities.*;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.mapper.CityMapper;
import org.kehrbusch.mapper.ZipStreetMapper;
import org.kehrbusch.services.AdminService;
import org.kehrbusch.services.redis.DbInflateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
@Tag(name = "data")
@RestController
@RequestMapping(path = "/data")*/
@Deprecated
public class RedisDatabaseController {
    private static final Logger logger = Logger.getLogger(RedisDatabaseController.class.getName());

    private final DbInflateService dbInflateService;
    private final ZipStreetMapper zipStreetMapper;
    private final AdminService adminService;
    private final CityMapper cityMapper;

    @Autowired
    public RedisDatabaseController(DbInflateService dbInflateService, ZipStreetMapper zipStreetMapper,
                                   AdminService adminService, CityMapper cityMapper){
        this.zipStreetMapper = zipStreetMapper;
        this.dbInflateService = dbInflateService;
        this.adminService = adminService;
        this.cityMapper = cityMapper;
    }

    @PostMapping(value = "/login")
    public TokenResponse admin(@RequestBody TokenRequest tokenRequest) throws BadRequestException, IOException {
        Jwt jwt = this.adminService.login(tokenRequest.getUserName(), tokenRequest.getUserPassword());
        return new TokenResponse(jwt.getAccessToken(), jwt.getExpiryDate());
    }

    @PostMapping(value = "/add/zipstreet")
    public Void insert(@RequestBody ZipStreetInsert zipStreetInsert) throws InternalServerError {
        logger.info("Received list with zip street data");
        dbInflateService.add(zipStreetInsert.getZipStreetApis()
                .stream()
                .map(zipStreetMapper::map)
                .collect(Collectors.toList()));
        logger.info("Finished loading list with zip street data");
        return null;
    }

    @PostMapping(value = "/init/zipstreet")
    public Void triggerZipStreetInit() throws InternalServerError {
        dbInflateService.initZipStreet();
        return null;
    }

    @PostMapping(value = "/init/city")
    public Void triggerCityInit() throws InternalServerError {
        this.dbInflateService.initCity();
        return null;
    }
}
