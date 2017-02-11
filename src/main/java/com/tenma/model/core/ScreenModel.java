/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Wed Jun 25 16:22:23 WIB 2014
 */

package com.tenma.model.core;

import com.tenma.auth.model.AuditModel;

public class ScreenModel extends AuditModel {
    private Integer screenId;
    private String screenName;
    private String screenClass;
    private Integer nextScreenId;
    private String screenType;

    private String screenDesc;

    public Integer getScreenId() {
        return this.screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenClass() {
        return this.screenClass;
    }

    public void setScreenClass(String screenClass) {
        this.screenClass = screenClass;
    }

    public Integer getNextScreenId() {
        return this.nextScreenId;
    }

    public void setNextScreenId(Integer nextScreenId) {
        this.nextScreenId = nextScreenId;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenDesc() {
        return screenDesc;
    }

    public void setScreenDesc(String screenDesc) {
        this.screenDesc = screenDesc;
    }
}