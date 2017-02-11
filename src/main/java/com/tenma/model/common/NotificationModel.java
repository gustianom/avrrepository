/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Jan 17 15:30:16 WIB 2014
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class NotificationModel extends AuditModel {
    private Integer notifyId;
    private String communityId;
    private String notifyFrom;
    private String notifyTo;
    private String notifySubject;
    private Integer notifyType;
    private Integer viewStatus;
    private String notifySourceId;


    public Integer getNotifyId() {
        return this.notifyId;
    }

    public void setNotifyId(Integer notifyId) {
        this.notifyId = notifyId;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getNotifyFrom() {
        return this.notifyFrom;
    }

    public void setNotifyFrom(String notifyFrom) {
        this.notifyFrom = notifyFrom;
    }

    public String getNotifyTo() {
        return this.notifyTo;
    }

    public void setNotifyTo(String notifyTo) {
        this.notifyTo = notifyTo;
    }

    public String getNotifySubject() {
        return this.notifySubject;
    }

    public void setNotifySubject(String notifySubject) {
        this.notifySubject = notifySubject;
    }

    public Integer getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifySourceId() {
        return notifySourceId;
    }

    public void setNotifySourceId(String notifySourceId) {
        this.notifySourceId = notifySourceId;
    }

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }
}