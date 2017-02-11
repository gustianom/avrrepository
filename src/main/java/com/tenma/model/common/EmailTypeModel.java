package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 5/27/13
 * Time: 9:34 AM
 */
public class EmailTypeModel extends AuditModel {
    private String emailId;
    private String emailType;
    private String emailDetail;
    private String emailSubject;
    private String communityStructure;


    public String getCommunityStructure() {
        return communityStructure;
    }

    public void setCommunityStructure(String communityStructure) {
        this.communityStructure = communityStructure;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailDetail() {
        return emailDetail;
    }

    public void setEmailDetail(String emailDetail) {
        this.emailDetail = emailDetail;
    }
}

