package com.tenma.model.fam;

import com.tenma.auth.model.AuditModel;

import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/30/12
 * Time: 9:43 AM
 */
public class AccountGroupModel extends AuditModel implements java.io.Serializable {
    private String accountGrpId;
    private String accountGrpName;
    private String accountGrpImage;
    private String parentAccountGrpId;
    private String parentAccountGrpName;
    private String accountGrpDesc;
    private String recordStatus;
    private Integer accountGrpType;
    private String communityId;

    private String accountId;
    private String accountNames;

    private List accountItemList;

    public List getAccountItemList() {
        return accountItemList;
    }

    public void setAccountItemList(List accountItemList) {
        this.accountItemList = accountItemList;
    }

    public String getAccountGrpImage() {
        return accountGrpImage;
    }

    public void setAccountGrpImage(String accountGrpImage) {
        this.accountGrpImage = accountGrpImage;
    }

    public String getAccountGrpId() {
        return accountGrpId;
    }

    public void setAccountGrpId(String accountGrpId) {
        this.accountGrpId = accountGrpId;
    }

    public String getAccountGrpName() {
        return accountGrpName;
    }

    public void setAccountGrpName(String accountGrpName) {
        this.accountGrpName = accountGrpName;
    }

    public String getParentGrpId() {
        return parentAccountGrpId;
    }

    public void setParentGrpId(String parentAccountGrpId) {
        this.parentAccountGrpId = parentAccountGrpId;
    }

    public String getParentGrpName() {
        return parentAccountGrpName;
    }

    public void setParentGrpName(String parentAccountGrpName) {
        this.parentAccountGrpName = parentAccountGrpName;
    }

    public String getAccountGrpDesc() {
        return accountGrpDesc;
    }

    public void setAccountGrpDesc(String accountGrpDesc) {
        this.accountGrpDesc = accountGrpDesc;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getAccountGrpType() {
        return accountGrpType;
    }

    public void setAccountGrpType(Integer accountGrpType) {
        this.accountGrpType = accountGrpType;
    }

    @Deprecated
    public String getAccountId() {
        return accountId;
    }

    @Deprecated
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Deprecated
    public String getAccountNames() {
        return accountNames;
    }

    @Deprecated
    public void setAccountNames(String accountNames) {
        this.accountNames = accountNames;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }


}

