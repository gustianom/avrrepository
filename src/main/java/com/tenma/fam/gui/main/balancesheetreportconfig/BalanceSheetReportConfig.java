package com.tenma.fam.gui.main.balancesheetreportconfig;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.fam.bean.balancesheet.BalanceSheetHelper;
import com.tenma.fam.gui.main.balancesheetreportconfig.balancesheetaccount.BalanceSheetAccount;
import com.tenma.model.fam.BalanceSheetModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 06/01/16.
 */
public class BalanceSheetReportConfig extends TenmaPanel implements BalanceSheetReportEventListener {
    Button btnAdd = new Button();
    HorizontalLayout hHeadButton = new HorizontalLayout();
    HorizontalLayout hTitle = new HorizontalLayout();
    VerticalLayout root = new VerticalLayout();
    HorizontalLayout hMain = new HorizontalLayout();
    VerticalLayout activaLayout = new VerticalLayout();
    VerticalLayout passivaLayout = new VerticalLayout();
    private List lsActiva = new ArrayList<>();
    private List lsPassiva = new ArrayList<>();
    private BalanceSheetAccount ac;

    public BalanceSheetReportConfig() {

        GUI();
    }

    private void GUI() {
        loadActiva();
        loadPassiva();
        btnAdd.setDescription(getLabel("default.add"));
        btnAdd.setIcon(new ThemeResource(Constants.BUTTON_CIRCLE_ICON.ADD.getValue()));
        btnAdd.setStyleName("circle");
        btnAdd.addClickListener(click -> addBalanceSheet());
        hHeadButton.addComponent(btnAdd);

        Label titleActiva = new Label(TA.getCurrent().getParams().getLabel("default.activa"));
        Label titlePassiva = new Label(TA.getCurrent().getParams().getLabel("default.passiva"));
        hTitle.addComponent(titleActiva);
        hTitle.addComponent(titlePassiva);
        hTitle.setComponentAlignment(titleActiva, Alignment.MIDDLE_LEFT);
        hTitle.setComponentAlignment(titlePassiva, Alignment.MIDDLE_RIGHT);
        hTitle.setSizeFull();

        hMain.setHeight(null);
        hMain.addComponent(activaLayout);
        hMain.addComponent(passivaLayout);
        hMain.setComponentAlignment(activaLayout, Alignment.MIDDLE_LEFT);
        hMain.setComponentAlignment(passivaLayout, Alignment.MIDDLE_RIGHT);

        hMain.setExpandRatio(activaLayout, 50f);
        hMain.setExpandRatio(passivaLayout, 50f);
        hMain.setSizeFull();
        root.addComponent(hHeadButton);
        root.addComponent(hTitle);
        root.addComponent(hMain);
        ac = new BalanceSheetAccount();
        root.addComponent(ac);

        root.setComponentAlignment(hHeadButton, Alignment.TOP_RIGHT);
        root.setComponentAlignment(hMain, Alignment.MIDDLE_CENTER);
        root.setMargin(new MarginInfo(true, true, true, true));
        setContent(root);
        generateActivaUI();
        generatePassivaUI();
    }

    private void addBalanceSheet() {
        AddBalanceSheet window = new AddBalanceSheet();
        window.setCaption("Add Balance Sheet");
        window.addEventListener(this);
        window.center();
        UI.getCurrent().getUI().addWindow(window);
    }

    public void doRefreshLayout() {
        hMain.setImmediate(true);
        generateActivaUI();
        generatePassivaUI();
    }

    private void loadActiva() {
        BalanceSheetHelper helper = new BalanceSheetHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "balanceSheetName");
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.ASCENDING);
        map.put("type", BSBoxItem.ACTIVE);
        lsActiva = helper.getListRenderer(map, false);

    }


    private void loadPassiva() {
        BalanceSheetHelper helper = new BalanceSheetHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        map.put(Constants.RECORDSELECT_SORTEDFIELD, "balanceSheetName");
        map.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.ASCENDING);
        map.put("type", BSBoxItem.PASSIVE);
        lsPassiva = helper.getListRenderer(map, false);
    }


    private void generateActivaUI() {

        System.out.println("*****************************************************************");
        activaLayout.removeAllComponents();
        if (lsActiva.size() == 0) {
            // define only Add class item
            HorizontalLayout hz = new HorizontalLayout();
//            hz.addComponent(wholeclass);
//            hz.addComponent(addNewClass);
//            hz.setComponentAlignment(addNewClass, Alignment.MIDDLE_LEFT);
//            hz.addComponent(wholeclass);
            activaLayout.addComponent(hz);
        } else {
            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
//            hz.addComponent(wholeclass);
//            activaLayout.addComponent(hz);
//            hz.setSpacing(true);
//            hz.setMargin(true);
            for (int i = 0; i < lsActiva.size(); i++) {
                System.out.println("========================== " + i);
                BalanceSheetModel balanceSheetModel = (BalanceSheetModel) lsActiva.get(i);
                hz = new HorizontalLayout();
//                hz.setSpacing(true);
//                hz.setMargin(new MarginInfo(false,false,true,false));
                activaLayout.addComponent(hz);
                BSBoxItem item = new BSBoxItem(balanceSheetModel);
                item.addBoxSheetEventListener(this);
                hz.addComponent(item);
            }
            activaLayout.addComponent(hz);
        }
    }


    private void generatePassivaUI() {

        System.out.println("*****************************************************************");
        passivaLayout.removeAllComponents();
        if (lsPassiva.size() == 0) {
            // define only Add class item
            HorizontalLayout hz = new HorizontalLayout();
//            hz.addComponent(wholeclass);
//            hz.addComponent(addNewClass);
//            hz.setComponentAlignment(addNewClass, Alignment.MIDDLE_LEFT);
//            hz.addComponent(wholeclass);
            passivaLayout.addComponent(hz);
        } else {
            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
//            hz.addComponent(wholeclass);
//            activaLayout.addComponent(hz);
//            hz.setSpacing(true);
//            hz.setMargin(true);
            for (int i = 0; i < lsPassiva.size(); i++) {
                System.out.println("========================== " + i);
                BalanceSheetModel balanceSheetModel = (BalanceSheetModel) lsPassiva.get(i);
                hz = new HorizontalLayout();
                passivaLayout.addComponent(hz);
                BSBoxItem item = new BSBoxItem(balanceSheetModel);
                item.addBoxSheetEventListener(this);
                hz.addComponent(item);
            }
            passivaLayout.addComponent(hz);
        }
    }


    @Override
    public void BoxSheetRemoveEventListener(BalanceSheetModel model) {
        if (model.getType() == BSBoxItem.ACTIVE) {
            lsActiva.remove(model);
        } else {
            lsPassiva.remove(model);
        }
        doRefreshLayout();

    }

    @Override
    public void AddEventListener() {
        loadActiva();
        loadPassiva();
        doRefreshLayout();
    }


}
