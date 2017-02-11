package com.tenma.common.gui.main.forgotpassword;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/23/13
 * Time: 2:52 AM
 */
public class ForgotPassword extends TenmaNewPanel {
    public final String USER_MODEL = "USER_MODEL";
    public final String VERIFICATION_MEDIA = "VERIFICATION_MEDIA";
    public HashMap dataForgot;
    private Button btnViewAccount;
    private Button buttonReset;
    private Button buttonContinue;
    private Button buttonCancel;
    private Panel panelContent;
    private int stageIndex = 0;
    private ForgotInterface forgotInterface;


    public ForgotPassword() {
        super();
        dataForgot = new HashMap();
        param = TA.getCurrent().getParams();
        buttonContinue = new Button(param.getLabel("default.continue"), this);
        buttonContinue.setIcon(new ThemeResource("layouts/images/16/131.png"));

        buttonReset = new Button(param.getLabel("default.resend"), this);
        buttonReset.setIcon(new ThemeResource("layouts/images/16/128.png"));
        buttonReset.setVisible(false);

        buttonCancel = new Button(param.getLabel(Constants.LABEL_CANCEL), this);
        buttonCancel.setIcon(new ThemeResource("layouts/images/deletered.png"));

        btnViewAccount = new Button(param.getLabel("forgotPassword.gotoLogon"), this);
        btnViewAccount.setVisible(false);

        panelContent = new Panel();
        panelContent.setCaption(param.getLabel("default.forgotPassword"));
        panelContent.setPrimaryStyleName("forgottenPasswordFrame");
        panelContent.setWidth("100%");

        doDisplayStage();

        HorizontalLayout lyBut = new HorizontalLayout();
        lyBut.addComponent(buttonReset);
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
        forgotInterface.doFieldFocus();
    }

    public final void doDisplayStage() {
        getButtonReset().setVisible(false);
        switch (stageIndex) {
            case 0:
                ForgotStage1 stage1 = new ForgotStage1(this);
                panelContent.setContent(stage1);
                forgotInterface = stage1;
                break;
            case 1:
                ForgotStage2 stage2 = new ForgotStage2(this);
                panelContent.setContent(stage2);
                forgotInterface = stage2;
                break;
            case 2:
                ForgotStageVerification stage3 = new ForgotStageVerification(this);
                panelContent.setContent(stage3);
                forgotInterface = stage3;
                getButtonReset().setVisible(true);
                break;
            case 3:
                ConfirmPasswordView stage4 = new ConfirmPasswordView(this);
                panelContent.setContent(stage4);
                forgotInterface = stage4;
                break;
        }

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        try {
            if (clickEvent.getButton().equals(buttonContinue)) {
                if (forgotInterface.saveCurrentStage()) {
                    stageIndex++;
                    doDisplayStage();
                }
            } else if (clickEvent.getButton().equals(buttonReset)) {
                stageIndex = 1;
                doDisplayStage();
            } else if ((clickEvent.getButton().equals(buttonCancel)) || (clickEvent.getButton().equals(btnViewAccount))) {
                doCancel();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("error occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
        }
    }

    private void doCancel() throws Exception {

        try {
            getUI().getSession().getSession().invalidate();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        getUI().getPage().setLocation("../index.jsp");
    }


    public Button getButtonContinue() {
        return buttonContinue;
    }

    public Button getBtnViewAccount() {
        return btnViewAccount;
    }

    public Button getButtonReset() {
        return buttonReset;
    }

    public void doButtonContinue() {
        buttonContinue.click();
    }

    public void doButtonResend() {
        buttonReset.click();
    }

    public final STAGE_INDEX getForceStageIndex() {
        STAGE_INDEX stg = STAGE_INDEX.ENTER_IDENTITY;
        switch (stageIndex) {
            case 0:
                stg = STAGE_INDEX.ENTER_IDENTITY;
                break;
            case 1:
                stg = STAGE_INDEX.SELECT_MEDIA;
                break;
            case 2:
                stg = STAGE_INDEX.ENTER_VERIFICATION;
                break;
            case 3:
                stg = STAGE_INDEX.ENTER_NEW_PASSWORD;
                break;
        }
        return stg;
    }

    public final void setForceStageIndex(STAGE_INDEX index) {
        stageIndex = index.getValue();
    }

    enum STAGE_INDEX {
        ENTER_IDENTITY(0), SELECT_MEDIA(1), ENTER_VERIFICATION(2), ENTER_NEW_PASSWORD(3);
        Integer methodvalue = 0;

        STAGE_INDEX(Integer value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return String.valueOf(methodvalue);
        }

    }
}