package com.tenma.fam.gui.main.balancesheetreportconfig.Field;

import com.tenma.common.TA;
import com.tenma.common.gui.display.component.AutoComplete;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.balancesheet.BalanceSheetHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.BalanceSheetModel;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 08/01/16.
 */
public class BSField extends AutoComplete implements BSSelection {
    private static final String BS_ID = "bSId";
    private static final String BS_NAME = "bSName";

    private BSSelection parent;
    private BalanceSheetModel selectedModel;
    private HashMap additionalMap;
    private TenmaTableContainer container;

    public BSField(BSSelection mainParent) {
        this.parent = mainParent;
    }

    public void resetField() {
        lookupTextId.setValue("");
        selectedModel = null;
    }

    public void setNullDisplay() {
        lookupTextId.setValue("");
    }

    public HashMap getAdditionalMap() {
        return additionalMap;
    }

    public void setAdditionalMap(HashMap additionalMap) {
        this.additionalMap = additionalMap;
    }

    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();
        container.addContainerProperty(BS_ID, Integer.class, 0);
        container.addContainerProperty(BS_NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();
        resultsTable.setVisibleColumns(BS_NAME);
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        resultsTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Item item = event.getItem();
                tableRowSelected = true;
                popupView.setPopupVisible(false);
                filterFld.setValue("");
                if (item != null) {
                    BalanceSheetHelper helper = new BalanceSheetHelper();
                    BalanceSheetModel bsModel = new BalanceSheetModel();
                    bsModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    bsModel.setBalanceSheetName((String) item.getItemProperty(BS_NAME).getValue());
                    bsModel.setBalanceSheetId((Integer) item.getItemProperty(BS_ID).getValue());
                    bsModel = helper.getBalanceSheetDetail(bsModel);
                    setModel(bsModel);
                }
            }
        });
    }

    private void setModel(BalanceSheetModel model) {
        selectedModel = model;
        lookupTextId.setValue(selectedModel.getBalanceSheetName());
        parent.setBalanceSheetModel(model);
    }

    public void addValueChangeListener(ValueChangeListener listener) {
        lookupTextId.addValueChangeListener(listener);
    }

    public void setBSId(Integer levelId) {
        BalanceSheetHelper helper = new BalanceSheetHelper();
        BalanceSheetModel bsModel = new BalanceSheetModel();
        bsModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        bsModel.setBalanceSheetId(levelId);
        bsModel = helper.getBalanceSheetDetail(bsModel);
        this.lookupTextId.setValue(bsModel.getBalanceSheetName());
        selectedModel = bsModel;
    }

    public void setDisable(Boolean disable) {
        this.lookupTextId.setEnabled(disable);
        this.btn.setEnabled(disable);
    }

    @Override
    protected List<Object> getList(String inputKey) {
        BalanceSheetHelper helper = new BalanceSheetHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        if (additionalMap != null) {
            for (Object ob : additionalMap.keySet()) {
                map.put(ob, additionalMap.get(ob));
            }
        }
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "balanceSheetName");
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.ASCENDING.getValue());
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = helper.getListRenderer(map, true);
        System.out.println("==================> ls = " + ls.size());
        return ls;
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();
        for (int i = 0; i < resultset.size(); i++) {
            BalanceSheetModel mdl = (BalanceSheetModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(BS_NAME).setValue(mdl.getBalanceSheetName());
            id.getItemProperty(BS_ID).setValue(mdl.getBalanceSheetId());
        }
        if (resultset.size() > 10) {
            resultsTable.setPageLength(10);
        } else {
            resultsTable.setPageLength(resultset.size());
        }
        resultsTable.setSizeFull();
        setSizeUndefined();
    }

    @Override
    public Object getValue() {
        return selectedModel.getBalanceSheetId();
    }

    public String getLevelName() {
        if (selectedModel == null) {
            return "-";
        } else if (selectedModel.getBalanceSheetName() != null) {
            return selectedModel.getBalanceSheetName();
        } else {
            return "-";
        }
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btn)) {
            resultset = getList(filterFld.getValue());
            setResultsTableValues();
            popupView.setPopupVisible(true);
        }
    }

    public BalanceSheetModel getSelectedModel() {
        return selectedModel;
    }


    @Override
    public void setBalanceSheetModel(BalanceSheetModel model) {
        setModel(model);
    }
}
