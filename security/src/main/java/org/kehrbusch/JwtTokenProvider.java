package org.kehrbusch;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.kehrbusch.entities.Jwt;
import org.kehrbusch.util.InvalidationException;
import org.kehrbusch.entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 60000;

    private final UserDetailsResponse userDetailsResponse;

    private Date expiryDate;

    @Autowired
    public JwtTokenProvider(UserDetailsResponse userDetailsResponse){
        this.userDetailsResponse = userDetailsResponse;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Jwt createToken(Long id, List<Roles> roles){
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put("auth", new SimpleGrantedAuthority(Roles.CLIENT.toString()));

        Date date = new Date();
        expiryDate = new Date(System.currentTimeMillis() + validityInMilliseconds);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return new Jwt(id, token, String.valueOf(expiryDate.getTime()), roles);
    }

    public Authentication getAuthentication(String token) throws InvalidationException {
        UserDetails userDetails;
        try {
            String profileId = validateToken(token);
            userDetails = userDetailsResponse.loadUserByUsername(profileId);
        } catch (UsernameNotFoundException e){
            throw new InvalidationException("Expired or invalid Jwt Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String validateToken(String token) throws InvalidationException{
        try {
            //If validated return id in token
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e){
            throw new InvalidationException("Expired or invalid Jwt Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
