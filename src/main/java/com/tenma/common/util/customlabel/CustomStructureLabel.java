/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 3/4/14
 * Time: 9:06 AM
 */

package com.tenma.common.util.customlabel;

import com.tenma.common.bean.structurelabel.StructureLabelHelper;
import com.tenma.model.common.StructureLabelModel;

import java.util.HashMap;

public class CustomStructureLabel {
    private static CustomStructureLabel instance;
    HashMap mapCache;

    private CustomStructureLabel() {
        mapCache = new HashMap();
    }

    public static CustomStructureLabel getInstance() {
        if (instance == null) {
            instance = new CustomStructureLabel();
        }
        return instance;
    }

    public String getStructureLabel(String structureId, String argLabel) {
        Object o = mapCache.get(structureId + argLabel);
        String rst;
        if (o == null) {
            StructureLabelHelper helper = new StructureLabelHelper();
            StructureLabelModel m = new StructureLabelModel();
            m.setStructId(structureId);
            m.setLabelId(argLabel);
            StructureLabelModel rm = helper.getStructureLabelDetail(m);
            if (rm != null) {
                rst = rm.getLabelValue();
            } else {
                rst = argLabel;
            }
        } else {
            rst = o.toString();
        }
        return rst;
    }

    public void refreshCache() {
        mapCache = new HashMap();
    }
}
