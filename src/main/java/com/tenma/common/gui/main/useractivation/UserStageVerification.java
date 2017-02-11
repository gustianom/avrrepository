package com.tenma.common.gui.main.useractivation;

import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.main.forgotpassword.ForgotInterface;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.VerificationCodeBankModel;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class UserStageVerification extends TenmaNewPanel implements ForgotInterface {

    private final UserAccountActivation parentComponent;
    private MemberModel selectedMemberModel;
    private Panel layout;
    private UserModel selectedUserModel;
    private String verificationMedia;

    private TextField fieldVerification;

    public UserStageVerification(UserAccountActivation components) {
        super();
        parentComponent = components;
        this.selectedUserModel = (UserModel) parentComponent.dataVerification.get(components.USER_MODEL);
        this.selectedMemberModel = (MemberModel) parentComponent.dataVerification.get(components.MEMBER_MODEL);
        this.verificationMedia = (String) parentComponent.dataVerification.get(components.VERIFICATION_MEDIA);
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
                .append(param.getLabel("userverification.userAccountNotActive"))
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);


        String byPhoneTitle = param.getLabel("forgotPassword.sentVerificationCodeMessageBySMS");
        String byEmailTitle = param.getLabel("forgotPassword.sentVerificationCodeMessageByEmail");

        String formatPhone = TenmaConverter.formatSecuredAccountName(selectedMemberModel.getMobilePhone());
        String formatEmail = TenmaConverter.formatSecuredAccountName(selectedMemberModel.getEmailAddress());

        buf = new StringBuffer();

        if ((verificationMedia != null) && (verificationMedia.trim().length() != 0)) {
            buf.append(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia) ? byPhoneTitle : byEmailTitle)
                    .append(" ")
                    .append(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia) ? formatPhone : formatEmail);
        } else {
            buf.append(byPhoneTitle).append(" ").append(formatPhone)
                    .append(" ")
                    .append(param.getLabel("default.and"))
                    .append(" ")
                    .append(param.getLabel("default.email")).append(" ").append(formatEmail);
        }
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
                parentComponent.getButtonContinue().click();
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

    private int deleteNotificationCode(String verificationCode) throws Exception {
        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();
        VerificationCodeBankModel tmp = new VerificationCodeBankModel();
        tmp.setUserId(selectedUserModel.getUserId());
        tmp.setCodeid(verificationCode);
        return hlp.deleteVerificationCodeBank(tmp);
    }

    //    @Override
    public boolean saveCurrentStage() throws Exception {
        boolean isCont = false;
        String vl = fieldVerification.getValue();
        String cde = "-1";
        try {
            cde = vl;
        } catch (NumberFormatException e) {
        }

        VerificationCodeBankHelper hlp = new VerificationCodeBankHelper();


        VerificationCodeBankModel m = new VerificationCodeBankModel();
        m.setUserId(selectedUserModel.getUserId());
        m.setCodeid(cde);

        m = hlp.getVerificationCodeBankDetail(m);
        if (m != null) {
            Calendar cal = Calendar.getInstance();

            Calendar calCode = Calendar.getInstance();
            calCode.setTime(m.getDateValid());

            if (cal.before(calCode)) {
                deleteNotificationCode(vl);

                isCont = doActivateUserAccount(m);
                if (isCont)
                    viewUpdateSuccess();
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

    private boolean doActivateUserAccount(VerificationCodeBankModel model) throws Exception {
        UserHelper hlp = new UserHelper();
        SqlSession session = hlp.sqlSessionFactory.openSession();

        UserModel m = new UserModel();
        m.setUserId(selectedUserModel.getUserId());
        m.setUpdatedBy(selectedUserModel.getUserId());
        m.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
        m.setCreatedBy(selectedUserModel.getUserId());
        m.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        m.setUserStatus(Constants.USER_STATUS.ACTIVE.getValue());
        m.setUserRegistrationStatus(Constants.USER_REGISTRATION_STATUS.ACCOUNT_ACTIVATED.getValue());

        int res = 0;
        try {
            MemberModel mModel = new MemberModel();
            mModel.setMemberId(selectedMemberModel.getMemberId());
            mModel.setUpdatedBy(selectedUserModel.getUserId());
            mModel.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
            mModel.setCreatedBy(selectedUserModel.getUserId());
            mModel.setCreatedFrom(TA.getCurrent().getRemoteAddress());

            if (Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue().equals(model.getVerificationMedia()))
                mModel.setEmailStatusActive(true);
            else if (Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(model.getVerificationMedia()))
                mModel.setMobileStatusActive(true);

            res = hlp.updateUser(session, m);
            MemberHelper memberHelper = new MemberHelper();
            memberHelper.updateMember(session, mModel);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        //update phone status or email status
        if (res != 0) return true;
        else return false;
    }

    public void doFieldFocus() {

    }

    private void viewUpdateSuccess() {
        boolean isContinue = true;
//        remark do mandatory system task not finalzed.
//        boolean isContinue = getTenmaApplication().doCheckMandatorySystemTask();
        if (isContinue)
            viewAccountVerificationSuccess();
    }

    private void viewAccountVerificationSuccess() {
        VerticalLayout ly = new VerticalLayout();
        ly.setWidth("100%");
        ly.setSpacing(true);
        ly.setMargin(true);

        ly.addComponent(new Label());
        ly.addComponent(new Label());
        ly.addComponent(new Label(param.getLabel("userverification.sucessfullyActivateAcccount")));
        ly.addComponent(new Label(param.getLabel("forgotPassword.sucessfullyResetPasswordMessage")
                .replaceFirst("#@@emailAddress@@#", selectedMemberModel.getEmailAddress())
                .replaceFirst("#@@userFullName@@#", selectedMemberModel.getMemberName()))
        );
        parentComponent.getButtonContinue().setVisible(false);
        parentComponent.getBtnViewAccount().setVisible(true);
        parentComponent.getBtnGoLogout().setVisible(true);
        setCompositionRoot(ly);
    }
}
