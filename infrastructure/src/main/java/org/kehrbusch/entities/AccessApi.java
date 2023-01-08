package org.kehrbusch.entities;

public class AccessApi {
    private Long id;
    private String userKey;
    private String userPassword;

    public AccessApi(){}

    public AccessApi(Long id, String userKey, String userPassword) {
        this.id = id;
        this.userKey = userKey;
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
