/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.common.gui.display.custom;

import java.util.HashMap;

/**
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/15/14
 * Time: 12:06 PM
 */
public class TenmaCustomGenericModel {
    private HashMap fieldObject;

    public void initModel(String[] fields) {
        fieldObject = new HashMap();
        for (int i = 0; i < fields.length; i++) {
            fieldObject.put(fields[i], null);
        }
    }

    public Object getModelValue(String field) {
        return fieldObject.get(field);
    }

    public void setModelValue(String field, Object value) {
        fieldObject.put(field, value);
    }
}
