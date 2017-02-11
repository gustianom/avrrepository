/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import java.util.Date;

public class UserLoadActivityModel {
    private String communityId;
    private Date logStart;
    private Date logEnd;
    private String userId;
    private Integer logId;
    private Integer menuItemId;
    private Integer itemDuration;
    private Integer hitCount;
    private Integer errorCount;
    private Integer nextMenu;
    private String menuItemName;
    private String memberName;
    private Integer count;

    public String getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Date getLogStart() {
        return this.logStart;
    }

    public void setLogStart(Date logStart) {
        this.logStart = logStart;
    }

    public Date getLogEnd() {
        return this.logEnd;
    }

    public void setLogEnd(Date logEnd) {
        this.logEnd = logEnd;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLogId() {
        return this.logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getMenuItemId() {
        return this.menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getItemDuration() {
        return this.itemDuration;
    }

    public void setItemDuration(Integer itemDuration) {
        this.itemDuration = itemDuration;
    }

    public Integer getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Integer getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(Integer nextMenu) {
        this.nextMenu = nextMenu;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}