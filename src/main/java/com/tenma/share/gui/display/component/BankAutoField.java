package com.tenma.share.gui.display.component;

import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.bank_management.BankManagementHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.BankManagementModel;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ndwijaya on 12/7/14.
 */
public class BankAutoField extends AutoComplete {
    public static final String BANK_ID = "bankID";
    public static final String BANK_NAME = "bankName";

    private BankSelection parent;


    private BankManagementModel selectedModel;

    public BankAutoField(BankSelection aparent) {

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
    public void setEnabled(boolean enabled) {
        lookupTextId.setEnabled(enabled);
        btn.setEnabled(enabled);
    }

    private void setAccount(BankManagementModel model) {
        selectedModel = model;
        lookupTextId.setValue(model.getBankName());
        parent.setBankModel(model);
    }

    public BankManagementModel getBank() {
        return selectedModel;
    }

    public void setBank(String bankId) {
        System.out.println("bankId = " + bankId);
        BankManagementHelper help = new BankManagementHelper();

        BankManagementModel p = new BankManagementModel();
        p.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        p.setBankId(bankId);
        BankManagementModel m = help.getBankManagementDetail(p);
        selectedModel = m;
        this.lookupTextId.setValue(m.getBankName());
    }

    public String getVehicleName() {
        if (selectedModel == null) {
            return "";
        } else if (selectedModel.getBankName() != null) {
            return selectedModel.getBankName();
        } else {
            return "";
        }
    }

    public void setSelectedModel(BankManagementModel model) {
        selectedModel = model;
        parent.setBankModel(selectedModel);
    }


    @Override
    protected void initTable() {
        resultsTable = new Table();
        resultsTable.setSelectable(true);
        container = new TenmaTableContainer();

        container.addContainerProperty(BANK_ID, String.class, 0);
        container.addContainerProperty(BANK_NAME, String.class, "");

        resultsTable.setContainerDataSource(container);
        resultsTable.commit();

        resultsTable.setWidth("300px");
        resultsTable.setImmediate(true);
        resultsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        resultsTable.setVisibleColumns(new Object[]{BANK_NAME});
        resultsTable.addItemClickListener(
                new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent event) {
                        Item item = event.getItem();
                        tableRowSelected = true;
                        popupView.setPopupVisible(false);
                        filterFld.setValue("");
                        if (item != null) {
                            BankManagementModel pcm = new BankManagementModel();
                            pcm.setBankId(item.getItemProperty(BANK_ID).getValue().toString());
                            pcm.setBankName(item.getItemProperty(BANK_NAME).getValue().toString());
                            pcm.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                            setAccount(pcm);
                            setSelectedObject(pcm);
                        }
//
                    }
                }
        );

    }

    public void setSelectedObject(Object obj) {
        if (obj != null) {
            selectedModel = (BankManagementModel) obj;
        }
    }

    @Override
    public void setResultsTableValues() {
        container.removeAllItems();

        for (int i = 0; i < resultset.size(); i++) {
            BankManagementModel mdl = (BankManagementModel) resultset.get(i);
            Item id = container.addItem(i);
            id.getItemProperty(BANK_ID).setValue(mdl.getBankId());
            id.getItemProperty(BANK_NAME).setValue(mdl.getBankName());
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

    @Override
    public boolean isValid() {
        boolean rs = false;
        if (selectedModel != null) rs = true;
        return rs;
    }

    @Override
    public void validate() throws Validator.InvalidValueException {
        super.validate();
        if (selectedModel != null) throw new Validator.InvalidValueException("Bank is not selected");
    }

    @Override
    protected List<Object> getList(String inputKey) {
        BankManagementHelper phelper = new BankManagementHelper();
        HashMap map = new HashMap();
        Integer skip = new Integer(0);
        int pageSize = 20;
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "bankName");
        map.put(Constants.RECORDSELECT_SORTORDER, "ASCENDING");
        map.put("searchValue", "%" + inputKey.trim() + "%");
        map.put(Constants.RECORDSELECT_SKIP, skip);
        map.put(Constants.RECORDSELECT_MAX, pageSize);
        List ls = phelper.getListRenderer(map, true);
        System.out.println("Size = " + ls.size() + " : " + inputKey);
        return ls;
    }

}
