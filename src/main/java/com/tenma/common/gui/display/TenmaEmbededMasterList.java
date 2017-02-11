package com.tenma.common.gui.display;


import com.tenma.common.util.CommonPagingHelper;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * User: anom
 * Date: 10/27/12
 * Time: 3:51 PM
 */

public abstract class TenmaEmbededMasterList extends TenmaEmbededList {
    private Table tableProperty;

    protected TenmaEmbededMasterList() {

        System.out.println();
        System.out.println();
    }

    public final void initialize() {
        tableProperty = new Table();
        tableProperty.setSizeFull();
        addListComponent(tableProperty);
    }

    public final Component getDataListComponent() {
        return tableProperty;
    }

    public final List loadPagingData(HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, CommonPagingHelper helper) {
        List result = paging.loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
        tableProperty.setPageLength(result.size());
        return result;
    }

    public final List loadPagingData(int pageSize, HashMap parameterMap, String sortField, String sortOrder, String loggingCaption, CommonPagingHelper helper) {
        List result = paging.loadPagingData(pageSize, parameterMap, sortField, sortOrder, loggingCaption, helper);
        tableProperty.setPageLength(result.size());
        return result;
    }


    public final Table getTableProperty() {
        return tableProperty;
    }


}
