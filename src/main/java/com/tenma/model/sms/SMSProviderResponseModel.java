package com.tenma.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 12/16/13
 * Time: 1:35 PM
 */
public class SMSProviderResponseModel implements Serializable {
    private Integer messageId;
    private String to;
    private String from;
    private Integer status;
    private String statusMessage;

    private Date messageDate;

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
