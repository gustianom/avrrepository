package com.tenma.model.core;


import com.tenma.auth.model.MemberModel;

/**
 * Created by gustianom on 13/11/14.
 */
public class UserGroupDetailModel extends MemberModel {
    private int groupId;
    private String communityId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
