package com.tenma.share.gui.display.component;


import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormLayout;
import com.tenma.common.util.Constants;
import com.tenma.common.util.web.TenmaReportUtility;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.HashMap;

/**
 * Created by Sigit hadi wibowo .
 * Date    : 28-01-2015
 * Time    : 11:05
 * Copyright (c) 2015 Tenma Bright Sky
 * ReDesign by :Andrian 17-04-2015
 */
public abstract class ReportForm extends TenmaPanel {


    private TenmaMasterFormLayout lay;
    private TenmaMasterFormLayout additionalComponent;
    private Button viewButton;
    private HashMap map = new HashMap();
    private ReportFormSelection selection;

    public ReportForm() {

        this.selection = getReportSelection();
        viewButton = new Button(param.getLabel("default.view"), this);
        viewButton.setIcon(new ThemeResource(Constants.PAPER_ICON));

        VerticalLayout vMain = new VerticalLayout();
        HorizontalLayout hHeader = new HorizontalLayout();

        Label lTitile = new Label(getReportCaption());
        hHeader.addComponent(lTitile);
        hHeader.setComponentAlignment(lTitile, Alignment.TOP_CENTER);
        lTitile.setPrimaryStyleName("headerFormTitle");
        hHeader.setStyleName("metroHeader");
        hHeader.setWidth("600px");
        hHeader.setHeight("35px");


        lay = new TenmaMasterFormLayout();

        additionalComponent = new TenmaMasterFormLayout();

        vMain.addComponent(lay);
        vMain.setWidth("600px");
        vMain.setPrimaryStyleName("metro-layout");

        HorizontalLayout hFooter = new HorizontalLayout();
        hFooter.addComponent(viewButton);
        hFooter.setComponentAlignment(viewButton, Alignment.BOTTOM_CENTER);
        hFooter.setWidth("600px");
        hFooter.setHeight("40px");
        hFooter.setStyleName("metro-metro-grey");

        VerticalLayout customUI = new VerticalLayout();
        customUI.addComponent(hHeader);
        customUI.addComponent(vMain);
        customUI.addComponent(hFooter);
        customUI.setComponentAlignment(hHeader, Alignment.MIDDLE_CENTER);
        customUI.setComponentAlignment(vMain, Alignment.MIDDLE_CENTER);
        customUI.setComponentAlignment(hFooter, Alignment.MIDDLE_CENTER);


        this.setContent(customUI);

    }

    public abstract ReportFormSelection getReportSelection();

    public abstract String getReportCaption();

    public final void addComponent(Field component, String keyId) {
        component.setId(keyId);
        lay.addComponent(component);
    }


    public final void addAdditionalComponent(Component component) {
        lay.addComponent(component);
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (viewButton.equals(clickEvent.getButton())) {
            doOpenReport();
        }
    }

    private void doOpenReport() {
        int com = lay.getComponentCount();
        for (int index = 0; index < com; index++) {
            Component comp = lay.getComponent(index);
            if (comp instanceof Field) {
                Field field = (Field) comp;
                map.put(field.getId(), field.getValue());
            }
        }
        TenmaReportUtility.ReportInterface[] reports = new TenmaReportUtility.ReportInterface[]{
                selection.doViewReport(map)
        };
        openReport(getCaption(), reports, false, Constants.REPORT_MIME_TYPES.PDF);
    }


}
