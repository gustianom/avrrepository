package com.tenma.common.gui.ui;


import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.main.TopPanelAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by gustianom on 02/12/14.
 */
public abstract class CommonScreenApps extends TenmaNewPanel implements View {
    protected String viewName;
    protected boolean requiredBlankScreen = true;

    public CommonScreenApps() {

    }

    public boolean isRequiredBlankScreen() {
        return requiredBlankScreen;
    }

    public abstract void doRenderWelcome();

    public abstract void setUserLogo(String usrLogon);

    public abstract VerticalLayout focusedWindows();

    public abstract TopPanelAction getTopPanel();

    public abstract VerticalLayout getWorkingPanelArea();

    public abstract void goHome();

    public abstract void refreshScreen();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.out.println("CommonScreenApps.enter");
    }


}
