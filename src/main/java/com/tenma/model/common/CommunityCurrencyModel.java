package com.tenma.model.common;

import com.tenma.auth.model.CommunityModel;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 5/19/12
 * Time: 12:00 PM
 */
public class CommunityCurrencyModel extends CommunityModel {

    private String currencyId;
    private String currencyName;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
