/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 */

package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountGroupHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountGroupModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/29/13
 * Time: 4:11 PM
 */
public class SelectAccountGroup extends TenmaWindow {
    private final String ACCOUNT_GROUP_ID = "accountGroupId";
    private final String ACCOUNT_GROUP_NAME = "accountGroupName";
    TenmaTableContainer ic = new TenmaTableContainer();
    private TenmaWindow tenmaWindow;
    private TenmaPanel tenmaPanel;
    private Button butCancel;
    private Button butSave;
    private Table tablePurchase = new Table();
    private AccountGroupModel accountGroupModel;
    private Integer type;

    public SelectAccountGroup(TenmaPanel parentContainer, Integer type) {
        super();
        this.tenmaPanel = parentContainer;
        setType(type);
        generateUI();
        dataCollection();
    }

    public SelectAccountGroup(TenmaWindow parentContainer, Integer type) {
        super();
        this.tenmaWindow = parentContainer;
        setType(type);
        generateUI();
        dataCollection();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private void generateUI() {

        butCancel = new Button(getLabel(Constants.LABEL_CANCEL));
        butSave = new Button(getLabel(Constants.LABEL_SELECT));


        HorizontalLayout hr = new HorizontalLayout();
        hr.addComponent(butSave);
        hr.addComponent(butCancel);
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.addComponent(hr);
        ly.addComponent(tablePurchase);
        setContent(ly);


    }

    private void dataCollection() {
        ic = new TenmaTableContainer();
        tablePurchase.setContainerDataSource(ic);
        ic.addContainerProperty(ACCOUNT_GROUP_ID, String.class, "");
        ic.addContainerProperty(ACCOUNT_GROUP_NAME, String.class, "");
        tablePurchase.commit();
//        tablePurchase.setColumnHeaders(new String[]{
//                getLabel("account.accountGroupId"),
//                getLabel("account.accountGroupName")
//
//        }
//        );
        tablePurchase.setColumnCollapsingAllowed(true);
        tablePurchase.setSelectable(true);
        tablePurchase.setImmediate(true);
        tablePurchase.setMultiSelect(false);
        tablePurchase.setColumnCollapsed(ACCOUNT_GROUP_ID, true);


        tablePurchase.addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                System.out.println();
                System.out.println();
                System.out.println("value - " + valueChangeEvent.getProperty().getValue());
                Item item = tablePurchase.getItem(valueChangeEvent.getProperty().getValue());
                accountGroupModel = new AccountGroupModel();
                System.out.println("item = " + item);
                accountGroupModel.setAccountGrpId((String) item.getItemProperty(ACCOUNT_GROUP_ID).getValue());
                accountGroupModel.setAccountGrpName((String) item.getItemProperty(ACCOUNT_GROUP_NAME).getValue());

            }
        });

        ic = collectData();
    }

    private TenmaTableContainer collectData() {
        AccountGroupHelper help = new AccountGroupHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List ls = help.getListRenderer(p, false);
        String lc = getLogger().openLog();
        getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, "View  AccountGroup Dialog CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());


        for (int i = 0; i < ls.size(); i++) {
            AccountGroupModel m = (AccountGroupModel) ls.get(i);
            Item id = ic.addItem(m.getAccountGrpId());
            id.getItemProperty(ACCOUNT_GROUP_ID).setValue(m.getAccountGrpId());
            id.getItemProperty(ACCOUNT_GROUP_NAME).setValue(m.getAccountGrpName() == null ? "" : m.getAccountGrpName());
        }

        return ic;
    }

    public void buttonClick(Button.ClickEvent clickEvent) {


        if (clickEvent.getSource().equals(butSave))
            doSave();
        else if (clickEvent.getSource().equals(butCancel))
            this.getParent().getUI().removeWindow(this);


    }

    private void doDetail() {
        System.out.println("SelectItem.doDetail");
    }

    private void doSave() {
        if (tenmaPanel != null) {
            tenmaPanel.refreshTable(accountGroupModel);
            this.getParent().getUI().removeWindow(this);
        } else {
            tenmaWindow.doRefresh(accountGroupModel);
            this.getParent().getUI().removeWindow(this);
        }
    }

    @Override
    public void doRefresh(Object newData) {
    }

    @Override
    public Object getSelectedObject() {
        return null;
    }

    @Override
    public void setSelectedObject(Object obj) {

    }
}

