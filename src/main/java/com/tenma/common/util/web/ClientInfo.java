/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.util.web;

import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.SecurityModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.web.AuthInfo;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:21 AM
 */
public class ClientInfo extends AuthInfo {

    private Integer sessionProcessId;
    private String sessionProcessImageId;
    private String sessionProcessName;
    private String sessionProcessAction;
    private Integer sessionProcessGroupId;
    private String sessionProcessGroupName;
    private int ratio;
    private UserModel clientUserModel = new UserModel();
    private MemberModel clientMemberModel = new MemberModel();
    private SecurityModel securityModel = new SecurityModel();

    public SecurityModel getSecurityModel() {
        return securityModel;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public Integer getSessionProcessId() {
        return sessionProcessId;
    }

    public void setSessionProcessId(Integer sessionProcessId) {
        System.out.println("ClientInfo.setSessionProcessId " + sessionProcessId);
        this.sessionProcessId = sessionProcessId;
    }

    public Integer getSessionProcessGroupId() {
        return sessionProcessGroupId;
    }

    public void setSessionProcessGroupId(Integer sessionProcessGroupId) {
        System.out.println("ClientInfo.setSessionProcessGroupId " + sessionProcessGroupId);
        this.sessionProcessGroupId = sessionProcessGroupId;
    }

    public String getSessionProcessImageId() {
        return sessionProcessImageId;
    }

    public void setSessionProcessImageId(String sessionProcessImageId) {
        this.sessionProcessImageId = sessionProcessImageId;
    }

    public String getSessionProcessName() {
        return sessionProcessName;
    }

    public void setSessionProcessName(String sessionProcessName) {
        this.sessionProcessName = sessionProcessName;
    }

    public String getSessionProcessAction() {
        return sessionProcessAction;
    }

    public void setSessionProcessAction(String sessionProcessAction) {
        this.sessionProcessAction = sessionProcessAction;
    }

    public String getSessionProcessGroupName() {
        return sessionProcessGroupName;
    }

    public void setSessionProcessGroupName(String sessionProcessGroupName) {
        this.sessionProcessGroupName = sessionProcessGroupName;
    }

    public UserModel getClientUserModel() {
        return clientUserModel;
    }

    public void setClientUserModel(UserModel clientUserModel) {
        this.clientUserModel = clientUserModel;
    }

    public MemberModel getClientMemberModel() {
        return clientMemberModel;
    }

    public void setClientMemberModel(MemberModel clientMemberModel) {
        this.clientMemberModel = clientMemberModel;
    }
}

