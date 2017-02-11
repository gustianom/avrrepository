/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:46:31 ICT 2013
 */

package com.tenma.common.model;

public class LangLabelValueModel extends LanguageLabelModel {
    private Integer langId;
    private String labelValue;
    private String communityId;

    public Integer getLangId() {
        return this.langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getLabelValue() {
        return this.labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}