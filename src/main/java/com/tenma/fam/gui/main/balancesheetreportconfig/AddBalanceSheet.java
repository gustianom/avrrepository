package com.tenma.fam.gui.main.balancesheetreportconfig;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.balancesheet.BalanceSheetHelper;
import com.tenma.model.fam.BalanceSheetModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by tenma-01 on 07/01/16.
 */
public class AddBalanceSheet extends TenmaWindow {

    private TextField tName;
    private OptionGroup optType;
    private Button btnAdd;
    private VerticalLayout root;
    private BalanceSheetReportEventListener parents;
    private BalanceSheetModel model;

    public AddBalanceSheet() {
        super();
        GUI();
    }

    private void GUI() {
        tName = new TextField("Name :");
        optType = new OptionGroup("Type :");
        btnAdd = new Button();
        btnAdd.setDescription(getLabel("default.add"));
        btnAdd.setIcon(new ThemeResource(Constants.SAVE_ICON));
        btnAdd.setStyleName("circle");
        btnAdd.addClickListener(click -> addBalanceSheet());
        HorizontalLayout hBtn = new HorizontalLayout(btnAdd);

        optType.addItem(BSBoxItem.ACTIVE);
        optType.addItem(BSBoxItem.PASSIVE);
        optType.setItemCaption(BSBoxItem.ACTIVE, TA.getCurrent().getParams().getLabel("default.activa"));
        optType.setItemCaption(BSBoxItem.PASSIVE, TA.getCurrent().getParams().getLabel("default.passiva"));
        optType.setStyleName("horizontal");
        FormLayout form = new FormLayout(tName, optType);
        root = new VerticalLayout(form, hBtn);
        root.setComponentAlignment(hBtn, Alignment.BOTTOM_RIGHT);
        setContent(root);
        root.setMargin(true);
    }

    private void addBalanceSheet() {
        if (model == null) {
            if (checkValid())
                doSave();
        } else {
            doUpdate();
        }
    }

    private void doUpdate() {
        int res = 0;
        try {
            BalanceSheetHelper helper = new BalanceSheetHelper();
            BalanceSheetModel model = new BalanceSheetModel();
            model.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            model.setBalanceSheetId(this.model.getBalanceSheetId());
            model.setBalanceSheetName(tName.getValue());
            model.setType((Integer) optType.getValue());
            res = helper.updateBalanceSheet(model);
            if (res > 0) {
                parents.AddEventListener();
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSave() {
        int res = 0;
        try {
            BalanceSheetHelper helper = new BalanceSheetHelper();
            BalanceSheetModel model = new BalanceSheetModel();
            model.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
            model.setBalanceSheetName(tName.getValue());
            model.setType((Integer) optType.getValue());
            res = helper.insertNewBalanceSheet(model);
            if (res > 0) {
                parents.AddEventListener();
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean checkValid() {
        Boolean rt = true;
        if (tName.getValue().length() == 0) {
            tName.focus();
            rt = false;
        } else if (optType.getValue() == null) {
            optType.select(1); // select default activa
            rt = false;
        }
        return rt;
    }

    public void addEventListener(BalanceSheetReportEventListener parents) {
        this.parents = parents;
    }

    public void setValueModel(BalanceSheetModel valueModel) {
        this.model = valueModel;
        tName.setValue(valueModel.getBalanceSheetName());
        optType.setValue(valueModel.getType());
    }
}
