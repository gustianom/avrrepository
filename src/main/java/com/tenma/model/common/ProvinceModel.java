/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * TenmaGenerator Version 1.5.beta.1
 * Generated on Mon Aug 18 16:28:23 WIB 2014
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class ProvinceModel extends AuditModel {
    private String communityId;
    private Integer provinceId;
    private String provinceName;
    private Integer depdagriCode; // DEPDAGRI_CODE is 2 digit code for province at Indo

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getDepdagriCode() {
        return depdagriCode;
    }

    public void setDepdagriCode(Integer depdagriCode) {
        this.depdagriCode = depdagriCode;
    }
}