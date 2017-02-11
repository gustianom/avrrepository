package com.tenma.common.gui.display;

import com.tenma.auth.util.db.DaoHelper;

import com.tenma.common.util.Constants;
import com.vaadin.ui.Component;
import com.vaadin.ui.TreeTable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 5/12/13
 * Time: 2:11 PM
 */
public abstract class TenmaMasterTreeList extends TenmaList {
    private TreeTable tableProperty;


    protected TenmaMasterTreeList() {
        super();
    }

    public final void initialize() {
        tableProperty = new TreeTable();
//        tableProperty.set();
        addDataList(tableProperty);
    }

    public final Component getDataListComponent() {
        return tableProperty;
    }

    @Override
    public final List loadPagingData(HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        List result = super.loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
        tableProperty.setPageLength(result.size());
        return result;
    }

    public final TreeTable getTableProperty() {
        return tableProperty;
    }


}
