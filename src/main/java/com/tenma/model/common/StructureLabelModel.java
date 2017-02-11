/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class StructureLabelModel extends AuditModel {
    private String structId;
    private String labelId;
    private String labelValue;

    public String getStructId() {
        return this.structId;
    }

    public void setStructId(String structId) {
        this.structId = structId;
    }

    public String getLabelId() {
        return this.labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelValue() {
        return this.labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

}