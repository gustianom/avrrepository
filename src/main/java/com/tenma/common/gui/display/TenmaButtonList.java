package com.tenma.common.gui.display;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.util.Constants;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.GridLayout;
import org.vaadin.peter.contextmenu.ContextMenu;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: administrator
 * Date: 5/21/13
 * Time: 2:39 PM
 */
public abstract class TenmaButtonList extends TenmaList implements LayoutEvents.LayoutClickListener {
    private GridLayout gridProperty;

    public TenmaButtonList() {
        super();
    }

    public TenmaButtonList(boolean isSetting) {
        super();
    }

    protected abstract void createListLayout();

    public final void initialize() {
        gridProperty = new GridLayout();
        gridProperty.setSizeFull();
        addDataList(gridProperty);
    }

    public final GridLayout getDataListComponent() {
        return gridProperty;
    }


    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
    }

    public void contextMenuItemClicked(ContextMenu.ContextMenuItemClickEvent event) {
    }

    @Override
    public final List loadPagingData(HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        return super.loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
    }
}

