package com.tenma.common.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.fam.gui.main.account.AccountSelection;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

public class COAField extends AutoComplete {
    private final String ACCOUNT_ID = "accountId";
    private final String ACCOUNT_NAMES = "accountName";
    private final String ACCOUNT_GROUP = "accountGroupId";
    private final String ACCOUNT_VALID_FROM = "accountValidFrom";
    private final String ACCOUNT_VALID_TO = "accountValidTo";
    private final String ACCOUNT_CURRENCY = "accountCurrency";
    private final String ACCOUNT_FIXED_CURR = "accountFixCurrency";
    private final String ACCOUNT_BAL = "accountBAL";

    private AccountSelection parent;

    private AccountModel selectedModel;

    public COAField(AccountSelection aparent) {
        this.parent = aparent;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(btn)) {
            resultset = getList(filterFld.getValue());
            setResultsTableValues();
            popupView.setPopupVisible(true);
        }
    }

    @Override
    public boolean isValid() {
        return selectedModel != null;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (selectedModel != null) throw new Validator.InvalidValueException("Account is not selected");
    }

    private void setAccount(AccountModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getAccountName());
        parent.setSelectedAccount(model);
    }

    public AccountModel getAccount() {
        return selectedModel;
    }

    public void setAccount(String accountId) {
        AccountHelper help = new AccountHelper();
        AccountModel p = new AccountModel();
        p.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        p.setAccountId(accountId);
        AccountModel m = help.getAccountDetail(p);
        this.lookupTextId.setValue(m.getAccountName());
        selectedModel = m;
    }

    public void setSelectedModel(AccountModel model) {
        selectedModel = model;
        parent.setSelectedAccount(selectedModel);
    }

    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(ACCOUNT_ID, String.class, "");
        container.addContainerProperty(ACCOUNT_NAMES, String.class, "");
        container.addContainerProperty(ACCOUNT_GROUP, String.class, "");
        container.addContainerProperty(ACCOUNT_CURRENCY, String.class, "");
        container.addContainerProperty(ACCOUNT_BAL, String.class, "");
        container.addContainerProperty(ACCOUNT_FIXED_CURR, Boolean.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();

        resultsTable.setWidth("300px");
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        resultsTable.setVisibleColumns(new Object[]{ACCOUNT_ID, ACCOUNT_NAMES});
        resultsTable.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        tableRowSelected = true;
                        popupView.setPopupVisible(false);
                        filterFld.setValue("");
                        if (item != null) {
                            AccountModel pcm = new AccountModel();
                            pcm.setAccountId(item.getItemProperty(ACCOUNT_ID).getValue().toString());
                            pcm.setAccountCurrency(item.getItemProperty(ACCOUNT_CURRENCY).getValue() == null ? "" : item.getItemProperty(ACCOUNT_CURRENCY).getValue().toString());
                            pcm.setAccountName(item.getItemProperty(ACCOUNT_NAMES).getValue().toString());
                            pcm.setAccountGroupId(item.getItemProperty(ACCOUNT_GROUP).getValue() == null ? "" : item.getItemProperty(ACCOUNT_GROUP).getValue().toString());
                            pcm.setAccountNormal(item.getItemProperty(ACCOUNT_BAL).getValue().toString().equals("D") ? 0 : 1);
                            pcm.setAccountFixedCurrency((Boolean) item.getItemProperty(ACCOUNT_FIXED_CURR).getValue());
                            pcm.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                            setAccount(pcm);
                        }
//
                    }
                }
        );

    }

    public void setSelectedObject(Object obj) {
        if (obj != null) {
            selectedModel = (AccountModel) obj;
        }
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();

        for (int i = 0; i < resultset.size(); i++) {
            AccountModel mdl = (AccountModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(ACCOUNT_ID).setValue(mdl.getAccountId());
            id.getItemProperty(ACCOUNT_NAMES).setValue(mdl.getAccountName());
            id.getItemProperty(ACCOUNT_GROUP).setValue(mdl.getAccountGroupId());
            id.getItemProperty(ACCOUNT_CURRENCY).setValue(mdl.getAccountCurrency());
            id.getItemProperty(ACCOUNT_BAL).setValue(mdl.getAccountNormal().intValue() == 0 ? "D" : "C");
            id.getItemProperty(ACCOUNT_FIXED_CURR).setValue(mdl.isAccountFixedCurrency());


        }
        if (resultset.size() > 10)
            resultsTable.setPageLength(10);
        else
            resultsTable.setPageLength(resultset.size());
        resultsTable.setSizeFull();
        setSizeUndefined();
    }

    @Override
    public Object getValue() {
        return selectedModel;
    }

    public void setValue(String value) {
        setAccount(value);
    }

    @Override
    protected List<Object> getList(String inputKey) {
        AccountHelper phelper = new AccountHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "accountId");
        map.put(Constants.RECORDSELECT_SORTORDER, "ASCENDING");
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = phelper.getListRenderer(map, true);
        System.out.println("Size = " + ls.size() + " : " + inputKey);
        return ls;
    }

}
