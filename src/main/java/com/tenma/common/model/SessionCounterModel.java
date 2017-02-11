package com.tenma.common.model;

import com.tenma.auth.model.AuditModel;

/**
 * Created by gustianom on 07/02/15.
 */
public class SessionCounterModel extends AuditModel {
    private String temporaryFileId;
    private String sessionId;
    private String sessionPath;
    private String sessionType;
    private Integer recordStatus;

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getTemporaryFileId() {
        return temporaryFileId;
    }

    public void setTemporaryFileId(String temporaryFileId) {
        this.temporaryFileId = temporaryFileId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionPath() {
        return sessionPath;
    }

    public void setSessionPath(String sessionPath) {
        this.sessionPath = sessionPath;
    }
}

