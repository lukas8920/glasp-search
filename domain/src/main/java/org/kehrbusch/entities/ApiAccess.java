package org.kehrbusch.entities;

public class ApiAccess {
    private Long id;
    private String userName;
    private String userPassword;

    public ApiAccess(){}

    public ApiAccess(Long id, String userName, String userPassword) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString(){
        return id + "," + userName + "," + userPassword;
    }
}
