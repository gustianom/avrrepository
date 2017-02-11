/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */
package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

public class TransactionTemplateDetailModel extends AuditModel {
    private String communityId;
    private String templateId;
    private String templateDetailId;
    private String accountId;
    private Integer accountType;
    private String customId;
    private Boolean dirty;
    private Boolean delete;
    private String accountName;
    private String templateDetailDesc;
    private String templateDetailCurr;
    private Integer templateDetailBalance;
    private Integer orderNumber;


    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateDetailId() {
        return templateDetailId;
    }

    public void setTemplateDetailId(String templateDetailId) {
        this.templateDetailId = templateDetailId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTemplateDetailDesc() {
        return templateDetailDesc;
    }

    public void setTemplateDetailDesc(String templateDetailDesc) {
        this.templateDetailDesc = templateDetailDesc;
    }

    public String getTemplateDetailCurr() {
        return templateDetailCurr;
    }

    public void setTemplateDetailCurr(String templateDetailCurr) {
        this.templateDetailCurr = templateDetailCurr;
    }

    public Integer getTemplateDetailBalance() {
        return templateDetailBalance;
    }

    public void setTemplateDetailBalance(Integer templateDetailBalance) {
        this.templateDetailBalance = templateDetailBalance;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
