/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BankAccountModel extends AuditModel {
    @NotNull
    private String communityId;
    @NotNull
    private String acccountNumber;
    @NotNull
    private String bankName;
    @NotNull
    private String branchName;
    @NotNull
    private String swiftCode;
    @NotNull
    private String currency;
    @NotNull
    private Date openingDate;
    @NotNull
    private Double openingBalance;
    private Date toDate;
    private Date fromDate;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getAcccountNumber() {
        return this.acccountNumber;
    }

    public void setAcccountNumber(String acccountNumber) {
        this.acccountNumber = acccountNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSwiftCode() {
        return this.swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getOpeningDate() {
        return this.openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Double getOpeningBalance() {
        return this.openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
}