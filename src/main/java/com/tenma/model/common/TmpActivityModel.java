/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import java.io.Serializable;

public class TmpActivityModel implements Serializable {
    private String communityId;
    private String userId;
    private Integer menuid;
    private Integer counter;
    private long startlog;
    private long endlog;
    private Integer nextMenu;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMenuid() {
        return this.menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getCounter() {
        return this.counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public long getStartlog() {
        return this.startlog;
    }

    public void setStartlog(long startlog) {
        this.startlog = startlog;
    }

    public long getEndlog() {
        return this.endlog;
    }

    public void setEndlog(long endlog) {
        this.endlog = endlog;
    }

    public Integer getNextMenu() {
        return this.nextMenu;
    }

    public void setNextMenu(Integer nextMenu) {
        this.nextMenu = nextMenu;
    }

}