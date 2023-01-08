package org.kehrbusch.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenRequest {
    @Schema(description = "Your specific user name which you can see in your account at www.glasp.eu.",
        example = "54321-54321-54321-54321-54321")
    private String userName;
    @Schema(description = "Your specific user password which you can see in your account at www.glasp.eu.",
            example = "pafd342k1afd3429")
    private String userPassword;

    public TokenRequest() {}

    public TokenRequest(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
