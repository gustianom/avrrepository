package com.tenma.auth.model;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Apr 25 16:21:32 WIB 2014
 */

public class MemberModel extends AuditModel {
    private Integer memberId;
    private String memberName;
    private String mobilePhone;
    private String emailAddress;
    private String profilePicture;
    private String memberAbb;

    private boolean mobileStatusActive;
    private boolean emailStatusActive;
    private String topicArn;
    private String deviceId;
    private String deviceArn;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


    public String getMemberAbb() {
        return memberAbb;
    }

    public void setMemberAbb(String memberAbb) {
        this.memberAbb = memberAbb;
    }

    public boolean isMobileStatusActive() {
        return mobileStatusActive;
    }

    public void setMobileStatusActive(boolean mobileStatusActive) {
        this.mobileStatusActive = mobileStatusActive;
    }

    public boolean isEmailStatusActive() {
        return emailStatusActive;
    }

    public void setEmailStatusActive(boolean emailStatusActive) {
        this.emailStatusActive = emailStatusActive;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceArn() {
        return deviceArn;
    }

    public void setDeviceArn(String deviceArn) {
        this.deviceArn = deviceArn;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }


    @Override
    public String toString() {
        return "MemberModel{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", memberAbb='" + memberAbb + '\'' +
                ", mobileStatusActive=" + mobileStatusActive +
                ", emailStatusActive=" + emailStatusActive +
                ", topicArn='" + topicArn + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceArn='" + deviceArn + '\'' +
                '}';
    }
}