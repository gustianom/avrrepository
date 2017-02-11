package com.tenma.auth.model;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthCredential extends AuditModel {

    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
