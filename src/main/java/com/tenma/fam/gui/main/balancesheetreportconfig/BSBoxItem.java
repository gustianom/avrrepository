package com.tenma.fam.gui.main.balancesheetreportconfig;

import com.tenma.common.TA;

import com.tenma.common.util.Constants;
import com.tenma.fam.bean.balancesheet.BalanceSheetHelper;
import com.tenma.fam.bean.balancesheetdetail.BalanceSheetDetailHelper;
import com.tenma.model.fam.BalanceSheetModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.HashMap;

/**
 * Created by tenma-01 on 06/01/16.
 */
public class BSBoxItem extends HorizontalLayout implements Button.ClickListener {
    public static final int ACTIVE = 1;
    public static final int PASSIVE = 2;
    private final int type;
    private BalanceSheetModel objModel;
    private Button icon;

    private VerticalLayout root = new VerticalLayout();
    private BalanceSheetReportEventListener parents;

    public BSBoxItem(BalanceSheetModel model) {
        this.type = model.getType();
        this.objModel = model;
        prepareBSBoxItem();
    }

    private void prepareBSBoxItem() {
        icon = new Button();
//        icon.setPrimaryStyleName("studentbtn");
//        icon.addStyleName("coursebtnicon");
        icon.setIcon(new ThemeResource("layouts/images/16/closed.png"));
        icon.setPrimaryStyleName("btnClosedwelcome");
        icon.addClickListener(this);
        generateUI(type);
    }

    public void generateUI(int type) {
        root.setHeight("80");
        root.setWidth("300px");
        root.setMargin(new MarginInfo(false, false, false, true));
        root.setStyleName("studentitemborder");
        addComponent(root);
        addComponent(icon);
        setStyleName("studentroot");

        Button labelName = new Button();
        Button labelCountAccount = new Button();
        labelName.setPrimaryStyleName("roommaster");
        labelCountAccount.setPrimaryStyleName("roommaster");
        labelName.addClickListener(click -> showName());
        labelCountAccount.addClickListener(click -> showCountAccount());

//        labelName.setStyleName("room");
//        labelName.setContentMode(ContentMode.HTML);
//        labelName.setStyleName("studentname");
        labelName.setWidth("100px");

//        labelCountAccount.setContentMode(ContentMode.HTML);
//        labelCountAccount.setStyleName("studentname");
        labelCountAccount.setWidth(null);
        VerticalLayout vName = new VerticalLayout(labelName, labelCountAccount);

        labelName.setCaption(objModel.getBalanceSheetName());
        labelCountAccount.setCaption(String.valueOf(getCountAccount(objModel) + " Account"));

        HorizontalLayout hz = new HorizontalLayout();
        hz.addComponent(vName);
        hz.setComponentAlignment(vName, Alignment.MIDDLE_RIGHT);
        root.addComponent(hz);
        hz.setWidth("100%");
    }

    private Integer getCountAccount(BalanceSheetModel objModel) {
        int count = 0;
        BalanceSheetDetailHelper helper = new BalanceSheetDetailHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put("balanceSheetId", objModel.getBalanceSheetId());
        count = helper.countTotalList(map);
        return count;
    }

    private void showCountAccount() {
        dialogUpdateAccount();
    }

    private void dialogUpdateAccount() {
        AddAccountBalanceSheet window = new AddAccountBalanceSheet(objModel);
        window.addEventListener(parents);
        window.center();
        window.setCaption("Account");
        UI.getCurrent().getUI().addWindow(window);
    }

    private void showName() {
        dialogUpdateBalanceSheet();
    }

    private void dialogUpdateBalanceSheet() {
        AddBalanceSheet window = new AddBalanceSheet();
        window.setValueModel(objModel);
        window.addEventListener(parents);
        window.center();
        window.setCaption("Update Balance Sheet");
        UI.getCurrent().getUI().addWindow(window);

    }


    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(icon)) {
            if (doDelete())
                parents.BoxSheetRemoveEventListener(objModel);
        }
    }

    private Boolean doDelete() {
        int res = 0;
        Boolean rt = false;
        BalanceSheetHelper helper = new BalanceSheetHelper();
        BalanceSheetModel model = new BalanceSheetModel();
        model.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        ;
        model.setBalanceSheetId(objModel.getBalanceSheetId());
        try {
            res = helper.deleteBalanceSheet(model);
            if (res > 0)
                rt = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }

    public void addBoxSheetEventListener(BalanceSheetReportEventListener parents) {
        this.parents = parents;
    }
}
