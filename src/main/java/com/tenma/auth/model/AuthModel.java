package com.tenma.auth.model;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthModel extends AuditModel implements java.io.Serializable {

    private String userId;
    private Integer memberId;
    private Integer userLevelType;
    private String emailAddress;
    private String mobilePhone;
    private String userFullName;
    private String idForLogin;
    private Integer recordStatus;
    private String password;

    private Integer screenId;

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getIdForLogin() {
        return idForLogin;
    }

    public void setIdForLogin(String idForLogin) {
        this.idForLogin = idForLogin;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserLevelType() {
        return userLevelType;
    }

    public void setUserLevelType(Integer userLevelType) {
        this.userLevelType = userLevelType;
    }
}
