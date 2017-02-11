/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jul 24 15:22:16 ICT 2013
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class VerificationCodeBankModel extends AuditModel {
    private String userId;
    private String codeid;
    private Date dateValid;
    private String verificationMedia;  // 101 -> Phone, 102 -> Email

    public String getVerificationMedia() {
        return verificationMedia;
    }

    public void setVerificationMedia(String verificationMedia) {
        this.verificationMedia = verificationMedia;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCodeid() {
        return this.codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public Date getDateValid() {
        return this.dateValid;
    }

    public void setDateValid(Date dateValid) {
        this.dateValid = dateValid;
    }

}