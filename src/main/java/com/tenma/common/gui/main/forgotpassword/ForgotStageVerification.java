package com.tenma.common.gui.main.forgotpassword;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;

import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.VerificationCodeBankModel;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.Calendar;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class ForgotStageVerification extends TenmaNewPanel implements ForgotInterface {

    private final ForgotPassword parentComponent;
    private Panel layout;
    private UserModel selectedUserModel;
    private String verificationMedia;

    private TextField fieldVerification;

    public ForgotStageVerification(ForgotPassword components) {
        super();
        parentComponent = components;
        this.selectedUserModel = (UserModel) parentComponent.dataForgot.get(components.USER_MODEL);
        this.verificationMedia = (String) parentComponent.dataForgot.get(components.VERIFICATION_MEDIA);
        param = TA.getCurrent().getParams();
        setCompositionRoot(createLayoutForgot());
    }

    private VerticalLayout createLayoutForgot() {
        VerticalLayout lay = new VerticalLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);
        lay.setMargin(true);


        StringBuffer buf = new StringBuffer()
                .append("<span class=\"orange\"><b>")
                .append(param.getLabel("forgotPassword.verificationCodeSentByMedia"))
                .append("</b></span>")
                .append("<span class=\"orange\"><b>")
                .append(" ").append(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia) ? param.getLabel("default.mobilePhone") : param.getLabel("default.email"))
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);


        buf = new StringBuffer()
                .append(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia) ? param.getLabel("forgotPassword.sentVerificationCodeMessageBySMS") : param.getLabel("forgotPassword.sentVerificationCodeMessageByEmail"))
                .append(" ")
                .append(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia) ? TenmaConverter.formatSecuredAccountName(selectedUserModel.getMobilePhone()) : TenmaConverter.formatSecuredAccountName(selectedUserModel.getEmailAddress()));

        Label label = new Label(buf.toString());

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(label);
        lay.addComponent(new Label());
        lay.addComponent(createVerificationEntry());

        return lay;
    }

    private Component createVerificationEntry() {
        HorizontalLayout ly = new HorizontalLayout();
        ly.setSpacing(true);
        ly.setWidth("100%");

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setWidth("100%");

        Label lb = new Label(param.getLabel("forgotPassword.enterVerificationCode"));
        fieldVerification = new TextField();
        fieldVerification.setMaxLength(6);
        fieldVerification.setWidth("50px");
        fieldVerification.setRequired(true);
        fieldVerification.setInputPrompt(param.getLabel("forgotPassword.verificationCode"));
        fieldVerification.setDescription(param.getLabel("forgotPassword.verificationCode"));
        fieldVerification.addShortcutListener(new ShortcutListener("textSeacrList" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                parentComponent.doButtonContinue();
            }
        });

        HorizontalLayout layVer = new HorizontalLayout(lb, fieldVerification);
        layVer.setWidth("100%");
        layVer.setSpacing(true);

        vl.addComponent(layVer);
        ly.addComponent(vl);
        ly.addComponent(new Label(param.getLabel("forgotPassword.messageVerificationCodeEntry")));
        return ly;
    }

    private int deleteNotificationCode(VerificationCodeBankModel verificationCode) throws Exception {
        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();
        return hlp.deleteVerificationCodeBank(verificationCode);
    }

    public boolean saveCurrentStage() throws Exception {
        boolean isCont = false;
        String vl = fieldVerification.getValue();
        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();

        String coded = "-1";
        try {
            coded = vl;
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
                Notification.show(param.getLabel("forgotPassword.verificationCodeExpired"), Notification.Type.HUMANIZED_MESSAGE);
                parentComponent.getButtonReset().setVisible(true);

            }
        } else {
            parentComponent.getButtonReset().setVisible(false);
            Notification.show(param.getLabel("forgotPassword.verificationCodeNotFound"), Notification.Type.HUMANIZED_MESSAGE);
        }
        return isCont;
    }

    public void doFieldFocus() {

    }


}
