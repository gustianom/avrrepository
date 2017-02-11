package com.tenma.common.gui.main.useractivation;

import com.tenma.common.TA;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.logon.FirstTimeActivation;
import com.tenma.common.gui.main.TenmaBaseApplication;
import com.tenma.common.gui.main.forgotpassword.ForgotInterface;
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
public class UserAccountActivation extends TenmaNewPanel {
    public final String MEMBER_MODEL = "MEMBER_MODEL";
    public final String USER_MODEL = "USER_MODEL";
    public final String VERIFICATION_MEDIA = "VERIFICATION_MEDIA";
    public HashMap dataVerification;
    private Button btnViewAccount;
    private Button btnGoLogout;
    private Button buttonReset;
    private Button buttonContinue;
    private Panel panelContent;
    private int stageIndex = 1;
    private ForgotInterface forgotInterface;
    private TenmaBaseApplication parent;

    public UserAccountActivation(TenmaBaseApplication parent) {
        super();
        this.parent = parent;
        param = TA.getCurrent().getParams();
        dataVerification = new HashMap();
        dataVerification.put(USER_MODEL, TA.getCurrent().getClientInfo().getClientUserModel());
        dataVerification.put(MEMBER_MODEL, TA.getCurrent().getClientInfo().getClientMemberModel());

        buttonContinue = new Button(param.getLabel("default.continue"), this);
        buttonContinue.setIcon(new ThemeResource("layouts/images/16/131.png"));

        btnGoLogout = new Button(param.getLabel("default.logout"), this);
        btnGoLogout.setIcon(new ThemeResource("layouts/images/logout.png"));
        btnGoLogout.setVisible(false);

        buttonReset = new Button(param.getLabel("default.resend"), this);
        buttonReset.setIcon(new ThemeResource("layouts/images/16/128.png"));
        buttonReset.setVisible(false);

        btnViewAccount = new Button(param.getLabel("userverification.viewaccount"), this);
        btnViewAccount.setIcon(new ThemeResource(Constants.SELECT_ICON));
        btnViewAccount.setVisible(false);

        panelContent = new Panel();
        panelContent.setCaption(param.getLabel("userverification.title"));
        panelContent.setPrimaryStyleName("forgottenPasswordFrame");
        panelContent.setWidth("100%");

        doDisplayStage();

        HorizontalLayout lyBut = new HorizontalLayout();
        lyBut.addComponent(buttonReset);
        lyBut.addComponent(buttonContinue);
        lyBut.addComponent(btnViewAccount);
        lyBut.addComponent(btnGoLogout);

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

        setSizeFull();
        setPrimaryStyleName("wizardBackGround");

        FirstTimeActivation firstTimeActivation = new FirstTimeActivation(this);
        firstTimeActivation.notifyActivation();

        setCompositionRoot(gen);
        forgotInterface.doFieldFocus();
    }

    private void doDisplayStage() {
        getButtonReset().setVisible(false);
        switch (stageIndex) {
            case 0:
                ResendVerification stage1 = new ResendVerification(this);
                panelContent.setContent(stage1);
                forgotInterface = stage1;
                break;
            case 1:
                UserStageVerification stage2 = new UserStageVerification(this);
                panelContent.setContent(stage2);
                forgotInterface = stage2;
                getButtonReset().setVisible(true);
                break;
        }

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        try {
            if (clickEvent.getButton().equals(buttonContinue)) {
                if (forgotInterface.saveCurrentStage()) {
                    stageIndex++;
//                    TODO-BUG
//                    UserTeacherGroup userTeacherGroup = new UserTeacherGroup();
//                    userTeacherGroup.setUserTeacherGroup(tenmaApplication, TA.getCurrent().getClientInfo().getClientMemberModel() );
                    doDisplayStage();
                }
            } else if (clickEvent.getButton().equals(buttonReset)) {
                stageIndex = 0;
                doDisplayStage();
            } else if (clickEvent.getButton().equals(btnViewAccount)) {
                parent.logonSuccessFully();
            } else if (clickEvent.getButton().equals(btnGoLogout)) {
                UI.getCurrent().close();
                getSession().getSession().invalidate();
                getUI().getPage().setLocation("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("error occured, please contact your administrator", Notification.Type.ERROR_MESSAGE);
        }
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

    public Button getBtnGoLogout() {
        return btnGoLogout;
    }

    public void doButtonResend() {
        buttonReset.click();
    }

}