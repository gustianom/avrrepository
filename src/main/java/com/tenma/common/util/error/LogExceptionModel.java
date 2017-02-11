package com.tenma.common.util.error;

import java.time.LocalDateTime;

/**
 * Created by ndwijaya on 8/18/15.
 */
public class LogExceptionModel {
    private int severity;
    private int errorCode;
    private LocalDateTime exceptionDate;
    private String exceptionTrace;
    private String community;
    private String userId;
    private String module;
    private String ip;

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(LocalDateTime exceptionDate) {
        this.exceptionDate = exceptionDate;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
