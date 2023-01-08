package org.kehrbusch.entities;

import java.util.Date;
import java.util.List;

public class Jwt {
    private Long id;
    private String accessToken;
    private String expiryDate;
    private List<Roles> roles;

    public Jwt(){}

    public Jwt(Long id, String accessToken, String expiryDate, List<Roles> roles) {
        this.id = id;
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean hasExpired(){
        Date date = new Date();
        return date.getTime() >= Long.parseLong(expiryDate);
    }

    public List<Roles> getRoles(){
        return this.roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
