/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Dec 14 13:17:42 WIT 2013
 */

package com.tenma.sms.model;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class SmsOutboundModel extends AuditModel {
    private Integer outboundId;
    private Integer providerDeliveryId;
    private String communityId;
    private String communityName;

    private String clientId;   //if communityId is tenma, then the clientId may vary according to the usage of SYSTEM sms by another community
    private String clientName;

    private String smsFrom;
    private String smsTo;
    private String smsMessage;

    private Date deliveredDate;
    private Date deliveryDate;
    private Integer deliveryStatus;
    private Integer smsType;
    private Integer smsRecipientType;
    private Date lastGeneratedInvoice;
    private Date generatedInvoiceMaxDate; //max date of generated invoice


    public Integer getOutboundId() {
        return outboundId;
    }

    public void setOutboundId(Integer outboundId) {
        this.outboundId = outboundId;
    }

    public Integer getProviderDeliveryId() {
        return providerDeliveryId;
    }

    public void setProviderDeliveryId(Integer providerDeliveryId) {
        this.providerDeliveryId = providerDeliveryId;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getSmsFrom() {
        return smsFrom;
    }

    public void setSmsFrom(String smsFrom) {
        this.smsFrom = smsFrom;
    }

    public String getSmsTo() {
        return smsTo;
    }

    public void setSmsTo(String smsTo) {
        this.smsTo = smsTo;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Integer getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    /**
     * is SMS recepient Type:
     * </p> 0 for local/national phone number
     * </p> 1 for INTERNATIONAL PHONE NUMBER
     */
    public Integer getSmsRecipientType() {
        return smsRecipientType;
    }

    /**
     * is SMS recepient Type:
     * </p> 0 for local/national phone number
     * </p> 1 for INTERNATIONAL PHONE NUMBER
     */
    public void setSmsRecipientType(Integer smsRecipientType) {
        this.smsRecipientType = smsRecipientType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getLastGeneratedInvoice() {
        return lastGeneratedInvoice;
    }

    public void setLastGeneratedInvoice(Date lastGeneratedInvoice) {
        this.lastGeneratedInvoice = lastGeneratedInvoice;
    }

    public Date getGeneratedInvoiceMaxDate() {
        return generatedInvoiceMaxDate;
    }

    public void setGeneratedInvoiceMaxDate(Date generatedInvoiceMaxDate) {
        this.generatedInvoiceMaxDate = generatedInvoiceMaxDate;
    }
}