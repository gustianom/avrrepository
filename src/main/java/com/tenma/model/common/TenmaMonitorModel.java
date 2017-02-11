/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import java.util.Calendar;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/13/14
 * Time: 1:01 PM
 */
public class TenmaMonitorModel {
    private String communityId;
    private Integer menuId;
    private int counter;
    private long startLog;
    private long endLog;
    private Integer nextMenu;

    public TenmaMonitorModel(Integer menuId) {
        this.menuId = menuId;
        counter = 1;
        startLog = Calendar.getInstance().getTimeInMillis();
        endLog = startLog;
    }

    public void addCounter() {
        counter++;
        endLog = Calendar.getInstance().getTimeInMillis();
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public int getCounter() {
        return counter;
    }

    public long getStartLog() {
        return startLog;
    }

    public long getEndLog() {
        return endLog;
    }

    public void setEndLog(long endLog) {
        this.endLog = endLog;
    }

    public Integer getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(Integer nextMenu) {
        this.nextMenu = nextMenu;
    }
}
