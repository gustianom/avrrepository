package com.tenma.common.gui.display.list.gridview;

import com.tenma.auth.util.db.DaoHelper;
import com.tenma.common.gui.display.TenmaList;
import com.tenma.common.util.Constants;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 19/11/14.
 */
public abstract class TenmaGridViewList extends TenmaList implements LayoutEvents.LayoutClickListener {
    private TenmaInnerGridView gridProperty;

    public TenmaGridViewList() {
        super();
    }

    public final void initialize() {
        gridProperty = new TenmaInnerGridView() {
            @Override
            protected String getNoRecordAvailableLabel() {
                return getLabelNoRecordAvailable();
            }

            @Override
            public boolean isExist(HashMap mapExistingItems, Object data) {
                return isItemExist(mapExistingItems, data);
            }

            @Override
            protected String getFrameItemStyleName() {
                return getFrameItemStyle();
            }

            @Override
            protected String getThirdLayerStyleName() {
                return getThirdLayerStyle();
            }

            @Override
            protected Component createThirdLayerFrameComponent(Object data) {
                return createFrameThirdLayerComponent(data);
            }

            @Override
            protected Component createSecondLayerFrameComponent(Object data) {
                return createFrameSecondLayerComponent(data);
            }

            @Override
            protected String getSecondLayerStyleName() {
                return getSecondLayerStyle();
            }

            @Override
            protected String getFirstLayerStyleName() {
                return getFirstLayerStyle();
            }

            @Override
            protected Component createFirstLayerFrameComponent(Object data) {
                return createFrameFirstLayerComponent(data);
            }

            @Override
            protected String getFrameDescription(Object data) {
                return getFrameItemDescription(data);
            }
        };
        gridProperty.setWidth(100, Unit.PERCENTAGE);
        addDataList(gridProperty);
    }

    protected abstract String getFrameItemDescription(Object data);

    protected abstract Component createFrameFirstLayerComponent(Object data);

    protected abstract String getFirstLayerStyle();

    protected abstract String getSecondLayerStyle();

    protected abstract Component createFrameSecondLayerComponent(Object data);

    protected abstract Component createFrameThirdLayerComponent(Object data);


    @Override
    protected TenmaInnerGridView getDataListComponent() {
        return gridProperty;
    }

    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
    }

    @Override
    public final List loadPagingData(HashMap parameterMap, String sortField, Constants.SORTING_TYPE sortOrder, String loggingCaption, DaoHelper helper) {
        return super.loadPagingData(parameterMap, sortField, sortOrder, loggingCaption, helper);
    }


    public abstract String getFrameItemStyle();

    public abstract String getThirdLayerStyle();

    public abstract String getLabelNoRecordAvailable();


    public abstract boolean isItemExist(HashMap mapExistingItems, Object data);

}


