/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Thu May 08 12:14:04 ICT 2014
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class SmsTypeModel extends AuditModel {
    private String communityStructure;
    private Integer smsId;
    private String smsType;
    private String smsDesc;

    public String getCommunityStructure() {
        return this.communityStructure;
    }

    public void setCommunityStructure(String communityStructure) {
        this.communityStructure = communityStructure;
    }

    public Integer getSmsId() {
        return this.smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    public String getSmsType() {
        return this.smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsDesc() {
        return this.smsDesc;
    }

    public void setSmsDesc(String smsDesc) {
        this.smsDesc = smsDesc;
    }

}