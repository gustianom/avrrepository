/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.model.common;

import java.util.List;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/18/14
 * Time: 3:49 PM
 */
public class TenmaCustomJSONModel {
    List<TenmaCustomFieldsModel> lsCustomFieldModel;

    public List<TenmaCustomFieldsModel> getLsCustomFieldModel() {
        return lsCustomFieldModel;
    }

    public void setLsCustomFieldModel(List<TenmaCustomFieldsModel> lsCustomFieldModel) {
        this.lsCustomFieldModel = lsCustomFieldModel;
    }
}
