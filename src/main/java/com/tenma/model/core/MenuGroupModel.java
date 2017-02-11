/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.model.core;

import com.tenma.auth.model.AuditModel;

import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:25 AM
 */
public class MenuGroupModel extends AuditModel {
    private Integer menuGrpId;
    private String menuGrpName;
    private String menuGrpImage;
    private Integer parentGrpId;
    private String parentGrpName;
    private String menuGrpDesc;
    private boolean collapse;
    private int menuOrder;
    private int recordStatus;
    private boolean autoUpdate;

    private List<MenuModel> menuItemList;

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }


    public List getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuModel> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public String getMenuGrpImage() {
        return menuGrpImage;
    }

    public void setMenuGrpImage(String menuGrpImage) {
        this.menuGrpImage = menuGrpImage;
    }

    public Integer getMenuGrpId() {
        return menuGrpId;
    }

    public void setMenuGrpId(Integer menuGrpId) {
        this.menuGrpId = menuGrpId;
    }

    public String getMenuGrpName() {
        return menuGrpName;
    }

    public void setMenuGrpName(String menuGrpName) {
        this.menuGrpName = menuGrpName;
    }

    public Integer getParentGrpId() {
        return parentGrpId;
    }

    public void setParentGrpId(Integer parentGrpId) {
        this.parentGrpId = parentGrpId;
    }

    public String getParentGrpName() {
        return parentGrpName;
    }

    public void setParentGrpName(String parentGrpName) {
        this.parentGrpName = parentGrpName;
    }

    public String getMenuGrpDesc() {
        return menuGrpDesc;
    }

    public void setMenuGrpDesc(String menuGrpDesc) {
        this.menuGrpDesc = menuGrpDesc;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
}