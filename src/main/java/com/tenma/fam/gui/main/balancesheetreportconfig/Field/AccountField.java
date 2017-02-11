package com.tenma.fam.gui.main.balancesheetreportconfig.Field;

import com.tenma.common.TA;

import com.tenma.common.gui.display.component.AutoComplete;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 08/01/16.
 */
public class AccountField extends AutoComplete implements AccountSelection {
    private static final String ACCOUNT_ID = "accountId";
    private static final String ACCOUNT_NAME = "accountName";

    private AccountSelection parent;
    private AccountModel selectedModel;
    private HashMap additionalMap;
    private TenmaTableContainer container;

    public AccountField(AccountSelection mainParent) {
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
        container.addContainerProperty(ACCOUNT_ID, String.class, "");
        container.addContainerProperty(ACCOUNT_NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();
        resultsTable.setVisibleColumns(ACCOUNT_NAME);
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
                    AccountModel lvlModel = new AccountModel();
                    lvlModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    lvlModel.setAccountName((String) item.getItemProperty(ACCOUNT_NAME).getValue());
                    lvlModel.setAccountId((String) item.getItemProperty(ACCOUNT_ID).getValue());
                    AccountHelper helper = new AccountHelper();
                    lvlModel = helper.getAccountDetail(lvlModel);
                    setModel(lvlModel);
                }
            }
        });
    }

    private void setModel(AccountModel model) {
        selectedModel = model;
        lookupTextId.setValue(selectedModel.getAccountName());
        parent.setAccountModel(model);
    }

    public void addValueChangeListener(ValueChangeListener listener) {
        lookupTextId.addValueChangeListener(listener);
    }

    public void setLevelId(String levelId) {
        AccountHelper helper = new AccountHelper();
        AccountModel levelModel = new AccountModel();
        levelModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        levelModel.setAccountId(levelId);
        levelModel = helper.getAccountCommunityDetail(levelModel);
        this.lookupTextId.setValue(levelModel.getAccountName());
        selectedModel = levelModel;
    }

    public void setDisable(Boolean disable) {
        this.lookupTextId.setEnabled(disable);
        this.btn.setEnabled(disable);
    }

    @Override
    protected List<Object> getList(String inputKey) {
        AccountHelper levelHelper = new AccountHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        if (additionalMap != null) {
            for (Object ob : additionalMap.keySet()) {
                map.put(ob, additionalMap.get(ob));
            }
        }
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "accountName");
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.ASCENDING.getValue());
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = levelHelper.getListRenderer(map, true);
        System.out.println("==================> ls = " + ls.size());
        return ls;
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();
        for (int i = 0; i < resultset.size(); i++) {
            AccountModel mdl = (AccountModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(ACCOUNT_NAME).setValue(mdl.getAccountName());
            id.getItemProperty(ACCOUNT_ID).setValue(mdl.getAccountId());
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
        return selectedModel.getAccountId();
    }

    public String getLevelName() {
        if (selectedModel == null) {
            return "-";
        } else if (selectedModel.getAccountName() != null) {
            return selectedModel.getAccountName();
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

    public AccountModel getSelectedModel() {
        return selectedModel;
    }


    @Override
    public void setAccountModel(AccountModel model) {
        setModel(model);
    }
}
