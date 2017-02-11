package com.tenma.auth.model;

/**
 * Created by gustianom on 02/10/15.
 */
public class CommunityMemberModel extends MemberModel {
    private String communityId;
    private Integer memberStatus;
    private Integer memberGroup;
    private Integer memberType;
    private String bankIdentificationNumber;
    private String bankVirtualAccountNumber;
    private Integer relationStatus;
    private Integer authorityStatus;
    private String parentCommunityId;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentCommunityId() {
        return parentCommunityId;
    }

    public void setParentCommunityId(String parentCommunityId) {
        this.parentCommunityId = parentCommunityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }

    public void setBankIdentificationNumber(String bankIdentificationNumber) {
        this.bankIdentificationNumber = bankIdentificationNumber;
    }

    public String getBankVirtualAccountNumber() {
        return bankVirtualAccountNumber;
    }

    public void setBankVirtualAccountNumber(String bankVirtualAccountNumber) {
        this.bankVirtualAccountNumber = bankVirtualAccountNumber;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

    public Integer getAuthorityStatus() {
        return authorityStatus;
    }

    public void setAuthorityStatus(Integer authorityStatus) {
        this.authorityStatus = authorityStatus;
    }

    public Integer getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(Integer memberGroup) {
        this.memberGroup = memberGroup;
    }
}
