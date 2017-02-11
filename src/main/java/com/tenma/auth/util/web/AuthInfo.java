package com.tenma.auth.util.web;


import com.tenma.auth.model.AuthModel;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthInfo implements Serializable, User {
    private String ipAddress;
    private HashMap<String, Boolean> functionMap = new HashMap();
    private String userStatus;
    private String ticketId;

    private String password;

    private AuthModel clientAuthUserModel = new AuthModel();

    private String timeZone;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public HashMap<String, Boolean> getFunctionMap() {
        return functionMap;
    }

    public void setFunctionMap(HashMap<String, Boolean> functionMap) {
        this.functionMap = functionMap;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Override toString
     *
     * @return {@link String}
     */
    public String toString() {
        return "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthModel getClientAuthUserModel() {
        return clientAuthUserModel;
    }

    public void setClientAuthUserModel(AuthModel clientAuthUserModel) {
        this.clientAuthUserModel = clientAuthUserModel;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}

