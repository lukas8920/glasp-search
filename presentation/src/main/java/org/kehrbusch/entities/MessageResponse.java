package org.kehrbusch.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public class MessageResponse {
    @Schema(description = "Confirmation that revoking the access token worked.",
            example = "The access token was revoked.")
    private String message;

    public MessageResponse(){}

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
