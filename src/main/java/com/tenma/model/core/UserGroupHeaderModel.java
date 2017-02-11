package com.tenma.model.core;

import com.tenma.auth.model.AuditModel;

/**
 * Created by gustianom on 13/11/14.
 */
public class UserGroupHeaderModel extends AuditModel {
    private Integer groupId;
    private String groupName;
    private String groupDesc;
    private String communityId;
    private Integer groupType;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * *<p> 1 -> DEFAULT SYSTEM SIGNUP ADMIN</p>
     * <p>0 -> GENERAL (SELF CREATED GROUP)</p>
     *
     * @return
     */
    public Integer getGroupType() {
        return groupType;
    }

    /**
     * <p> 1 -> DEFAULT SYSTEM SIGNUP ADMIN</p>
     * <p>0 -> GENERAL (SELF CREATED GROUP)</p>
     *
     * @param groupType
     */
    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "UserGroupHeaderModel{" +
                "groupId=" + groupId +
                "groupType=" + groupType +
                ", groupName='" + groupName + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", communityId='" + communityId + '\'' +
                '}';
    }
}
