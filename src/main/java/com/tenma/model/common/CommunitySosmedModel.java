/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Dec 23 21:11:11 WIT 2013
 */

package com.tenma.model.common;

import com.tenma.auth.model.AuditModel;

public class CommunitySosmedModel extends AuditModel {
    private Integer sosmedId;
    private String communityId;
    private String communityName;
    private String sosmedName;
    private Integer sosmedType;
    private String sosmedPageid;
    private String sosmedAppid;
    private String sosmedAccessSecreet;
    private String sosmedAccessToken;
    private String sosmedConsumerSecreet;
    private String sosmedConsumerKey;

    public String getSosmedName() {
        return sosmedName;
    }

    public void setSosmedName(String sosmedName) {
        this.sosmedName = sosmedName;
    }

    public Integer getSosmedId() {
        return this.sosmedId;
    }

    public void setSosmedId(Integer sosmedId) {
        this.sosmedId = sosmedId;
    }

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getSosmedType() {
        return this.sosmedType;
    }

    public void setSosmedType(Integer sosmedType) {
        this.sosmedType = sosmedType;
    }

    public String getSosmedPageid() {
        return this.sosmedPageid;
    }

    public void setSosmedPageid(String sosmedPageid) {
        this.sosmedPageid = sosmedPageid;
    }

    public String getSosmedAppid() {
        return this.sosmedAppid;
    }

    public void setSosmedAppid(String sosmedAppid) {
        this.sosmedAppid = sosmedAppid;
    }

    public String getSosmedAccessSecreet() {
        return this.sosmedAccessSecreet;
    }

    public void setSosmedAccessSecreet(String sosmedAccessSecreet) {
        this.sosmedAccessSecreet = sosmedAccessSecreet;
    }

    public String getSosmedAccessToken() {
        return this.sosmedAccessToken;
    }

    public void setSosmedAccessToken(String sosmedAccessToken) {
        this.sosmedAccessToken = sosmedAccessToken;
    }

    public String getSosmedConsumerSecreet() {
        return this.sosmedConsumerSecreet;
    }

    public void setSosmedConsumerSecreet(String sosmedConsumerSecreet) {
        this.sosmedConsumerSecreet = sosmedConsumerSecreet;
    }

    public String getSosmedConsumerKey() {
        return this.sosmedConsumerKey;
    }

    public void setSosmedConsumerKey(String sosmedConsumerKey) {
        this.sosmedConsumerKey = sosmedConsumerKey;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}