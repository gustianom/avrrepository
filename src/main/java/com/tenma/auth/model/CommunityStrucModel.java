/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */
package com.tenma.auth.model;


/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 1/13/13
 * Time: 2:10 PM
 */
public class CommunityStrucModel extends ScreenModel {
    private String structureId;
    private String structureName;
    private String structureDesc;
    private String structureType;
    private String structureCategory;
    private Integer recordStatus;

    public String getStructureCategory() {
        return structureCategory;
    }

    public void setStructureCategory(String structureCategory) {
        this.structureCategory = structureCategory;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getStructureDesc() {
        return structureDesc;
    }

    public void setStructureDesc(String structureDesc) {
        this.structureDesc = structureDesc;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }
}
