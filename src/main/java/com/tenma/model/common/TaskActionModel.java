/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Tue Dec 03 12:10:42 WIT 2013
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class TaskActionModel extends AuditModel {
    private Integer actionId;
    private String actionName;
    private String actionDesc;
    private String actionClass;

    public Integer getActionId() {
        return this.actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDesc() {
        return this.actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getActionClass() {
        return this.actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

}