/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 13:37:27 WIB 2016
 */

package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

public class BalanceSheetDetailModel extends AuditModel {
    private String communityId;
    private Integer balanceSheetId;
    private String accountId;
    private String accountName;

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

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}