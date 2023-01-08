package org.kehrbusch.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.kehrbusch.entities.*;
import org.kehrbusch.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Tag(name = "Access Token")
@RestController
@RequestMapping(path = "/auth")
public class TokenController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Operation(summary = "Requesting the webservice access token with the user credentials.", operationId = "accessToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The token which shall be used for the searching requests.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    @PostMapping("/token/access")
    public TokenResponse accessToken(@Parameter(description = "Users can sign up at www.glasp.eu to receive access credentials.") @RequestBody TokenRequest tokenRequest) throws BadRequestException, IOException {
        Jwt jwt = this.tokenService.getJwt(tokenRequest.getUserName(), tokenRequest.getUserPassword());
        long seconds = (new Date(Long.parseLong(jwt.getExpiryDate())).getTime() - new Date().getTime()) / 1000;
        return new TokenResponse(jwt.getAccessToken(), String.valueOf(seconds));
    }

    @Operation(summary = "Requesting the revocation of the webservice access token.", operationId = "revokeToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The confirmation that the token was revoked.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/token/revoke")
    public MessageResponse revoke() throws BadRequestException{
        this.tokenService.revoke();
        return new MessageResponse("The Access Token was revoked.");
    }
}
