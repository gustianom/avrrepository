/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Mar 05 10:41:17 WIB 2015
 */

package com.tenma.model.email;

public class UndeliveredMailModel extends EmailModel {
    private String communityId;
    private Integer emailId;
    private byte[] bynaryFileAttached;

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public byte[] getBynaryFileAttached() {
        return bynaryFileAttached;
    }

    public void setBynaryFileAttached(byte[] bynaryFileAttached) {
        this.bynaryFileAttached = bynaryFileAttached;
    }
}