/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.model;

import com.tenma.auth.model.AuditModel;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 3:53 PM
 */
public class SystemPropertyModel extends AuditModel {
    private String systemPropertyName, systemPropertyValue, systemPropertyDescription;
    private int recordStatus;
    private String communityId;

    public String getSystemPropertyName() {
        return systemPropertyName;
    }

    public void setSystemPropertyName(String systemPropertyName) {
        this.systemPropertyName = systemPropertyName;
    }

    public String getSystemPropertyValue() {
        return systemPropertyValue;
    }

    public void setSystemPropertyValue(String systemPropertyValue) {
        this.systemPropertyValue = systemPropertyValue;
    }

    public String getSystemPropertyDescription() {
        return systemPropertyDescription;
    }

    public void setSystemPropertyDescription(String systemPropertyDescription) {
        this.systemPropertyDescription = systemPropertyDescription;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
