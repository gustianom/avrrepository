/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 10:49:49 WIB 2016
 */

package com.tenma.model.fam;

import java.io.Serializable;

public class BalanceSheetModel implements Serializable {
    private String communityId;
    private Integer balanceSheetId;
    private String balanceSheetName;
    private String typeName;
    private Integer type;
    private Double debit;
    private Double credit;
    private Integer statusValue;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getBalanceSheetId() {
        return this.balanceSheetId;
    }

    public void setBalanceSheetId(Integer balanceSheetId) {
        this.balanceSheetId = balanceSheetId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBalanceSheetName() {
        return balanceSheetName;
    }

    public void setBalanceSheetName(String balanceSheetName) {
        this.balanceSheetName = balanceSheetName;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}