/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/21/14
 * Time: 4:14 PM
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.Date;


public class AccountModel extends AuditModel {
    private String communityId;
    private String accountId;
    private String accountDesc;
    private String accountName;
    private Integer accountType;
    private Integer accountNormal;
    private boolean accountFixedCurrency;
    private String accountCurrency;
    private Date accountValidFrom;
    private Date accountValidTo;
    private Integer accountStatus;
    private Integer revaluation;
    private String accountGroupId;
    private String accountGroupName;
    private Integer tenmaType;
    private String accountParent;
    private Integer status;

    private String accountPackageId;
    private String accountPackageName;
    private String parentAccountPackageId;
    private String accountUserCustom;
    private String masterAccountKey;

    private String bankAccount;
    private Integer balanceSheet;

    public Integer getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(Integer balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountNormal() {
        return accountNormal;
    }

    public void setAccountNormal(Integer accountNormal) {
        this.accountNormal = accountNormal;
    }

    public boolean isAccountFixedCurrency() {
        return accountFixedCurrency;
    }

    public void setAccountFixedCurrency(boolean accountFixedCurrency) {
        this.accountFixedCurrency = accountFixedCurrency;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public Date getAccountValidFrom() {
        return accountValidFrom;
    }

    public void setAccountValidFrom(Date accountValidFrom) {
        this.accountValidFrom = accountValidFrom;
    }

    public Date getAccountValidTo() {
        return accountValidTo;
    }

    public void setAccountValidTo(Date accountValidTo) {
        this.accountValidTo = accountValidTo;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getRevaluation() {
        return revaluation;
    }

    public void setRevaluation(Integer revaluation) {
        this.revaluation = revaluation;
    }


    public String getAccountGroupId() {
        return accountGroupId;
    }

    public void setAccountGroupId(String accountGroupId) {
        this.accountGroupId = accountGroupId;
    }

    public Integer getTenmaType() {
        return tenmaType;
    }

    public void setTenmaType(Integer tenmaType) {
        this.tenmaType = tenmaType;
    }

    public String getAccountParent() {
        return accountParent;
    }

    public void setAccountParent(String accountParent) {
        this.accountParent = accountParent;
    }

    public String getAccountGroupName() {
        return accountGroupName;
    }

    public void setAccountGroupName(String accountGroupName) {
        this.accountGroupName = accountGroupName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccountPackageId() {
        return accountPackageId;
    }

    public void setAccountPackageId(String accountPackageId) {
        this.accountPackageId = accountPackageId;
    }

    public String getAccountPackageName() {
        return accountPackageName;
    }

    public void setAccountPackageName(String accountPackageName) {
        this.accountPackageName = accountPackageName;
    }

    public String getParentAccountPackageId() {
        return parentAccountPackageId;
    }

    public void setParentAccountPackageId(String parentAccountPackageId) {
        this.parentAccountPackageId = parentAccountPackageId;
    }

    public String getAccountUserCustom() {
        return accountUserCustom;
    }

    public void setAccountUserCustom(String accountUserCustom) {
        this.accountUserCustom = accountUserCustom;
    }

    public String getMasterAccountKey() {
        return masterAccountKey;
    }

    public void setMasterAccountKey(String masterAccountKey) {
        this.masterAccountKey = masterAccountKey;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountDescription='" + accountDesc + '\'' +
                '}';
    }
}
