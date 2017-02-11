package com.tenma.common.gui.display.custom;


import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by Andrian on 06/11/15.
 */
public abstract class EduCustomModify extends TenmaMasterFormModify {
    private HorizontalLayout hHeader, hFooter, hBtnLay;
    private VerticalLayout vcustomUI;
    private Label lTitle;
    private FormLayout uiForm;


    public EduCustomModify(TenmaPanel parentContainer, int modifyMode) {
        super(parentContainer, modifyMode);
        initializeUI();
        getHeader().setVisible(false);

        setUIStyle();
    }


    private void initializeUI() {
        vcustomUI = new VerticalLayout();
        hHeader = new HorizontalLayout();
        hFooter = new HorizontalLayout();
        hBtnLay = new HorizontalLayout();
        lTitle = new Label();


        hBtnLay.addComponent(getButSave());
        hBtnLay.addComponent(getButCancel());

        hFooter.addComponent(hBtnLay);
        hFooter.setComponentAlignment(hBtnLay, Alignment.MIDDLE_RIGHT);
        hFooter.setMargin(true);

        uiForm = getPersonForm();


        vcustomUI.addComponent(hHeader);
        vcustomUI.addComponent(uiForm);
        vcustomUI.addComponent(hFooter);
        vcustomUI.setComponentAlignment(hHeader, Alignment.BOTTOM_CENTER);
        vcustomUI.setComponentAlignment(uiForm, Alignment.BOTTOM_CENTER);
        vcustomUI.setComponentAlignment(hFooter, Alignment.BOTTOM_CENTER);
        vcustomUI.setMargin(new MarginInfo(true, true, false, true));


    }


    private void setUIStyle() {
        vcustomUI.setWidth("100%");
        uiForm.setWidth("800px");
        hHeader.setWidth("800px");
        hHeader.setHeight("35px");
        hFooter.setWidth("800px");
        hFooter.setHeight("100px");
        hFooter.setStyleName("metro-metro-grey");
        uiForm.setStyleName("metro-layout");
    }


    public VerticalLayout getCustomUI() {
        return vcustomUI;
    }


    public void setTitleStr(String titleStr) {
        hHeader.setStyleName("metro-slate-grey");
        hHeader.addComponent(lTitle);
        lTitle.setValue(param.getLabel(titleStr));
        lTitle.setPrimaryStyleName("headerTitle");

    }

    public FormLayout getCustomForm() {
        return uiForm;
    }


    public HorizontalLayout getCustomFooter() {
        return hFooter;
    }
}

