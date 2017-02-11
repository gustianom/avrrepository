/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

public class TenmaCustomFieldsModel {
    private Integer fieldOrder;
    private String fieldName;
    private String fieldLabel;
    private Integer fieldType;
    private String fieldCustom;
    private Integer fieldWidth;
    private Integer fieldHeight;
    private String initValue;
    private Integer fieldGroup;
    private Integer fieldRow;
    private Boolean initEnabled;
    private Boolean isMandatory;

    public Integer getFieldOrder() {
        return this.fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return this.fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public Integer getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldCustom() {
        return this.fieldCustom;
    }

    public void setFieldCustom(String fieldCustom) {
        this.fieldCustom = fieldCustom;
    }

    public Integer getFieldWidth() {
        return this.fieldWidth;
    }

    public void setFieldWidth(Integer fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public Integer getFieldHeight() {
        return this.fieldHeight;
    }

    public void setFieldHeight(Integer fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public String getInitValue() {
        return this.initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public Integer getFieldGroup() {
        return this.fieldGroup;
    }

    public void setFieldGroup(Integer fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public Integer getFieldRow() {
        return this.fieldRow;
    }

    public void setFieldRow(Integer fieldRow) {
        this.fieldRow = fieldRow;
    }

    public Boolean getInitEnabled() {
        return this.initEnabled;
    }

    public void setInitEnabled(Boolean initEnabled) {
        this.initEnabled = initEnabled;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }
}