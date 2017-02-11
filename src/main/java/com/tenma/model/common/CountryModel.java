/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Jul 27 11:39:09 WIB 2015
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class CountryModel extends AuditModel {
    private Integer countryId;
    private String countryName;
    private String iso2;
    private String iso3;

    public Integer getCountryId() {
        return this.countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso2() {
        return this.iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return this.iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

}