/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Wed Jun 25 16:41:39 WIB 2014
 */

package com.tenma.model.core;

import com.tenma.auth.model.AuditModel;

public class MenuModel extends AuditModel {
    private Integer menuId;
    private Integer menuGrpId;
    private String menuName;
    private String menuDesc;
    private Integer menuType;
    private Integer menuOrder;
    private Integer nextMenuId;

    private String menuGrpName;
    private String parentGrpId;
    private String parentGrpName;
    private String menuDescription;
    private String menuAction;
    private String menuImage;
    private Integer recordStatus;
    private int menuFav;
    private boolean autoUpdate;
    private boolean collapse;

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuGrpId() {
        return this.menuGrpId;
    }

    public void setMenuGrpId(Integer menuGrpId) {
        this.menuGrpId = menuGrpId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return this.menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public Integer getMenuType() {
        return this.menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuOrder() {
        return this.menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getNextMenuId() {
        return this.nextMenuId;
    }

    public void setNextMenuId(Integer nextMenuId) {
        this.nextMenuId = nextMenuId;
    }

    public String getMenuGrpName() {
        return menuGrpName;
    }

    public void setMenuGrpName(String menuGrpName) {
        this.menuGrpName = menuGrpName;
    }

    public String getParentGrpId() {
        return parentGrpId;
    }

    public void setParentGrpId(String parentGrpId) {
        this.parentGrpId = parentGrpId;
    }

    public String getParentGrpName() {
        return parentGrpName;
    }

    public void setParentGrpName(String parentGrpName) {
        this.parentGrpName = parentGrpName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getMenuFav() {
        return menuFav;
    }

    public void setMenuFav(int menuFav) {
        this.menuFav = menuFav;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }
}