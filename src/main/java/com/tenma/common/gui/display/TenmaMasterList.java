package com.tenma.common.gui.display;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.TenmaTable;
import com.tenma.common.util.Constants;
import com.vaadin.ui.Component;

import java.util.HashMap;
import java.util.List;

/**
 * User: anom
 * Date: 10/27/12
 * Time: 3:51 PM
 */

public abstract class TenmaMasterList extends TenmaList {
    private TenmaTable tableProperty;
    private boolean dbSortImplemented = false;
    private String sortColumn;
    private boolean sortHeaderStateAscending = false;


    protected TenmaMasterList() {
        // not implement DB Column SORT
        super();
    }

    protected TenmaMasterList(String[] dBColumnId) {
        super();
        tableProperty.setDbColumn(dBColumnId);
    }

    public void setDateFormatType(String formatType) {
        tableProperty.setDateFormatType(formatType);
    }


    public final void initialize() {
        tableProperty = new TenmaTable(this);
        addDataList(tableProperty);
    }

    public final Component getDataListComponent() {
        return tableProperty;
    }

    @Override
    public final List loadPagingData(HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        if (dbSortImplemented) {
            sortField = sortColumn == null ? sortField : sortColumn;
            sortOrder = getCustomSortOrder(sortOrder);
        }
        List result = super.loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);

        tableProperty.setPageLength(result.size() + 1);
        return result;
    }

    public final void fireCustomDBSort(String column, boolean ascending) {
        dbSortImplemented = true;  // will be default true if performReload is override
        sortColumn = column;
        sortHeaderStateAscending = ascending;
        performReloadForTableSort();
    }

    public void performReloadForTableSort() {
        dbSortImplemented = false; // will be false when not override/implemnted
        System.out.println("Custom Sort from DB is not implemented");
    }

    public final TenmaTable getTableProperty() {
        return tableProperty;
    }

    private Constants.SORTING_TYPE getCustomSortOrder(Constants.SORTING_TYPE sortOrder) {
        if (sortHeaderStateAscending)
            return Constants.SORTING_TYPE.ASCENDING;
        else {
            return Constants.SORTING_TYPE.DESCENDING;
        }
    }


}
