/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/21/14
 * Time: 4:14 PM
 */

package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;

public class AccountListLookup extends Window implements Button.ClickListener {

    private final String ACCOUNT_NAME = "accountName";
    private final String ACCOUNT_ID = "collectionId";
    private final TenmaPanel parentComponent;
    private final TenmaMasterList list;
    private TenmaTableContainer container;
    private AccountModel selectedAccountModel;

    public AccountListLookup(TenmaPanel parentComponent) {
        this.parentComponent = parentComponent;
        setClosable(true);
        setResizable(false);
        this.center();
        this.setModal(true);


        list = createAccountList();
        list.setTitle("accountCorporate.title");
        list.getAddButton().setVisible(false);
        list.getPrintButton().setVisible(false);
        list.getUpdateButton().setVisible(false);
        list.getDeleteButton().setVisible(false);
        Button btnSelect = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_SELECT), this);
        Button btnCancel = new Button(TA.getCurrent().getParams().getLabel(Constants.LABEL_CANCEL), this);

        btnSelect.setIcon(new ThemeResource(Constants.SELECT_ICON));
        btnCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        btnCancel.setId("CANCEL_LOOKUP");
        btnSelect.setId("SELECT_LOOKUP");
        list.getButtonArea().removeAllComponents();
        list.getButtonArea().addComponent(btnSelect);
        list.getButtonArea().addComponent(btnCancel);
        list.getTableProperty().setWidth("98%");
        list.setWidth("100%");

        VerticalLayout vMain = new VerticalLayout();
        HorizontalLayout hButton = new HorizontalLayout();
        vMain.setMargin(true);
        hButton.setMargin(true);
        hButton.addComponent(btnSelect);
        hButton.addComponent(btnCancel);
        hButton.setStyleName("templateList");
        vMain.addComponent(list);
        vMain.addComponent(hButton);
        setContent(vMain);

    }

    private TenmaMasterList createAccountList() {
        TenmaMasterList list = new TenmaMasterList() {
            @Override
            public void doModify(TenmaPanel parentContainer, int update_mode) {

            }

            @Override
            public int doDeletion() throws Exception {
                return 0;
            }

            @Override
            public void refreshTable() {

            }

            @Override
            public void executingDataPreparation() {
                container = new TenmaTableContainer();

                container.addContainerProperty(ACCOUNT_ID, String.class, "");
                container.addContainerProperty(ACCOUNT_NAME, String.class, "");

                getTableProperty().setContainerDataSource(container);
                getTableProperty().commit();

                getTableProperty().setColumnHeaders(
                        getLabel("account.id"),
                        getLabel("account.name"));
                getTableProperty().setColumnCollapsingAllowed(true);
                getTableProperty().setColumnCollapsible(ACCOUNT_ID, false);
                getTableProperty().setColumnCollapsible(ACCOUNT_NAME, false);


                getTableProperty().setSelectable(true);
                getTableProperty().setImmediate(true);
                getTableProperty().setMultiSelect(false);
                getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
                    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {

                        Item item = getTableProperty().getItem(valueChangeEvent.getProperty().getValue());

                        if (item != null) {
                            String id = (String) valueChangeEvent.getProperty().getValue();
                            String[] arr = id.split("\\|");

                            AccountModel ac = new AccountModel();
                            ac.setAccountId(arr[0]);
                            ac.setCommunityId(arr[1]);
                            setSelectedObject(ac);
                        } else
                            System.out.println("AccountListLookup.valueChange empty");

                    }

                });
                doRefreshData();
            }

            @Override
            public void doSearch() {
                setPageIndex(0);
                doRefreshData();
            }

            private void doRefreshData() {
                container.removeAllItems();
                container = collectData();
            }

            private TenmaTableContainer collectData() {
                HashMap p = new HashMap();

                p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
                p.put("isBankAccount", "yes");
                AccountHelper help = new AccountHelper();

                List ls = loadPagingData(p, "accountId", Constants.SORTING_TYPE.ASCENDING, "AccountCorporate", help);

                for (int i = 0; i < ls.size(); i++) {
                    AccountModel m = (AccountModel) ls.get(i);
                    String idCntx = m.getAccountId() + "|" + m.getCommunityId();
                    Item id = container.addItem(idCntx);
                    id.getItemProperty(ACCOUNT_ID).setValue(m.getAccountId());
                    id.getItemProperty(ACCOUNT_NAME).setValue(m.getAccountName());
                }

                return container;
            }
        };
        return list;
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {
        String id = event.getButton().getId();
        if ("SELECT_LOOKUP".equals(id))
            doSelectLookup();
        else
            doCancelLookup();

    }

    private void doCancelLookup() {
        UI.getCurrent().removeWindow(this);
    }

    private void doSelectLookup() {
        AccountModel model = (AccountModel) list.getSelectedObject();

        AccountHelper helper = new AccountHelper();
        model = helper.getAccountDetail(model);
        parentComponent.refreshTable(model);
        UI.getCurrent().removeWindow(this);
    }
}

