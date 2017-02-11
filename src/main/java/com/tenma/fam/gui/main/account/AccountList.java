/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/21/14
 * Time: 4:14 PM
 */

package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountHelper;
import com.tenma.fam.bean.saldoaccount.SaldoAccountReportHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountModel;
import com.tenma.model.fam.SaldoAccountReportModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import java.util.*;

public class AccountList extends TenmaMasterList {
    private static final String[] dbColumn = {"accountId", "accountId", "accountName", "accountCurrency", "accountValidFrom", "accountValidTo", "accountGroupId"};
    private final String ACCOUNT_DETAIL = "accountDetail";
    private final String ACCOUNT_NAME = "accountName";
    private final String ACCOUNT_ID = "collectionId";
    private final String ACCOUNT_GROUP = "accountGroupId";
    private final String ACCOUNT_VALID_FROM = "accountValidFrom";
    private final String ACCOUNT_VALID_TO = "accountValidTo";
    private final String ACCOUNT_CURRENCY = "accountCurrency";

    private TenmaTableContainer container;
    private Button viewReport;


    public AccountList() {
        super(dbColumn);
        setTitle("accountCorporate.title");
        getAddButton().setEnabled(true);
        getUpdateButton().setEnabled(true);
        getDeleteButton().setEnabled(true);
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            AccountModify modify = new AccountModify(this, update_mode);
            TA.getCurrent().updateWorkingArea(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void configureToolbarAfter() {
        viewReport = new Button(param.getLabel(Constants.LABEL_PRINT), this);
        viewReport.setIcon(new ThemeResource(Constants.PRINT_ICON));
        getButtonArea().addComponent(viewReport);
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getSource().equals(viewReport)) {
            doPrint();
        } else if (clickEvent.getButton() != null) {
            Button btn = clickEvent.getButton();
            AccountModel m = (AccountModel) btn.getData();
            if (m != null) {
                showDetail(m);
            }
        }
    }

    private void showDetail(AccountModel m) {
        AccountDetailTransaction detailDialog = new AccountDetailTransaction(m);
        detailDialog.center();
        getUI().addWindow(detailDialog);
    }


    public void doPrint() {
        SaldoAccountReportHelper helper = new SaldoAccountReportHelper();
        String communityId = TA.getCurrent().getCommunityModel().getCommunityId();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, communityId);
        List<SaldoAccountReportModel> listMaster = helper.getSaldoAccountGroupReportList(map);
        for (SaldoAccountReportModel model : listMaster) {
            map.put("accountGroupId", model.getAccountGroupId());
            List<SaldoAccountReportModel> list = helper.getSaldoAccountReportList(map);
            if (list == null) {
                list = new ArrayList<SaldoAccountReportModel>();
            }
            model.setAccountList(list);
        }

        HashMap reportMap = new HashMap();
        reportMap.put("title", "Daftar Akun");
        reportMap.put("titleaccountId", "No Akun");
        reportMap.put("titleaccountName", "Nama Akun");
        reportMap.put("titleaccountGroupName", "Grup Akun");
        reportMap.put("titleamount", "Saldo");
        openReport(param.getLabel("STATIC.Daftar Akun"), "vuk/account/saldoaccount.jasper", listMaster, reportMap, Constants.REPORT_MIME_TYPES.PDF);

    }

    private void doView() {
        AccountHelper help = new AccountHelper(TA.getCurrent().getLocale());
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        p.put("status", 4);
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("accountCorporate.title"), "account/accountCorporateList.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }

    @Override
    public int doDeletion
            () throws Exception {
        String lc = getLogger().openLog();
        AccountModel m = (AccountModel) getSelectedObject();
        AccountModel accountModel = new AccountModel();
        accountModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        accountModel.setAccountId(m.getAccountId());
        setAuditTrail(accountModel);
        AccountHelper help = new AccountHelper(TA.getCurrent().getLocale());
        int res = help.deleteAccount(accountModel);
        if (res != 0)
            getLogger().log(lc, TenmaLog.LOG_FOR_DELETE, "Delete AccountCorporate CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());
        return res;
    }

    @Override
    public void refreshTable() {
        doRefreshData();
    }

    public void executingDataPreparation() {

        container = new TenmaTableContainer();

        container.addContainerProperty(ACCOUNT_DETAIL, Button.class, "");
        container.addContainerProperty(ACCOUNT_ID, String.class, "");
        container.addContainerProperty(ACCOUNT_NAME, String.class, "");
        container.addContainerProperty(ACCOUNT_CURRENCY, String.class, "");
        container.addContainerProperty(ACCOUNT_VALID_FROM, Date.class, Calendar.getInstance().getTime());
        container.addContainerProperty(ACCOUNT_VALID_TO, Date.class, Calendar.getInstance().getTime());
        container.addContainerProperty(ACCOUNT_GROUP, String.class, "");

        getTableProperty().setContainerDataSource(container);
        getTableProperty().commit();


        getTableProperty().setColumnHeaders(
                getLabel(""),
                getLabel("account.id"),
                getLabel("account.name"),
                getLabel("default.currency"),
                getLabel("account.startActive"),
                getLabel("account.endActive"),
                getLabel("group.name")
        );
        getTableProperty().setColumnCollapsingAllowed(true);
        getTableProperty().setColumnCollapsible(ACCOUNT_NAME, false);
        getTableProperty().setColumnCollapsible(ACCOUNT_GROUP, false);
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().setMultiSelect(false);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Item item = getTableProperty().getItem(valueChangeEvent.getProperty().getValue());
                if (item != null) {
                    AccountModel ac = new AccountModel();
                    ac.setAccountId((String) item.getItemProperty(ACCOUNT_ID).getValue());
                    ac.setAccountName((String) item.getItemProperty(ACCOUNT_NAME).getValue());
                    ac.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
                    setSelectedObject(ac);
                } else {
                    System.out.println("empty");
                }
            }

        });

        getTableProperty().setColumnDate(ACCOUNT_VALID_FROM);
        getTableProperty().setColumnDate(ACCOUNT_VALID_TO);

        doRefreshData();
    }

    @Override
    public void doSearch() {
        setPageIndex(0);
        doRefreshData();
    }


    @Override
    public void performReloadForTableSort() {
        doRefreshData();
    }

    private void doRefreshData() {
        container.removeAllItems();
        container = collectData();
    }

    private TenmaTableContainer collectData() {
        HashMap p = new HashMap();

        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        AccountHelper help = new AccountHelper(TA.getCurrent().getLocale());

        List ls = loadPagingData(p, "accountId", Constants.SORTING_TYPE.ASCENDING, "AccountCorporate", help);

        for (Object l : ls) {
            AccountModel m = (AccountModel) l;
            container.addItem(m.getAccountId());

            Button btn = new Button();
            btn.setIcon(new ThemeResource(Constants.ICON_DETAIL));
            btn.setPrimaryStyleName("actioninvoice");
            btn.addClickListener(this);
            btn.setData(m);

            container.getContainerProperty(m.getAccountId(), ACCOUNT_DETAIL).setValue(btn);
            container.getContainerProperty(m.getAccountId(), ACCOUNT_ID).setValue(m.getAccountId());
            container.getContainerProperty(m.getAccountId(), ACCOUNT_NAME).setValue(m.getAccountName());
            container.getContainerProperty(m.getAccountId(), ACCOUNT_CURRENCY).setValue(m.getAccountCurrency() == null ? "-" : m.getAccountCurrency());
            container.getContainerProperty(m.getAccountId(), ACCOUNT_VALID_FROM).setValue(m.getAccountValidFrom());
            container.getContainerProperty(m.getAccountId(), ACCOUNT_VALID_TO).setValue(m.getAccountValidTo());
            container.getContainerProperty(m.getAccountId(), ACCOUNT_GROUP).setValue(m.getAccountGroupName());
        }

        return container;
    }


}

