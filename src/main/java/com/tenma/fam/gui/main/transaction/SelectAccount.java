package com.tenma.fam.gui.main.transaction;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.fam.gui.main.template.TemplateDetailModify;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
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
 * Date: 4/29/13
 * Time: 2:57 PM
 */
public class SelectAccount extends TenmaWindow implements Button.ClickListener {
    private final String ACCOUNT_ID = "accountId";
    private final String ACCOUNT_NAMES = "accountName";

    private Button butCancel;
    private Button butSave;
    private boolean typeMode;
    private Table tableAccount = new Table();


    private TemplateDetailModify templateDetailModify;
    private AccountModel accountModel;
    private TenmaTableContainer tenmaTableContainer;

    public SelectAccount() { //}, JournalDetailDialog modify) {
        super();
        this.typeMode = true;
        generateUI();

        dataCollection();
    }

    public SelectAccount(TemplateDetailModify modify) {
        super();
        this.templateDetailModify = modify;
        this.typeMode = false;
        generateUI();

        dataCollection();
    }

    public boolean isTypeMode() {
        return typeMode;
    }

    public void setTypeMode(boolean typeMode) {
        this.typeMode = typeMode;
    }

    private void generateUI() {
        butCancel = new Button(getLabel(Constants.LABEL_CANCEL), this);
        butSave = new Button(getLabel("templateDetail.selectAccount"), this);

        HorizontalLayout hr = new HorizontalLayout();
        hr.addComponent(butSave);
        hr.addComponent(butCancel);
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.addComponent(hr);
        ly.addComponent(tableAccount);
        setContent(ly);


    }


    private void dataCollection() {
        TenmaTableContainer addressBookData = collectData();
        tableAccount.setContainerDataSource(addressBookData);
        tableAccount.setColumnHeaders(new String[]{
                        getLabel("account.id"),
                        getLabel("account.name")
                }
        );
        tableAccount.setColumnCollapsingAllowed(true);
        tableAccount.setColumnCollapsed(ACCOUNT_ID, true);
        tableAccount.setSelectable(true);
        tableAccount.setImmediate(true);
        tableAccount.setMultiSelect(false);


        tableAccount.addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                System.out.println();
                System.out.println();
                System.out.println("value - " + valueChangeEvent.getProperty().getValue());
                Item item = tableAccount.getItem(valueChangeEvent.getProperty().getValue());
                accountModel = new AccountModel();
                System.out.println("item = " + item);
                accountModel.setAccountId((String) item.getItemProperty(ACCOUNT_ID).getValue());
                accountModel.setAccountName((String) item.getItemProperty(ACCOUNT_NAMES).getValue());


            }
        });


    }

    private TenmaTableContainer collectData() {
        AccountHelper help = new AccountHelper(TA.getCurrent().getLocale());
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        p.put("status", 4);
        List ls = help.getListRenderer(p, false);
        String lc = getLogger().openLog();
        getLogger().log(lc, TenmaLog.LOG_FOR_VIEW, "Select Account Dialog CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());

        tenmaTableContainer = new TenmaTableContainer();
        TenmaTableContainer ic = tenmaTableContainer;


        ic.addContainerProperty(ACCOUNT_ID, String.class, "");
        ic.addContainerProperty(ACCOUNT_NAMES, String.class, "");


        for (int i = 0; i < ls.size(); i++) {
            Object id = ic.addItem();
            AccountModel m = (AccountModel) ls.get(i);
            ic.getContainerProperty(id, ACCOUNT_ID).setValue(m.getAccountId());
            ic.getContainerProperty(id, ACCOUNT_NAMES).setValue(m.getAccountName());


        }

        return ic;
    }


    public void buttonClick(Button.ClickEvent clickEvent) {


        if (clickEvent.getSource().equals(butSave))
            doSave();
        else if (clickEvent.getSource().equals(butCancel))
            this.getParent().getUI().removeWindow(this);


    }


    private void doSave() {
        System.out.println("SelectResponsibleList.doSave");
        String accountId = accountModel.getAccountId();
        String accountName = accountModel.getAccountName();
        System.out.println("accountId = " + accountId);
        if (isTypeMode()) {
//            modify.setPayFromValue(accountId, accountName);
        } else {
            templateDetailModify.setAccountValue(accountId, accountName);
        }
        this.getParent().getUI().removeWindow(this);


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

