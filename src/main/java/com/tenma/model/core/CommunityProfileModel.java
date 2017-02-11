/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Oct 05 23:28:51 ICT 2015
 */

package com.tenma.model.core;

import com.tenma.auth.model.AuditModel;

public class CommunityProfileModel extends AuditModel {
    private String communityId;
    private String profile;
    private String communityGroup;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCommunityGroup() {
        return communityGroup;
    }

    public void setCommunityGroup(String communityGroup) {
        this.communityGroup = communityGroup;
    }

}