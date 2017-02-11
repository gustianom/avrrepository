package com.tenma.common.gui.display;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

/**
 * Created by ndwijaya on 11/30/14.
 * Design to be standard footer for ModifyForm
 */
public class TenmaFooterPanel extends Panel {
    HorizontalLayout hzm = new HorizontalLayout();
    private boolean enable;

    public TenmaFooterPanel() {
        enable = false;
        setId("tenmafooterid");
        setPrimaryStyleName("tenmafooter");
        createUI();
    }

    private void createUI() {
        hzm.setSpacing(true);
        setContent(hzm);
    }

    public void registerButton(Component newButton) {
        enable = true;
        hzm.addComponent(newButton);
    }

    public boolean isEnable() {
        return enable;
    }
}
