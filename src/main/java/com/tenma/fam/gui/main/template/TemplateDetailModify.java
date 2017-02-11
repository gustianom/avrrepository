/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */
package com.tenma.fam.gui.main.template;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.fam.gui.main.transaction.SelectAccount;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;


public class TemplateDetailModify extends TenmaWindow implements Button.ClickListener {
    private Button butSave;
    private Button butCancel;
    private Button selectAccount;
    private TextField accountIdTextField;
    private String accountSelected;
    private TransactionTemplateModify parent;
    private OptionGroup accountType;
    private boolean detailMode;
    private TransactionTemplateDetailModel model;
    private String headerNumber;


    private Panel panel = new Panel();


    public TemplateDetailModify(TransactionTemplateModify parent) {

        this.parent = parent;
        this.model = (TransactionTemplateDetailModel) parent.getSelectedObject();
//        TODO-TMP-URGNT
//        setHeaderNumber(((TransactionTemplateModel) parent.getTempSelectedObject1st()).getTemplateId());
        preparingUI();


    }


    public void setAccountValue(String accId, String accName) {

        System.out.println("userId = " + accId);
        setAccountSelected(accId);
        accountIdTextField.setValue(accName);

    }


    private void preparingUI() {
        butCancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
        butSave = new Button(param.getLabel(Constants.LABEL_SAVE), this);
        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butSave.setIcon(new ThemeResource(Constants.SAVE_ICON));
        selectAccount = new Button(param.getLabel("templateDetail.selectAccount"), this);
        selectAccount.setIcon(new ThemeResource("layouts/images/16/187.png"));
        accountIdTextField = new TextField();
        accountIdTextField.setEnabled(false);

        CustomLayout layout = new CustomLayout("templateModify");
        layout.addComponent(new Label("ADD/UPDATE " + param.getLabel("templateDetail.title")), "contentTitle");

        HorizontalLayout hx = new HorizontalLayout();
        hx.addComponent(butSave);
        hx.addComponent(butCancel);


        accountType = new OptionGroup();
        accountType.addItem(0);
        accountType.addItem(1);
        accountType.setItemCaption(0, "D");
        accountType.setItemCaption(1, "C");
        accountType.setValue(0);


        //  FOR UPDATE

        if (!isDetailMode()) {

            setAccountSelected(model.getAccountId());
            accountIdTextField.setValue(model.getAccountName());

            accountType.setValue(model.getAccountType());


        }

        VerticalLayout grd = new VerticalLayout();
        grd.addComponent(new Label(getLabel("templateDetail.accountId")));
        HorizontalLayout ht = new HorizontalLayout();
        ht.addComponent(accountIdTextField);
        ht.addComponent(selectAccount);
        grd.addComponent(ht);
        grd.addComponent(new Label(getLabel("templateDetail.accountType")));
        grd.addComponent(accountType);

        layout.addComponent(grd, "contentModify");
        layout.addComponent(hx, "contentButton");
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.addComponent(layout);

        setContent(ly);


    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        try {
            if (clickEvent.getSource().equals(butSave))
                doSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clickEvent.getButton().equals(butCancel))
            this.getParent().getUI().removeWindow(this);
        else if (clickEvent.getButton().equals(selectAccount))
            doSelectAccount();

    }


    private void doSelectAccount() {
        SelectAccount dialog = new SelectAccount(this);
        UI.getCurrent().getUI().addWindow(dialog);
    }


    private TransactionTemplateDetailModel doSave() throws Exception {
        TransactionTemplateDetailModel m = new TransactionTemplateDetailModel();
        m.setAccountId(getAccountSelected());
        m.setAccountName(accountIdTextField.getValue());
        m.setAccountType((Integer) accountType.getValue());
        m.setTemplateId(getHeaderNumber());
        m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        return m;
    }

    public String getAccountSelected() {
        return accountSelected;
    }

    public void setAccountSelected(String accountSelected) {
        this.accountSelected = accountSelected;
    }


    public TransactionTemplateDetailModel getTransactionTemplateDetailModel() {
        return model;
    }

    public void setTransactionTemplateDetailModel(TransactionTemplateDetailModel transactionTemplateDetailModel) {
        this.model = transactionTemplateDetailModel;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    public String getHeaderNumber() {
        return headerNumber;
    }

    public void setHeaderNumber(String headerNumber) {
        this.headerNumber = headerNumber;
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
