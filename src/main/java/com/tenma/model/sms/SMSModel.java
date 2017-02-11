package com.tenma.model.sms;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Anom
 * Date: Mar 29, 2012
 * Time: 7:10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class SMSModel extends AuditModel {
    private Date deliveryDate;
    private String smsFrom;
    private String smsTo;
    private String smsMessage;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    @Override
    public String toString() {
        return "SMSModel{" +
                "deliveryDate=" + deliveryDate +
                ", smsFrom='" + smsFrom + '\'' +
                ", smsTo='" + smsTo + '\'' +
                ", smsMessage='" + smsMessage + '\'' +
                '}';
    }
}
