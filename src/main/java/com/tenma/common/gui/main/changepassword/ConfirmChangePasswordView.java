package com.tenma.common.gui.main.changepassword;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class ConfirmChangePasswordView extends TenmaNewPanel {
    private Button buttonContinue;
    private Button btnViewAccount;

    private Button buttonCancel;
    private Panel panelContent;
    private ChangePasswordPanel passwordPanel;


    public ConfirmChangePasswordView() {
        super();
        param = TA.getCurrent().getParams();
        buttonContinue = new Button(param.getLabel("default.continue"), this);
        buttonContinue.setIcon(new ThemeResource("layouts/images/16/131.png"));

        btnViewAccount = new Button(param.getLabel("forgotPassword.gotoLogon"), this);
        btnViewAccount.setVisible(false);

        buttonCancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
        buttonCancel.setIcon(new ThemeResource("layouts/images/deletered.png"));

        panelContent = new Panel();
        panelContent.setCaption(param.getLabel("default.changepassword"));
        panelContent.setPrimaryStyleName("forgottenPasswordFrame");
        panelContent.setWidth("100%");

        doDisplayStage();

        HorizontalLayout lyBut = new HorizontalLayout();
        lyBut.addComponent(buttonContinue);
        lyBut.addComponent(btnViewAccount);
        lyBut.addComponent(buttonCancel);

        VerticalLayout vl = new VerticalLayout();
        vl.setWidth("50%");
        vl.setSpacing(true);
        vl.setMargin(true);
        vl.addComponent(new Label());
        vl.addComponent(new Label());
        vl.addComponent(panelContent);
        vl.addComponent(lyBut);
        vl.setComponentAlignment(panelContent, Alignment.MIDDLE_CENTER);
        vl.setComponentAlignment(lyBut, Alignment.BOTTOM_LEFT);

        HorizontalLayout gen = new HorizontalLayout();
        gen.setSpacing(true);
        gen.setSizeFull();
        gen.setMargin(true);
        gen.addComponent(vl);
        gen.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);
        setCompositionRoot(gen);
        setSizeFull();
        setPrimaryStyleName("wizardBackGround");
    }

    private void doDisplayStage() {
        passwordPanel = new ChangePasswordPanel(this);
        panelContent.setContent(passwordPanel);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        try {
            if (clickEvent.getButton().equals(buttonContinue)) {
                passwordPanel.saveCurrentStage();
            } else if (clickEvent.getButton().equals(buttonCancel) || (clickEvent.getButton().equals(btnViewAccount))) {
                doCancel();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("error occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
        }
    }

    private void doCancel() throws Exception {
        getUI().getSession().getSession().invalidate();
        getUI().getPage().setLocation("../index.jsp");
    }


    public Button getButtonContinue() {
        return buttonContinue;
    }

    public void doButtonContinue() {
        buttonContinue.click();
    }

    public Button getBtnViewAccount() {
        return btnViewAccount;
    }

}