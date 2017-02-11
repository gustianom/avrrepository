package com.tenma.fam.gui.main.template;

/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 3/27/14
 * Time: 4:44 PM
 *
 * User: derry.irmansyah
 * Date: 3/2/13
 * Time: 2:41 PM
 *
 * */

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.currency.CurrencyHelper;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.gui.display.component.COAField;
import com.tenma.common.util.Constants;
import com.tenma.common.util.exrate.TenmaExchangeRate;
import com.tenma.common.util.web.Items;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.fam.gui.main.account.AccountSelection;
import com.tenma.model.fam.AccountModel;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.text.DecimalFormat;
import java.util.List;

public class TemplateDetailDialog extends TenmaWindow implements Button.ClickListener, Property.ValueChangeListener, AccountSelection {
    private final Label notifAcc = new Label();
    private Button butSave;
    private Button butCancel;
    private Button butSaveNew;
    //    private Button selectAccount;
//    private TextField accountIdTextField;
    private COAField coaField;
    private TextField accountNameTextField;
    private TextArea trxDesc;
    private NativeSelect trxCurr;
    private NativeSelect balance;
    private TransactionTemplateDetailModel templateDetailModel;
    private TransactionTemplateModify parent;
    private DecimalFormat df1;
    private DecimalFormat df2;
    private boolean updateDetail;
    private boolean updateValueChange;
    private AccountModel selectedAccount;


    public TemplateDetailDialog(TransactionTemplateModify parent, TransactionTemplateDetailModel detailModel) {
        super();
        df1 = (DecimalFormat) DecimalFormat.getInstance(TA.getCurrent().getLocale());
        df2 = (DecimalFormat) DecimalFormat.getInstance(TA.getCurrent().getLocale());
        df2.applyPattern("#,##0.000");
        df1.applyPattern("#,##0.00;(#,##0.00)");
        setCaption("Update  " + param.getLabel("journalDetail.title"));
        this.parent = parent;
        this.templateDetailModel = detailModel;
        preparingUI();
        String trxCur = (String) trxCurr.getValue();
        if (trxCur == null || trxCur == "") trxCur = TA.getCurrent().getCommunityModel().getBasedCurrency();
        double rate = TenmaExchangeRate.getTenmaRate().getRate(TA.getCurrent().getCommunityModel().getBasedCurrency(), trxCur);
        defineUpdateDate(rate);
    }

    @Override
    public void setSelectedAccount(AccountModel model) {
        selectedAccount = model;
        accountNameTextField.setValue(model.getAccountName());
        if (model.isAccountFixedCurrency()) {
            trxCurr.setValue(model.getAccountCurrency());
            trxCurr.setEnabled(false);
        }
    }

    private void defineUpdateDate(double rate) {
        if (templateDetailModel != null) {
            updateDetail = true;
            updateValueChange = true;

            trxCurr.setValue(templateDetailModel.getTemplateDetailCurr());
            trxCurr.setEnabled(false);
            coaField.setAccount(templateDetailModel.getAccountId());
            accountNameTextField.setValue(templateDetailModel.getAccountName());
            trxDesc.setValue(templateDetailModel.getTemplateDetailDesc());
            balance.setValue(templateDetailModel.getAccountType());
        } else {
            templateDetailModel = new TransactionTemplateDetailModel();
//            accountIdTextField.focus();
            coaField.focus();
            updateDetail = false;
            updateValueChange = false;
        }
    }

    public void setPayFromValue(String accId, String accName) {
        setSelectedObject(accId);
//        accountIdTextField.setValue(accId);
        coaField.setAccount(accId);
        accountNameTextField.setValue(accName);
        if (TA.getCurrent().getCommunityModel().isMultipleCurrency()) {
            AccountModel accountModel = new AccountModel();
            AccountHelper accountHelper = new AccountHelper(TA.getCurrent().getLocale());
            accountModel.setAccountId(accId);
            accountModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            accountModel = accountHelper.getAccountDetail(accountModel);
            if (accountModel.isAccountFixedCurrency()) {
                trxCurr.setValue(accountModel.getAccountCurrency());
                trxCurr.setEnabled(false);
            } else {
                trxCurr.setValue(TA.getCurrent().getCommunityModel().getBasedCurrency());
                trxCurr.setEnabled(true);
            }
        }
    }


    private void preparingUI() {
        butCancel = new Button(param.getLabel("journalDetail.cancelDetail"), this);
        butSave = new Button(param.getLabel("journalDetail.saveDetail"), this);
        butCancel.setIcon(new ThemeResource(Constants.BACK_ICON));
        butSave.setIcon(new ThemeResource(Constants.SAVE_ICON));
        butSaveNew = new Button(param.getLabel("journalDetail.saveNewDetail"), this);
        butSaveNew.setIcon(new ThemeResource(Constants.SAVE_ICON));

//        selectAccount = new Button("", this);
//        selectAccount.setIcon(new ThemeResource("layouts/images/16/187.png"));
//        accountIdTextField = new TextField();
        coaField = new COAField(this);
        accountNameTextField = new TextField();
        accountNameTextField.setEnabled(false);
        coaField.focus();
        /*
        accountIdTextField.setImmediate(true);
        accountIdTextField.addListener(new Listener() {
            public void componentEvent(Event event) {
                boolean isValid = true;
                String acc = accountIdTextField.getValue();
                AccountModel accountModel = new AccountModel();
                accountModel.setAccountId(acc);
                accountModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                accountModel.setStatus(4);
                if (acc.trim().length() != 0) {
                    AccountHelper helper = new AccountHelper(TA.getCurrent().getLocale());
                    accountModel = helper.getAccountDetail(accountModel);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    if (accountModel != null) {

                        notifAcc.setVisible(false);
                        accountNameTextField.setValue(accountModel.getAccountName());
                        accountIdTextField.removeStyleName("borderError");
                        CommunityModel comModel = new CommunityModel();
                        CommunityHelper communityHelper = new CommunityHelper();
                        comModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                        comModel = communityHelper.getCommunityDetail(comModel);
                        if (comModel.isMultipleCurrency()) {
                            if (accountModel.isAccountFixedCurrency()) {
                                trxCurr.setValue(accountModel.getAccountCurrency());
                                trxCurr.setEnabled(false);
                            } else {
                                trxCurr.setValue(comModel.getBasedCurrency());
                                trxCurr.setEnabled(true);
                            }
                        }
                        trxDesc.focus();
                    } else {

                        notifAcc.setCaption("Account ID not found");
                        notifAcc.setVisible(true);
                        accountIdTextField.setStyleName("borderError");
                        accountNameTextField.setValue("");
                        selectAccount.focus();
                    }
                } else {

                    notifAcc.setCaption("You can't leave this field empty!");
                    accountIdTextField.setStyleName("borderError");
                    notifAcc.setVisible(true);
                }


            }
        });
*/

        CustomLayout layout = new CustomLayout("templateDialog");

        HorizontalLayout hx = new HorizontalLayout();
        Label label9 = new Label();
        label9.setWidth("250px");
        hx.addComponent(label9);
        hx.addComponent(butSaveNew);
        hx.addComponent(butSave);
        hx.addComponent(butCancel);
        trxDesc = new TextArea();
        trxCurr = new NativeSelect();
        List list;
        CurrencyHelper hlp = new CurrencyHelper();
        CommunityModel communityModel = new CommunityModel();
        CommunityHelper communityHelper = new CommunityHelper();
        communityModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        communityModel = communityHelper.getCommunityDetail(communityModel);
        if (communityModel.isMultipleCurrency()) {

            list = hlp.listCurrencyItems();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    Items items = (Items) list.get(i);
                    trxCurr.addItem(items.getValue());
                    trxCurr.setItemCaption(items.getValue(), items.getLabel());
                }
            trxCurr.setValue(communityModel.getBasedCurrency());
        } else {
            list = hlp.listCurrencyItems();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    Items items = (Items) list.get(i);
                    trxCurr.addItem(items.getValue());
                    trxCurr.setItemCaption(items.getValue(), items.getLabel());
                }
            trxCurr.setValue(communityModel.getBasedCurrency());
            trxCurr.setEnabled(false);
        }
        trxCurr.setNullSelectionAllowed(false);
        balance = new NativeSelect();
        balance.addItem(0);
        balance.addItem(1);
        balance.setItemCaption(0, "D");
        balance.setItemCaption(1, "C");
        balance.setValue(0);

        VerticalLayout grd1 = new VerticalLayout();
        grd1.addComponent(new Label(param.getLabel("account.id")));
        HorizontalLayout ht = new HorizontalLayout();
//        VerticalLayout anak = new VerticalLayout();
//        anak.addComponent(accountIdTextField);
//        anak.addComponzent(accountNameTextField);
//        anak.addComponent(notifAcc);
        ht.addComponent(coaField);
        ht.addComponent(accountNameTextField);
        grd1.addComponent(ht);

        VerticalLayout grd2 = new VerticalLayout();
        grd2.addComponent(new Label(getLabel("default.description")));
        grd2.addComponent(trxDesc);

        VerticalLayout grd4 = new VerticalLayout();
        grd4.addComponent(new Label(getLabel("default.balance")));
        grd4.addComponent(balance);

        VerticalLayout grd3 = new VerticalLayout();
        grd3.addComponent(new Label(param.getLabel("default.currency")));
        grd3.addComponent(trxCurr);


        HorizontalLayout hr1 = new HorizontalLayout();
        hr1.addComponent(grd1);
        Label l1 = new Label("");
        l1.setWidth("20px");
        hr1.addComponent(l1);
        hr1.addComponent(grd2);
        Label l2 = new Label("");
        l2.setWidth("10px");
        hr1.addComponent(l2);
        hr1.addComponent(grd4);
        Label l4 = new Label("");
        l4.setWidth("10px");
        hr1.addComponent(l4);
        hr1.addComponent(grd3);
        Label l3 = new Label("");
        l3.setWidth("10px");
        hr1.addComponent(l3);
//        hr1.addComponent(grd5);

        VerticalLayout vr = new VerticalLayout();
        vr.addComponent(hr1);
        Label l8 = new Label("");
        l8.setHeight("30px");
        vr.addComponent(l8);
        vr.addComponent(hx);
        layout.addComponent(vr, "contentModify");
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.addComponent(layout);
        setContent(ly);
        this.addAction(new Button.ClickShortcut(butSaveNew, ShortcutAction.KeyCode.F3));
        this.addAction(new Button.ClickShortcut(butSave, ShortcutAction.KeyCode.F4));
        this.addAction(new Button.ClickShortcut(butCancel, ShortcutAction.KeyCode.F5));
        trxCurr.setImmediate(true);
        trxCurr.addValueChangeListener(this);
        butSave.addClickListener(this);
        butSaveNew.addClickListener(this);
        butCancel.addClickListener(this);

    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getSource().equals(butSave))
            try {
                doSave();
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if (clickEvent.getSource().equals(butSaveNew)) {
            try {
                doSave();
//                parent.renew();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (clickEvent.getButton().equals(butCancel)) {
            close();
        }
//        else if (clickEvent.getButton().equals(selectAccount)) {
//            doSelectAccount();
//        }

    }


    private void doSelectAccount() {
//        SelectAccount dialog = new SelectAccount( this);
//        UI.getCurrent().getUI().addWindow(dialog);
    }


    private void doSave() throws Exception {
        if (!isAccountIdValid()) {
            Notification.show("Account ID not valid");
        } else {
            TransactionTemplateDetailModel m = new TransactionTemplateDetailModel();
            m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
//            m.setAccountId(accountIdTextField.getValue());
            m.setAccountId(coaField.getAccount().getAccountId());
            m.setAccountName(accountNameTextField.getValue());
            m.setTemplateDetailDesc(trxDesc.getValue());
            m.setAccountType((Integer) balance.getValue());
            m.setTemplateDetailCurr((String) trxCurr.getValue());

            if (!updateDetail) {
                m.setDirty(false);
                m.setTemplateDetailId(m.getTemplateId() + (parent.getNewJournalDetailId() + 1));
                m.setOrderNumber(parent.getNewJournalDetailId() + 1);
                parent.addNewDetailJournal(m);
            } else {
                m.setDirty(true);
                m.setOrderNumber(templateDetailModel.getOrderNumber());
                m.setTemplateDetailId(templateDetailModel.getTemplateDetailId());
                m.setTemplateId(templateDetailModel.getTemplateId());
                parent.addNewDetailJournal(m);
            }

            close();
        }
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {

        String fromCurrency = event.getProperty().getValue().toString();
        String basedCurrency = TA.getCurrent().getCommunityModel().getBasedCurrency();
        double rate = 1;
    }

    private boolean isAccountIdValid() {
        return true;
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

