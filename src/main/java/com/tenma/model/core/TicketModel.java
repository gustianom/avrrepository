package com.tenma.model.core;

/**
 * Created by rizt.
 * Date    : 19-09-2015
 * Time    : 13:37
 * Copyright (c) 2015 Tenma Bright Sky
 */
public class TicketModel {
    private String ticketId;
    private String communityId;
    private Integer memberId;
    private Integer validTime;
    private Integer errorCode;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
