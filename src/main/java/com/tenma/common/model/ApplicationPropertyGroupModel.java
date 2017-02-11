/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.model;

import com.tenma.auth.model.AuditModel;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: Dec 26, 2010
 * Time: 12:11:01 AM
 */
public class ApplicationPropertyGroupModel extends AuditModel {
    Integer appGroupType;
    private String appGrpCd, appGrpName, appGrpDesc, appGrpTblField, appGrpRecSts, communityId;

    /**
     * Community owner of this Group. IT filled when the APPGRPTYPE is 2000
     *
     * @return
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * Community owner of this Group. IT filled when the APPGRPTYPE is 2000
     *
     * @param communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * -- 1000 FOR SYSTEM, 2000 FOR ALL CLIENT, 3000 is for specific communityId
     *
     * @return
     */
    public Integer getAppGroupType() {
        return appGroupType;
    }

    /**
     * -- 1000 FOR SYSTEM, 2000 FOR ALL CLIENT, 3000 is for specific communityId
     *
     * @param appGroupType
     */
    public void setAppGroupType(Integer appGroupType) {
        this.appGroupType = appGroupType;
    }

    public String getAppGrpCd() {
        return appGrpCd;
    }

    public void setAppGrpCd(String appGrpCd) {
        this.appGrpCd = appGrpCd;
    }

    public String getAppGrpName() {
        return appGrpName;
    }

    public void setAppGrpName(String appGrpName) {
        this.appGrpName = appGrpName;
    }

    public String getAppGrpDesc() {
        return appGrpDesc;
    }

    public void setAppGrpDesc(String appGrpDesc) {
        this.appGrpDesc = appGrpDesc;
    }

    public String getAppGrpTblField() {
        return appGrpTblField;
    }

    public void setAppGrpTblField(String appGrpTblField) {
        this.appGrpTblField = appGrpTblField;
    }

    public String getAppGrpRecSts() {
        return appGrpRecSts;
    }

    public void setAppGrpRecSts(String appGrpRecSts) {
        this.appGrpRecSts = appGrpRecSts;
    }
}
