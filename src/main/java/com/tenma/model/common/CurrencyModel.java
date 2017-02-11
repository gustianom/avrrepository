/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

/**
 * User: ndwijaya
 * Date: Feb 28, 2011
 * Time: 3:58:49 PM
 */
public class CurrencyModel extends AuditModel {
    private String currencyId;
    private String currencyName;
    private String countryId;
    private int recordStatus;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }
}
