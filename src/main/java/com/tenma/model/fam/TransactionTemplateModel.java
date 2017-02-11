/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.List;

public class TransactionTemplateModel extends AuditModel {
    private String templateId;
    private String templateName;
    private String templateDesc;
    private String currency;
    private String communityId;
    private String tenmaCustomId;
    private String tenmaCustomName;
    private Integer autoOnly;
    private Integer type;
    private String image;
    private Integer departmentId;
    private List<TransactionTemplateDetailModel> detail;
    private String customField;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getTenmaCustomId() {
        return tenmaCustomId;
    }

    public void setTenmaCustomId(String tenmaCustomId) {
        this.tenmaCustomId = tenmaCustomId;
    }

    public String getTenmaCustomName() {
        return tenmaCustomName;
    }

    public void setTenmaCustomName(String tenmaCustomName) {
        this.tenmaCustomName = tenmaCustomName;
    }

    public List<TransactionTemplateDetailModel> getDetail() {
        return detail;
    }

    public void setDetail(List<TransactionTemplateDetailModel> detail) {
        this.detail = detail;
    }

    public Integer getAutoOnly() {
        return autoOnly;
    }

    public void setAutoOnly(Integer autoOnly) {
        this.autoOnly = autoOnly;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }
}
