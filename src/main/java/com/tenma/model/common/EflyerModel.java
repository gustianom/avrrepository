/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Tue Mar 17 15:58:47 ICT 2015
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class EflyerModel extends AuditModel {
    private String communityId;
    private Integer eflyerId;
    private String eflyerName;
    private String eflyerData;
    private Integer paperSize;
    private Integer orientation;
    private Integer typeTemplate;

    public Integer getTypeTemplate() {
        return typeTemplate;
    }

    public void setTypeTemplate(Integer typeTemplate) {
        this.typeTemplate = typeTemplate;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Integer getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(Integer paperSize) {
        this.paperSize = paperSize;
    }

    public String getEflyerName() {
        return eflyerName;
    }

    public void setEflyerName(String eflyerName) {
        this.eflyerName = eflyerName;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getEflyerId() {
        return this.eflyerId;
    }

    public void setEflyerId(Integer eflyerId) {
        this.eflyerId = eflyerId;
    }

    public String getEflyerData() {
        return this.eflyerData;
    }

    public void setEflyerData(String eflyerData) {
        this.eflyerData = eflyerData;
    }

}