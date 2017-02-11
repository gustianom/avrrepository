package com.tenma.fam.gui.main.balancesheetreportconfig;

import com.tenma.common.TA;

import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.balancesheetdetail.BalanceSheetDetailHelper;
import com.tenma.fam.gui.main.balancesheetreportconfig.Field.AccountField;
import com.tenma.fam.gui.main.balancesheetreportconfig.Field.AccountSelection;
import com.tenma.model.fam.AccountModel;
import com.tenma.model.fam.BalanceSheetDetailModel;
import com.tenma.model.fam.BalanceSheetModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 07/01/16.
 */
public class AddAccountBalanceSheet extends TenmaWindow implements AccountSelection {
    Button btnAdd;
    VerticalLayout vLay = new VerticalLayout();
    int counter = 1;
    int maxCol = 3;
    private String deleteBtn = "DELETE_BUTTON";
    private BalanceSheetModel balanceModel;
    private VerticalLayout vRoot = new VerticalLayout();
    private List lsAccount = new ArrayList<>();
    private BalanceSheetReportEventListener parents;

    public AddAccountBalanceSheet(BalanceSheetModel objModel) {
        super();
        this.balanceModel = objModel;
        GUI();
    }

    private void GUI() {

        AccountField field = new AccountField(this);
        field.setCaption("Account Name");
        btnAdd = new Button();
        btnAdd.setDescription(getLabel("default.add"));
        btnAdd.setIcon(new ThemeResource(Constants.UPDATE_ICON));
        btnAdd.setStyleName("circle");
        btnAdd.addClickListener(click -> closeWindow());
        HorizontalLayout hBtn = new HorizontalLayout(btnAdd);

        FormLayout form = new FormLayout(field);
        vRoot.setSpacing(true);
        vRoot.addComponent(form);
        vRoot.addComponent(vLay);
        vRoot.addComponent(hBtn);
        vRoot.setMargin(true);
        vRoot.setComponentAlignment(hBtn, Alignment.BOTTOM_RIGHT);

        lsAccount = getListAccount();
        if (lsAccount.size() > 0)
            doRefreshUI();
        setContent(vRoot);
    }

    private void closeWindow() {
        parents.AddEventListener();
        this.close();
    }

    @Override
    public void setAccountModel(AccountModel model) {
        if (model != null) {
            BalanceSheetDetailModel m = new BalanceSheetDetailModel();
            m.setAccountId(model.getAccountId());
            m.setAccountName(model.getAccountName());
            if (doAddAccount(m)) {
                lsAccount.add(m);
                doRefreshUI();
            }
        }
    }

    private Boolean doAddAccount(BalanceSheetDetailModel model) {
        int res = 0;
        BalanceSheetDetailHelper helper = new BalanceSheetDetailHelper();
        model.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        model.setBalanceSheetId(balanceModel.getBalanceSheetId());
        TA.getCurrent().setAuditTrail(model);
        try {
            res = helper.insertNewBalanceSheetDetail(model);
            return res > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void doRefreshUI() {
        vLay.removeAllComponents();
        HorizontalLayout hz = new HorizontalLayout();
        vLay.addComponent(hz);
        for (int i = 0; i < lsAccount.size(); i++) {
            System.out.println("lsAccount.size() = " + lsAccount.size());
            BalanceSheetDetailModel accountModel = (BalanceSheetDetailModel) lsAccount.get(i);
            System.out.println("counter = " + counter);
            if (counter == maxCol) {
                counter = 0;
                hz = new HorizontalLayout();
                vLay.addComponent(hz);
            }
            Label lblName = new Label(accountModel.getAccountName());
            Label space = new Label("");
            space.setWidth(1, Unit.PIXELS);
            Button btn = new Button(new ThemeResource("layouts/images/12/close.jpg"));
            btn.setId(deleteBtn);
            btn.setData(accountModel);
            btn.setStyleName(BaseTheme.BUTTON_LINK);
            btn.addClickListener(click -> clickEventDelete(accountModel));

            HorizontalLayout item = new HorizontalLayout(lblName, space, btn);
            hz.addComponent(item);
            counter++;
        } // end for
    }

    private void clickEventDelete(BalanceSheetDetailModel model) {
        doDelete(model);

    }

    public void addEventListener(BalanceSheetReportEventListener parents) {
        this.parents = parents;
    }


    private void doDelete(BalanceSheetDetailModel model) {
        int res = 0;
        model.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        model.setBalanceSheetId(balanceModel.getBalanceSheetId());
        TA.getCurrent().setAuditTrail(model);
        BalanceSheetDetailHelper helper = new BalanceSheetDetailHelper();
        try {
            res = helper.deleteBalanceSheetDetail(model);
            if (res > 0) {
                lsAccount.remove(model);
                doRefreshUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getListAccount() {
        List<BalanceSheetDetailModel> list = new ArrayList<BalanceSheetDetailModel>();
        BalanceSheetDetailHelper helper = new BalanceSheetDetailHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("balanceSheetId", balanceModel.getBalanceSheetId());
        list = helper.getListRenderer(map, false);
        return list;
    }
}
