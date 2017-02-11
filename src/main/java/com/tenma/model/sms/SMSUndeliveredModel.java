/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Aug 13 11:01:54 WIB 2015
 */

package com.tenma.model.sms;

import java.util.Date;

public class SMSUndeliveredModel extends SMSModel {
    private Integer undeliverId;
    private String communityId;
    private Date smsDeliveredDate;
    private Date smsDeliveryDate;
    private Integer smsType;
    private String clientCommunityId;

    public Integer getUndeliverId() {
        return this.undeliverId;
    }

    public void setUndeliverId(Integer undeliverId) {
        this.undeliverId = undeliverId;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Date getSmsDeliveredDate() {
        return this.smsDeliveredDate;
    }

    public void setSmsDeliveredDate(Date smsDeliveredDate) {
        this.smsDeliveredDate = smsDeliveredDate;
    }

    public Date getSmsDeliveryDate() {
        return this.smsDeliveryDate;
    }

    public void setSmsDeliveryDate(Date smsDeliveryDate) {
        this.smsDeliveryDate = smsDeliveryDate;
    }

    public Integer getSmsType() {
        return this.smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getClientCommunityId() {
        return this.clientCommunityId;
    }

    public void setClientCommunityId(String clientCommunityId) {
        this.clientCommunityId = clientCommunityId;
    }

}