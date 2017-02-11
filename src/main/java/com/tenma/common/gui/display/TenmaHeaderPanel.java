package com.tenma.common.gui.display;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


/**
 * Created by ndwijaya on 11/30/14.
 */
public class TenmaHeaderPanel extends Panel {
    VerticalLayout vm = new VerticalLayout();
    HorizontalLayout hzm1 = new HorizontalLayout();
    HorizontalLayout hzm2 = new HorizontalLayout();
    private boolean enable;
    private int counter = 0;
    private Component headerTitle;


    public TenmaHeaderPanel() {
        enable = false;
        setPrimaryStyleName("tenmaHeader");
        createUI();
    }

    private void createUI() {
        hzm2.setSpacing(true);
        hzm2.setWidth("100%");
        vm.addComponent(hzm1);
        vm.addComponent(hzm2);
        setContent(vm);
    }

    public void registerTitle(Component component) {
        headerTitle = component;
        enable = true;
        vm.removeAllComponents();
        vm.addComponent(component);
        vm.addComponent(hzm2);
        setContent(vm);
    }

    public void registerComponent(Component newComponent, boolean reset) {
        hzm2.removeAllComponents();
        registerComponent(newComponent);
    }

    public void registerComponent(Component newComponent) {
        enable = true;
        counter++;
        hzm2.addComponent(newComponent);
    }

    public void setErrorMessage(String errorLabel) {
        if (headerTitle != null) {
            if (headerTitle instanceof TenmaHeaderLayout) {
                TenmaHeaderLayout headerLayout = (TenmaHeaderLayout) headerTitle;
                headerLayout.getLabelErr().setValue(errorLabel);
            }
        }
    }


    public boolean isEnable() {
        return enable;
    }
}
