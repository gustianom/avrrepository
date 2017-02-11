package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.currency.CurrencyHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.display.component.TenmaDialogInterface;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.web.Items;
import com.tenma.fam.bean.account.AccountGroupHelper;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.fam.bean.bankaccount.BankAccountHelper;
import com.tenma.fam.gui.main.balancesheetreportconfig.Field.BSField;
import com.tenma.fam.gui.main.balancesheetreportconfig.Field.BSSelection;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.*;
import com.tenma.share.gui.display.component.BankAutoField;
import com.tenma.share.gui.display.component.BankSelection;
import com.vaadin.data.Item;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: derry.irmansyah
 * Date: 5/11/13
 * Time: 2:05 PM
 */

//TODO PLEASE MAKE A NEW ONE
public class AccountModify extends TenmaMasterFormModify implements TenmaDialogInterface, BankSelection, BSSelection {
    private final String BANK_ACCOUNT = "bank";
    private final String BANK_ACCOUNT_NAME = "bankName";
    private final Label notifAcc = new Label();
    private AccountModel accountModel;
    private String accountGroupSelected;
    //    private TextField accountGroup;
//    private Button selectAccountGroup;
    private TextField accountName;
    private TextField accountId;
    //    private TextField accountBank;
//    private Button selectAccountBank;
    private int addMode;
    private TextArea accountDesc;
    private NativeSelect accountCurrency;
    private PopupDateField accountStartDate;
    private PopupDateField accountEndDate;
    private OptionGroup accountNormal;
    private BSField bsField;
    private CheckBox accountFixedCurrency;
    private NativeSelect revaluation;
    private boolean accountIdValid;
    private CheckBox isAccountBank;
    private CheckBox setupGroup;
    private CheckBox neverExpired;
    private Label labelRevaluation;
    private Label labelEndDate;
    private Label labelCurrency;
    private Label labelAccountGroup;
    private Label labelBankAccount;
    private Label labelAccountGroupName;
    private Label labelAccountBankName;

    private BankAutoField selectBankAccount;
    private NativeSelect nativeSelectAccountGroup;

    public AccountModify(TenmaPanel container, int modifyMode) throws Exception {
        super(container, modifyMode);
        setTitle("accountCorporate.title");

        labelAccountGroup = new Label(param.getLabel("setup.accountGroup"));
        labelBankAccount = new Label(param.getLabel("default.isbank"));


        labelEndDate = new Label(param.getLabel("default.endDate"));
        labelEndDate.setVisible(false);
        labelRevaluation = new Label(param.getLabel("default.revaluation"));
        labelCurrency = new Label(param.getLabel("default.currency"));
        accountDesc = new TextArea();
        accountName = new TextField();
        accountName.setRequired(true);
        accountId = new TextField();
        accountId.focus();
        accountId.setRequired(true);
        accountId.setImmediate(true);

        nativeSelectAccountGroup = new NativeSelect();
        HashMap paramMap = new HashMap();
        paramMap.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        AccountGroupHelper grpHelper = new AccountGroupHelper();
        List<AccountGroupModel> listGrp = grpHelper.getListRenderer(paramMap, false);
        for (AccountGroupModel grpModel : listGrp) {
            nativeSelectAccountGroup.addItem(grpModel.getAccountGrpId());
            nativeSelectAccountGroup.setItemCaption(grpModel.getAccountGrpId(), grpModel.getAccountGrpName());
        }

        selectBankAccount = new BankAutoField(this);
        selectBankAccount.setCaption(TA.getCurrent().getParams().getLabel("default.bank"));
//        accountBank = new TextField();

        if (modifyMode == ADD_MODE) {
            accountId.addListener(new Listener() {
                                      public void componentEvent(Event event) {
                                          boolean isValid = true;
                                          String acc = accountId.getValue();
                                          accountModel = new AccountModel();
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
                                                  setAccountIdValid(false);
                                                  accountId.setStyleName("borderError");
                                                  notifAcc.setCaption("Account already Axist. try again");
                                                  notifAcc.setVisible(true);
                                              } else {
                                                  setAccountIdValid(true);
                                                  accountId.removeStyleName("borderError");
                                                  notifAcc.setVisible(false);
                                              }
                                          } else {
                                              setAccountIdValid(false);
                                              notifAcc.setCaption("You can't leave this field empty!");
                                              accountId.setStyleName("borderError");
                                              notifAcc.setVisible(true);
                                          }


                                      }
                                  }

            );
        }


        accountCurrency = new NativeSelect();
        accountCurrency.setValue(null);


        accountStartDate = new PopupDateField();
        Locale locale = TA.getCurrent().getLocale();
        Calendar cal = Calendar.getInstance(locale);
        accountStartDate.setValue(cal.getTime());
        accountStartDate.setDateFormat("dd-MMM-yyyy");

        isAccountBank = new CheckBox(param.getLabel("default.isbank"));
        isAccountBank.setImmediate(true);
        isAccountBank.setValue(false);

        setupGroup = new CheckBox(param.getLabel("default.groupsetup"));
        setupGroup.setImmediate(true);
        setupGroup.setValue(false);

        neverExpired = new CheckBox(param.getLabel("default.neverexpired"));
        neverExpired.setImmediate(true);
        neverExpired.setValue(true);

        accountEndDate = new PopupDateField();
        accountEndDate.setVisible(false);


//        accountGroup = new TextField();
//        accountGroup.setEnabled(false);
//        selectAccountGroup = new Button("Select Acount Group", this);
//        selectAccountBank = new Button(getLabel("selec.bank"), this);

        accountNormal = new OptionGroup();

        accountNormal.setImmediate(true);
        accountNormal.addItem(0);
        accountNormal.setItemCaption(0, param.getLabel("default.debit"));
        accountNormal.addItem(1);
        accountNormal.setItemCaption(1, param.getLabel("default.credit"));
        accountNormal.setValue(0);

        labelAccountGroupName = new Label(param.getLabel("default.groupname"));
        labelAccountBankName = new Label(param.getLabel("default.bankname"));

//        accountGroup.setVisible(false);
//        selectAccountGroup.setVisible(false);
        nativeSelectAccountGroup.setVisible(false);
        labelAccountGroup.setVisible(false);
        labelAccountGroupName.setVisible(false);

//        accountBank.setVisible(false);
//        selectAccountBank.setVisible(false);
        selectBankAccount.setVisible(false);
        labelBankAccount.setVisible(false);
        labelAccountBankName.setVisible(false);


        accountFixedCurrency = new CheckBox(param.getLabel("default.fixedcurrency"));
        if (TA.getCurrent().getCommunityModel().isMultipleCurrency()) {
            accountFixedCurrency.setValue(false);
            accountCurrency.setVisible(true);
            labelCurrency.setVisible(true);
            accountCurrency.setValue("");
        } else {
            accountFixedCurrency.setValue(true);
            accountFixedCurrency.setEnabled(false);
            accountCurrency.setEnabled(false);
            labelCurrency.setEnabled(false);
            List list;
            CurrencyHelper hlp = new CurrencyHelper();
            list = hlp.listCurrencyItems();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    Items items = (Items) list.get(i);
                    accountCurrency.addItem(items.getValue());
                    accountCurrency.setItemCaption(items.getValue(), items.getLabel());
                }
            accountCurrency.setValue(TA.getCurrent().getCommunityModel().getBasedCurrency());

        }
        accountFixedCurrency.setImmediate(true);
        final String basedCurrency = TA.getCurrent().getCommunityModel().getBasedCurrency();
        accountFixedCurrency.addListener(new Listener() {

            public void componentEvent(Event event) {
                Object ac = accountFixedCurrency.getValue();
                if (ac.equals(true)) {

                    List list;
                    CurrencyHelper hlp = new CurrencyHelper();
                    list = hlp.listCurrencyItems();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items items = (Items) list.get(i);
                            accountCurrency.addItem(items.getValue());
                            accountCurrency.setItemCaption(items.getValue(), items.getLabel());
                        }
                    accountCurrency.setValue(basedCurrency);
                } else {
                    accountCurrency.setValue(null);
                    accountCurrency.removeAllItems();
                }
            }
        });

        neverExpired.addListener(new Listener() {

            public void componentEvent(Event event) {
                Locale locale = TA.getCurrent().getLocale();
                Calendar cal = Calendar.getInstance(locale);
                Object ac = neverExpired.getValue();
                if (ac.equals(true)) {
                    accountEndDate.setVisible(false);
                    accountEndDate.setValue(null);
                    labelEndDate.setVisible(false);
                } else {
                    accountEndDate.setValue(cal.getTime());
                    accountEndDate.setVisible(true);
                    accountEndDate.setDateFormat("dd-MMM-yyyy");
                    labelEndDate.setVisible(true);

                }
            }
        });

        setupGroup.addListener(new Listener() {

            public void componentEvent(Event event) {
                Object ac = setupGroup.getValue();
                if (ac.equals(false)) {
//                    accountGroup.setVisible(false);
//                    selectAccountGroup.setVisible(false);
                    nativeSelectAccountGroup.setVisible(false);
                    labelAccountGroup.setVisible(false);
                    labelAccountGroupName.setVisible(false);
                } else {
//                    accountGroup.setVisible(true);
//                    selectAccountGroup.setVisible(true);
                    nativeSelectAccountGroup.setVisible(true);
                    labelAccountGroup.setVisible(true);
                    labelAccountGroupName.setVisible(true);
                }
            }
        });

        isAccountBank.addListener(new Listener() {

            public void componentEvent(Event event) {
                Object ac = isAccountBank.getValue();
                if (ac.equals(false)) {
                    labelBankAccount.setVisible(false);
                    selectBankAccount.setVisible(false);
                    labelAccountBankName.setVisible(false);
                } else {
                    labelBankAccount.setVisible(true);
                    selectBankAccount.setVisible(true);
                    labelAccountBankName.setVisible(true);
                }
            }
        });


        revaluation = new NativeSelect();
        revaluation.setImmediate(true);

        revaluation.addItem(0);
        revaluation.setItemCaption(0, "yes");
        revaluation.addItem(1);
        revaluation.setItemCaption(1, "no");
        revaluation.setValue(0);

        VerticalLayout grd = new VerticalLayout();
        grd.setMargin(true);
        accountId.setCaption(getLabel("account.id"));
        HorizontalLayout hix = new HorizontalLayout();
        hix.addComponent(accountId);
        hix.addComponent(notifAcc);
        grd.addComponent(hix);
        accountId.setStyleName("textField");

        accountName.setCaption(getLabel("account.name"));
        grd.addComponent(accountName);
        accountName.setStyleName("textField");

        accountDesc.setCaption(getLabel("default.description"));
        grd.addComponent(accountDesc);
        accountDesc.setStyleName("areaField");

//        grd.addComponent(new Label(getLabel("account.accountType")));
//        grd.addComponent(accountType);

        grd.addComponent(labelAccountGroup);
        grd.addComponent(setupGroup);
        grd.addComponent(labelAccountGroupName);

        grd.addComponent(nativeSelectAccountGroup);

        grd.addComponent(labelBankAccount);
        grd.addComponent(isAccountBank);
        grd.addComponent(labelAccountBankName);

        grd.addComponent(selectBankAccount);

        accountNormal.setCaption(getLabel("account.accountNormal"));
        grd.addComponent(accountNormal);
        grd.addComponent(accountFixedCurrency);
        VerticalLayout vb = new VerticalLayout();
        vb.addComponent(labelCurrency);
        vb.addComponent(accountCurrency);
        grd.addComponent(vb);


        accountStartDate.setCaption(getLabel("account.accountStartDate"));
        grd.addComponent(accountStartDate);
        grd.addComponent(neverExpired);

        accountEndDate.setCaption(labelEndDate.getValue());
        grd.addComponent(accountEndDate);
        revaluation.setCaption(labelRevaluation.getValue());
        grd.addComponent(revaluation);

        bsField = new BSField(this);
        bsField.setCaption("Balance Sheet");
        grd.addComponent(bsField);
        grd.setSpacing(true);
        getPersonForm().addComponent(grd);


        if (modifyMode == UPDATE_MODE) {
            accountModel = (AccountModel) ((TenmaPanel) container).getSelectedObject();
            AccountHelper helper1 = new AccountHelper(TA.getCurrent().getLocale());
            AccountModel m = accountModel;
            System.out.println("accountModel.getCommunityId() = " + accountModel.getCommunityId());
            m = helper1.getAccountDetail(m);
            accountName.setValue(m.getAccountName());
            accountDesc.setValue(m.getAccountDesc());
            nativeSelectAccountGroup.setValue(m.getAccountGroupName());
            setAccountGroupSelected(m.getAccountGroupId());
            accountNormal.setValue(m.getAccountNormal());
            accountFixedCurrency.setValue(m.isAccountFixedCurrency());
            accountCurrency.setValue(m.getAccountCurrency());
            accountStartDate.setValue(m.getAccountValidFrom());
            accountEndDate.setValue(m.getAccountValidTo());
            revaluation.setValue(m.getRevaluation());
            accountId.setValue(m.getAccountId());
            accountId.setEnabled(false);
            setAccountIdValid(true);
            System.out.println("accountEndDate = " + m.getAccountValidTo());
            if (m.getAccountValidTo() != null) {
                neverExpired.setValue(false);
            } else {
                neverExpired.setValue(true);

            }
            if (m.getRevaluation() == null) {
                revaluation.setVisible(false);
                revaluation.setValue(null);
                labelRevaluation.setVisible(false);
            }
            if (m.getAccountGroupId() != null) {
                if (!m.getAccountGroupId().isEmpty()) {
                    setupGroup.setValue(true);
                    nativeSelectAccountGroup.setValue(m.getAccountGroupId());
                }
            }

            if (m.getBankAccount() != null) {
                if (!m.getBankAccount().isEmpty()) {
                    isAccountBank.setValue(true);
                    selectBankAccount.setBank(m.getBankAccount());
                }
            }
        }

        registerFooterButton(getButSave());
        registerFooterButton(getButCancel());

    }

    public boolean isAccountIdValid() {
        return accountIdValid;
    }

    public void setAccountIdValid(boolean accountIdValid) {
        this.accountIdValid = accountIdValid;
    }

    public String getAccountGroupSelected() {
        return accountGroupSelected;
    }

    public void setAccountGroupSelected(String accountGroupSelected) {
        this.accountGroupSelected = accountGroupSelected;
    }

    private void setAccountGroupValue(AccountGroupModel accountGroupModel) {
        setAccountGroupSelected(accountGroupModel.getAccountGrpId());
//        accountGroup.setValue(accountGroupModel.getAccountGrpName());
        if (accountGroupModel.getAccountGrpType() != 0) {
            revaluation.setVisible(false);
            revaluation.setValue(null);
            labelRevaluation.setVisible(false);
        } else {
            revaluation.setVisible(true);
            revaluation.setValue(0);
            labelRevaluation.setVisible(true);
        }
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);

    }

    private void doSelectAccountGroup() {
        Integer type = 0;
        SelectAccountGroup dialog = new SelectAccountGroup(this, type);
        UI.getCurrent().getUI().addWindow(dialog);
    }

    private void doSelectAccountBank() {
        Integer type = 0;
        SelectAccountGroup dialog = new SelectAccountGroup(this, type);
        UI.getCurrent().getUI().addWindow(dialog);
    }

    public void doCancel() {
        doBack2List();
    }


    public void doSave() {
        String lc = getLogger().openLog();
        if (accountName.getValue().length() == 0 || !isAccountIdValid()) {
            Notification.show("You can't Save this field empty Or Format Failed", Notification.Type.TRAY_NOTIFICATION);
        } else {
            AccountHelper helper = new AccountHelper(TA.getCurrent().getLocale());
            AccountModel m = new AccountModel();
            m.setAccountId(accountId.getValue());
            m.setAccountName(accountName.getValue());
            m.setAccountDesc(accountDesc.getValue());
            m.setAccountNormal((Integer) accountNormal.getValue());
            m.setAccountCurrency((String) accountCurrency.getValue());
            m.setAccountFixedCurrency((Boolean) accountFixedCurrency.getValue());
            m.setAccountValidFrom(accountStartDate.getValue());
            m.setAccountValidTo(accountEndDate.getValue());
            m.setRevaluation((Integer) revaluation.getValue());
            m.setBalanceSheet((Integer) bsField.getValue());
            if (isAccountBank.getValue()) {
                m.setBankAccount((String) selectBankAccount.getBank().getBankId());
            }
            if (setupGroup.getValue()) {
                m.setAccountGroupId((String) nativeSelectAccountGroup.getValue());
            }
            m.setStatus(4);
//            m.setAccountGroupId(getAccountGroupSelected());
            m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());


//            m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            setAuditTrail(m);
            int res = -1;
            try {
                if (ADD_MODE == getModifyMode())
                    res = helper.insertNewAccountCorporate(m);
                else
                    res = helper.updateAccount(m);
                if (res != 0) {
                    if (ADD_MODE == getModifyMode()) {
                        getLogger().log(lc, TenmaLog.LOG_FOR_ADD, "ADD Account Corporate| AccountId = " + m.getAccountId());

                    } else {
                        getLogger().log(lc, TenmaLog.LOG_FOR_UPDATE, "UPDATE Account Corporate | AccountID = " + m.getAccountId());
                    }
                }
                AccountList list = new AccountList();
                TA.getCurrent().updateWorkingArea(list);
            } catch (Exception e) {
                Notification.show("Saving Account Failed Process");
                e.printStackTrace();
            }

        }
    }

    private void doBack2List() {
        AccountList list = (AccountList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);

    }

    @Override
    public void refreshTable(Object data) {
        AccountGroupModel model = (AccountGroupModel) data;
        setAccountGroupValue(model);
    }

    public int getAddMode() {
        return addMode;
    }

    public void setAddMode(int addMode) {
        this.addMode = addMode;
    }

    @Override
    public TenmaTableContainer container() {
        TenmaTableContainer container = new TenmaTableContainer();
        container.addContainerProperty(BANK_ACCOUNT, String.class, "");
        container.addContainerProperty(BANK_ACCOUNT_NAME, String.class, "");
        BankAccountHelper bankHelper = new BankAccountHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List<BankAccountModel> list = bankHelper.getListRenderer(map, false);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("AccountModify.container - " + list.size() + " -" + TA.getCurrent().getCommunityModel().getCommunityId() + "|");
        for (BankAccountModel model : list) {
            Item id = container.addItem(model.getAcccountNumber());
            container.getContainerProperty(model.getAcccountNumber(), BANK_ACCOUNT).setValue(model.getAcccountNumber());
            container.getContainerProperty(model.getAcccountNumber(), BANK_ACCOUNT_NAME).setValue(model.getBankName());
        }
        return container;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] stringHeader() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBankModel(BankManagementModel m) {

    }

    @Override
    public void setBalanceSheetModel(BalanceSheetModel model) {

    }


}