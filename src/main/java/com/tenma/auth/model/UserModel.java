package com.tenma.auth.model;

import java.util.Date;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:55 PM
 */
public class UserModel extends AuthModel {
    private Date startDate, endDate, lastUpdatedPassword;
    private Integer counter;
    private String confirmpassword, defCommunityId;

    private Integer userStatus; // account status (locked (1), un-locked (0))
    private Integer userRegistrationStatus;
    private Integer totPost;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLastUpdatedPassword() {
        return lastUpdatedPassword;
    }

    public void setLastUpdatedPassword(Date lastUpdatedPassword) {
        this.lastUpdatedPassword = lastUpdatedPassword;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getDefCommunityId() {
        return defCommunityId;
    }

    public void setDefCommunityId(String defCommunityId) {
        this.defCommunityId = defCommunityId;
    }

    public Integer getUserRegistrationStatus() {
        return userRegistrationStatus;
    }

    public void setUserRegistrationStatus(Integer userRegistrationStatus) {
        this.userRegistrationStatus = userRegistrationStatus;
    }

    /**
     * Account Status (locked -1, unlocked (0)
     *
     * @return
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * Account Status (locked 1, unlocked (0)
     *
     * @param userStatus
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getTotPost() {
        return totPost;
    }

    public void setTotPost(Integer totPost) {
        this.totPost = totPost;
    }

}
