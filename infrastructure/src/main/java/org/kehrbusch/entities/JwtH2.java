package org.kehrbusch.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class JwtH2 {
    @Id
    private Long id;
    private String accessToken;
    private String expiryDate;
    @ElementCollection
    @CollectionTable(name="RolesH2", joinColumns=@JoinColumn(name="jwt_id"))
    @Column(name="role")
    private List<String> roles;

    public JwtH2(){}

    public JwtH2(Long id, String accessToken, String expiryDate, List<String> roles){
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

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
}
