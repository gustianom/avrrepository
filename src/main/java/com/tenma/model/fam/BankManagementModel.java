/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Mon Sep 08 10:13:58 WIB 2014
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

public class BankManagementModel extends AuditModel {
    private String communityId;
    private String bankId;
    private String bankName;
    private String cabang;
    private String acroname;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCabang() {
        return this.cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getAcroname() {
        return acroname;
    }

    public void setAcroname(String acroname) {
        this.acroname = acroname;
    }
}