/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Tue Apr 22 16:23:58 ICT 2014
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

import java.util.Date;

public class TicketSupportModel extends AuditModel {
    private String communityId;
    private String communityName;
    private Integer ticketId;
    private String ticketSubject;
    private String ticketProblem;
    private String fileReference;
    private Integer ticketStatus;
    private Integer pinnedResponseId;
    private Date closedDate;
    private String creatorName;

    private boolean isCommunityShows;


    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isCommunityShows() {
        return isCommunityShows;
    }

    public void setCommunityShows(boolean communityShows) {
        isCommunityShows = communityShows;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getPinnedResponseId() {
        return pinnedResponseId;
    }

    public void setPinnedResponseId(Integer pinnedResponseId) {
        this.pinnedResponseId = pinnedResponseId;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketSubject() {
        return this.ticketSubject;
    }

    public void setTicketSubject(String ticketSubject) {
        this.ticketSubject = ticketSubject;
    }

    public String getTicketProblem() {
        return this.ticketProblem;
    }

    public void setTicketProblem(String ticketProblem) {
        this.ticketProblem = ticketProblem;
    }

    public String getFileReference() {
        return this.fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public Integer getTicketStatus() {
        return this.ticketStatus;
    }

    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

}