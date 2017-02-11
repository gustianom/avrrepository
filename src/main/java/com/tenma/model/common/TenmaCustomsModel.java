/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class TenmaCustomsModel extends AuditModel {
    private String moduleItemId;
    private String moduleItemName;
    private Boolean locked;
    private String customs;

    public String getModuleItemId() {
        return this.moduleItemId;
    }

    public void setModuleItemId(String moduleItemId) {
        this.moduleItemId = moduleItemId;
    }

    public String getModuleItemName() {
        return moduleItemName;
    }

    public void setModuleItemName(String moduleItemName) {
        this.moduleItemName = moduleItemName;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCustoms() {
        return this.customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

}