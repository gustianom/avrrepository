package com.tenma.common.gui.main.forgotpassword;

import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.TenmaEncrypt;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.VerificationCodeBankModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.net.URLDecoder;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class VerifyChangePasswordFromEmailLink extends TenmaNewPanel {

    private String verificationCode;
    private Panel layout;
    private UserModel selectedUserModel;
    private String verificationMedia;

    private TextField fieldVerification;
    private Button btnViewAccount;
    private Button buttonReset;
    private Button buttonContinue;
    private Button buttonCancel;
    private Panel panelContent;

    public VerifyChangePasswordFromEmailLink(String linkParameter) {
        super();

        param = TA.getCurrent().getParams();


        buttonContinue = new Button(param.getLabel("default.continue"), this);
        buttonContinue.setIcon(new ThemeResource("layouts/images/16/131.png"));

        buttonReset = new Button(param.getLabel("default.resend"), this);
        buttonReset.setIcon(new ThemeResource("layouts/images/16/128.png"));
        buttonReset.setVisible(false);

        buttonCancel = new Button(param.getLabel("default.cancel"), this);
        buttonCancel.setIcon(new ThemeResource("layouts/images/deletered.png"));

        btnViewAccount = new Button(param.getLabel("forgotPassword.gotoLogon"), this);
        btnViewAccount.setVisible(false);

        panelContent = new Panel();
        panelContent.setCaption(param.getLabel("default.forgotPassword"));
        panelContent.setPrimaryStyleName("forgottenPasswordFrame");
        panelContent.setWidth("100%");

        if (initializeUser(linkParameter)) {
            try {
                if (doVerify()) ;
                {
                    System.out.println("VerifyChangePasswordFromEmailLink.VerifyChangePasswordFromEmailLink VERIFIED ----------");
                    ForgotPassword forgotPassword = new ForgotPassword();
                    forgotPassword.dataForgot.put(forgotPassword.USER_MODEL, selectedUserModel);
                    forgotPassword.setForceStageIndex(ForgotPassword.STAGE_INDEX.ENTER_NEW_PASSWORD);
                    UI.getCurrent().setContent(forgotPassword);
                    forgotPassword.doDisplayStage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            doCreateLayoutView();
            createLayoutForgot("forgotPassword.notfound");
        }
    }

    private boolean initializeUser(String linkParameter) {
        boolean bol = false;
        try {
            String code = URLDecoder.decode(linkParameter, "UTF-8");
            System.out.println("VerifyChangePasswordFromEmailLink.initializeUser decode " + code);

            code = TenmaEncrypt.doDecrypt(code);

            System.out.println("VerifyChangePasswordFromEmailLink.initializeUser decrypt " + code);

            String cd[] = code.split("\\|");
            String email = cd[0];
            code = cd[1];

            System.out.println("VerifyChangePasswordFromEmailLink.initializeUser " + email + " " + code);

            UserHelper hlp = new UserHelper();
            if (email.startsWith("0"))
                email = TenmaConverter.getMobileNumber(email);
            else
                email = email.toLowerCase();

            UserModel mdl = hlp.findForgottenPassword(email, TA.getCurrent().getAuthenticationSource());
            if (mdl != null) {
                this.selectedUserModel = mdl;
                this.verificationCode = code;
                bol = true;
            } else {
                doCreateLayoutView();
                createLayoutForgot("forgotPassword.notfound");

            }

        } catch (Exception e) {
            e.printStackTrace();
            bol = false;
            doCreateLayoutView();
            createLayoutForgot("forgotPassword.notfound");
        }
        return bol;
    }

    private boolean doVerify() throws Exception {
        boolean isCont = false;
        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();

        String coded = "-1";
        try {
            coded = verificationCode;
        } catch (NumberFormatException e) {

        }

        VerificationCodeBankModel m = new VerificationCodeBankModel();
        m.setUserId(selectedUserModel.getUserId());
        m.setCodeid(coded);

        m = hlp.getVerificationCodeBankDetail(m);

        if (m != null) {
            Calendar cal = Calendar.getInstance();

            Calendar calCode = Calendar.getInstance();
            calCode.setTime(m.getDateValid());

            if (cal.before(calCode)) {
                deleteNotificationCode(m);
                isCont = true;
            } else {
                doCreateLayoutView();
                panelContent.setContent(createLayoutForgot("forgotPassword.verificationCodeExpired"));
            }
        } else {
            doCreateLayoutView();
            panelContent.setContent(createLayoutForgot("forgotPassword.verificationCodeNotFound"));
        }

        return isCont;
    }

    private int deleteNotificationCode(VerificationCodeBankModel verificationCode) throws Exception {
        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();
        return hlp.deleteVerificationCodeBank(verificationCode);
    }


    private void doCreateLayoutView() {
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
        UI.getCurrent().setContent(this);
        setSizeFull();
        setPrimaryStyleName("wizardBackGround");
    }

    private VerticalLayout createLayoutForgot(String labelCaption) {
        VerticalLayout lay = new VerticalLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);
        lay.setMargin(true);


        StringBuffer buf = new StringBuffer()
                .append("<span class=\"orange\"><b>")
                .append(param.getLabel(labelCaption))
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);

        return lay;
    }

}
