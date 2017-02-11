/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Apr 02 13:07:09 ICT 2015
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class EflyerTrackModel extends AuditModel {
    private String communityId;
    private Integer sendId;
    private Integer trackCounter;
    private Date trackDate;

    public Integer getTrackCounter() {
        return trackCounter;
    }

    public void setTrackCounter(Integer trackCounter) {
        this.trackCounter = trackCounter;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getSendId() {
        return this.sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Date getTrackDate() {
        return this.trackDate;
    }

    public void setTrackDate(Date trackDate) {
        this.trackDate = trackDate;
    }

}