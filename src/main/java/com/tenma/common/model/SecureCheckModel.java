package com.tenma.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gustianom on 2/20/14.
 */
public class SecureCheckModel implements Serializable {
    private String userName;
    private int countFailedEnter;
    private int countAccess;
    private Date lastAccessDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCountFailedEnter() {
        return countFailedEnter;
    }

    public void setCountFailedEnter(int countFailedEnter) {
        this.countFailedEnter = countFailedEnter;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public int getCountAccess() {
        return countAccess;
    }

    public void setCountAccess(int countAccess) {
        this.countAccess = countAccess;
    }

    @Override
    public String toString() {
        return "SecureCheckModel{" +
                "userName='" + userName + '\'' +
                ", countFailedEnter=" + countFailedEnter +
                ", countAccess=" + countAccess +
                ", lastAccessDate=" + lastAccessDate +
                '}';
    }
}
