package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaMasterList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.account.AccountGroupHelper;
import com.tenma.model.common.TenmaTableContainer;
import com.tenma.model.fam.AccountGroupModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 8/29/12
 * Time: 1:51 PM
 */
public class AccountGroupList extends TenmaMasterList implements Button.ClickListener {
    private final String ACCOUNTGROUP_NAME = "accountGroupName";
    private final String ACCOUNTGROUP_DESC = "accountGroupDesc";
    private final String ACCOUNTGROUP_ID = "accountGroupId";
    private final String ACCOUNTGRPTYPE = "accountGrpType";
    private final String COMMUNITY_ID = "communityId";

    private Button menu;
    private AccountGroupModel ac;
    private boolean accMode;
    private TenmaTableContainer ic;
    private Button viewReport;

    public AccountGroupList() {
        super();
        setTitle("accountGroup.title");
        getAddButton().setEnabled(true);
        getUpdateButton().setEnabled(true);
        getDeleteButton().setEnabled(true);
        menu.setVisible(false);
    }

    public boolean isAccMode() {
        return accMode;
    }

    public void setAccMode(boolean accMode) {
        this.accMode = accMode;
    }

    public AccountGroupModel getAc() {
        return ac;
    }

    public void setAc(AccountGroupModel ac) {
        this.ac = ac;
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            AccountGroupModify modify = new AccountGroupModify(this, update_mode);
            TA.getCurrent().updateWorkingArea(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int doDeletion() throws Exception {
        String lc = getLogger().openLog();
        AccountGroupModel m = (AccountGroupModel) getSelectedObject();
        setAuditTrail(m);
        m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        AccountGroupHelper help = new AccountGroupHelper();
        int res = help.deleteAccountGroup(m);
        if (res != 0)
            getLogger().log(lc, TenmaLog.LOG_FOR_DELETE, "Delete AccountGroup CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());
        return res;
    }

    @Override
    public void refreshTable() {
        getSearchButton().click();
    }

    public void executingDataPreparation() {
        ic = new TenmaTableContainer();
        ic.addContainerProperty(ACCOUNTGROUP_ID, String.class, "");
        ic.addContainerProperty(ACCOUNTGROUP_NAME, String.class, "");
        ic.addContainerProperty(ACCOUNTGROUP_DESC, String.class, "");
        ic.addContainerProperty(ACCOUNTGRPTYPE, String.class, "");
        ic.addContainerProperty(COMMUNITY_ID, String.class, "");
        getTableProperty().setContainerDataSource(ic);
        getTableProperty().commit();
        getTableProperty().setColumnHeaders(new String[]{
                        getLabel("account.accountGroupId"),
                        getLabel("account.accountGroupName"),
                        getLabel("account.accountGroupDesc"),
                        getLabel("account.accountGrpType"),
                        getLabel("account.communityId")
                }
        );
        getTableProperty().setSelectable(true);
        getTableProperty().setImmediate(true);
        getTableProperty().setColumnCollapsingAllowed(true);
        getTableProperty().setColumnCollapsed(ACCOUNTGROUP_ID, true);
        getTableProperty().setColumnCollapsed(COMMUNITY_ID, true);
        getTableProperty().addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                ac = new AccountGroupModel();
                Item item = getTableProperty().getItem(event.getProperty().getValue());
                if (item != null) {
                    ac.setAccountGrpId((String) item.getItemProperty(ACCOUNTGROUP_ID).getValue());
                    ac.setAccountGrpName((String) item.getItemProperty(ACCOUNTGROUP_NAME).getValue());
                    ac.setAccountGrpDesc((String) item.getItemProperty(ACCOUNTGROUP_DESC).getValue());

                    setSelectedObject(ac);
                } else {
                    System.out.println("Null");
                }
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
        ic.removeAllItems();
        ic = collectData();
    }

    private TenmaTableContainer collectData() {
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        AccountGroupHelper help = new AccountGroupHelper();
        List ls = loadPagingData(p, "accountGrpName", Constants.SORTING_TYPE.ASCENDING, "AccountGroupList", help);
        for (int i = 0; i < ls.size(); i++) {
            AccountGroupModel m = (AccountGroupModel) ls.get(i);
            Item id = ic.addItem(m.getAccountGrpId());
            ic.getContainerProperty(m.getAccountGrpId(), ACCOUNTGROUP_ID).setValue(m.getAccountGrpId());
            ic.getContainerProperty(m.getAccountGrpId(), ACCOUNTGROUP_NAME).setValue(m.getAccountGrpName());
            ic.getContainerProperty(m.getAccountGrpId(), ACCOUNTGROUP_DESC).setValue(m.getAccountGrpDesc());
            ic.getContainerProperty(m.getAccountGrpId(), ACCOUNTGRPTYPE).setValue(m.getAccountGrpType() == 0 ? "BS" : "PL");
            ic.getContainerProperty(m.getAccountGrpId(), COMMUNITY_ID).setValue(m.getCommunityId());
        }

        return ic;
    }

    public void configureToolbarBefore() {
        menu = new Button(getLabel("default.menuAccount"), this);
        menu.setIcon(new ThemeResource("layouts/images/16/187.png"));
        getButtonArea().addComponent(menu);

    }

    public void configureToolbarAfter() {
        viewReport = new Button(param.getLabel("viewReport"), this);
//        getButtonArea().addComponent(viewReport);
    }

    public void doPrint() {
        AccountGroupHelper help = new AccountGroupHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("accountgroup.title"), "account/accountGroupList.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }


    private void doView() {
        AccountGroupHelper help = new AccountGroupHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("accountgroup.title"), "account/accountGroupList.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        Button b = clickEvent.getButton();
        if (b.equals(menu)) {
            doAccountList();
        } else if (b.equals(viewReport)) {
            doView();
        }
    }

    private void doAccountList() {
        if (ac == null) {
            Notification.show(param.getMessage("msg.selectrow"));
        } else {
            setAccMode(true);
//            AccountListOld list = new AccountListOld( this);
//            updateWorkingArea(list);
        }
    }
}
   
