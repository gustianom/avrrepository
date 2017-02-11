/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * User: ndwijaya
 * Date: 08/10/11
 * Time: 17:17
 */
public class AuditTrailModel implements Serializable {
    private String auditId;
    private int crudType;
    private Integer processId;
    private String userId;
    private Date logDate;
    private String logBy;
    private String logFrom;
    private String desc;
    private Integer moduleId;
    private int processTime;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public int getCrudType() {
        return crudType;
    }

    public void setCrudType(int crudType) {
        this.crudType = crudType;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogBy() {
        return logBy;
    }

    public void setLogBy(String logBy) {
        this.logBy = logBy;
    }

    public String getLogFrom() {
        return logFrom;
    }

    public void setLogFrom(String logFrom) {
        this.logFrom = logFrom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
