package org.kehrbusch.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenResponse {
    @Schema(description = "The token which you can use to call the rest API.",
            example = "4d3s0faf93d40faa8ck")
    private String accessToken;
    @Schema(description = "The time in seconds which remains until the access token becomes invalid (initially 2 days).",
            example = "172800")
    private String expiresIn;

    public TokenResponse(){}

    public TokenResponse(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
