package org.kehrbusch.entities;

public class RevokeRequest {
    private String accessToken;

    public RevokeRequest(){}

    public RevokeRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
