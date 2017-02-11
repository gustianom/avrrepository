/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:26 ICT 2013
 */

package com.tenma.common.model;

import com.tenma.auth.model.AuditModel;

public class LanguagesModel extends AuditModel {
    private Integer langId;
    private String langCode;
    private String langName;
    private String langImg;
    private Integer recordStatus;

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getLangId() {
        return this.langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangName() {
        return this.langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangImg() {
        return this.langImg;
    }

    public void setLangImg(String langImg) {
        this.langImg = langImg;
    }

}