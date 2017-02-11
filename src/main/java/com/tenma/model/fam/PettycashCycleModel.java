/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generated on Tue Jun 18 11:48:02 ICT 2013
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class PettycashCycleModel extends AuditModel {
    private String communityId;
    private Date cashOpen;
    private Date cashClose;
    private String userId;
    private Boolean status;
    private String currency;

    public String getCommunityId() {
        return this.communityId;

    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Date getCashOpen() {
        return this.cashOpen;
    }

    public void setCashOpen(Date cashOpen) {
        this.cashOpen = cashOpen;
    }

    public Date getCashClose() {
        return this.cashClose;
    }

    public void setCashClose(Date cashClose) {
        this.cashClose = cashClose;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}