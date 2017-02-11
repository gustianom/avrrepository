package com.tenma.common.gui.main.useractivation;

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.main.forgotpassword.ForgotInterface;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.VerificationCodeBankModel;
import com.tenma.model.sms.SMSModel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 1:34 PM
 */
public class ResendVerification extends TenmaNewPanel implements ForgotInterface {
    private final UserAccountActivation parentComponent;
    private UserModel selectedUserModel;
    private MemberModel selectedMemberModel;
    private OptionGroup selectionVerificationOption;


    public ResendVerification(UserAccountActivation components) {
        super();

        parentComponent = components;
        selectedUserModel = (UserModel) components.dataVerification.get(components.USER_MODEL);
        selectedMemberModel = (MemberModel) components.dataVerification.get(components.MEMBER_MODEL);
        param = TA.getCurrent().getParams();
        setCompositionRoot(createLayoutForgot());

        String mobPhone = selectedMemberModel.getMobilePhone();
        String emailAddress = selectedMemberModel.getEmailAddress();
        if (!((mobPhone != null) && (mobPhone.trim().length() != 0))) {
            try {
                dosendVerification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private VerticalLayout createLayoutForgot() {
        VerticalLayout lay = new VerticalLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);
        lay.setMargin(true);


        StringBuffer buf = new StringBuffer()
                .append("<span class=\"orange\"><b>")
                .append(param.getLabel("userverification.resendVerificationCode"))
                .append("</b></span>")
                .append("<span class=\"orange\"><b>")
                .append(" ").append(selectedMemberModel.getMemberName())
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);
        lay.addComponent(new Label());
        lay.addComponent(new Label());
        Label label = new Label(param.getLabel("userverification.selectVerificationMedia"));
        lay.addComponent(label);
        lay.addComponent(creatVerificationSelection());

        return lay;
    }

    private Component creatVerificationSelection() {
        String mobPhone = selectedMemberModel.getMobilePhone();
        String emailAddress = selectedMemberModel.getEmailAddress();
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.setWidth("100%");

        if ((mobPhone != null) && (mobPhone.trim().length() != 0)) {
            selectionVerificationOption = new OptionGroup();
            selectionVerificationOption.addItem(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue());
            selectionVerificationOption.addItem(Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue());

            selectionVerificationOption.setItemCaption(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue(), new StringBuffer().append(param.getLabel("forgotPassword.verificationMediaByPhone")).append(" : ").append(TenmaConverter.formatSecuredAccountName(mobPhone)).toString());
            selectionVerificationOption.setItemCaption(Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue(), new StringBuffer().append(param.getLabel("forgotPassword.verificationMediaByEmail")).append(" : ").append(TenmaConverter.formatSecuredAccountName(emailAddress)).toString());
            ly.addComponent(selectionVerificationOption);
        }
        return ly;
    }


    private void dosendVerification() throws Exception {
        String verificationMedia = Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue();
        if (selectionVerificationOption != null)
            verificationMedia = (String) selectionVerificationOption.getValue();

        String notifyCode = TenmaConverter.generateRandomPassword(true, 6);

        VerificationCodeBankHelper codeBankHelper = new VerificationCodeBankHelper();
        VerificationCodeBankModel model = new VerificationCodeBankModel();

        Calendar cal = Calendar.getInstance();
        Calendar calNew = Calendar.getInstance();
        calNew.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

        Object o = param.getProperty(Constants.VERIFICATIONCODE_LIFE_TIME_PROPERTY);
        int lf = 5;
        if (o != null) lf = new Integer(String.valueOf(o));

        calNew.add(Calendar.MINUTE, lf);


        model.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        model.setCodeid(notifyCode);
        model.setCreatedDate(cal.getTime());
        model.setDateValid(calNew.getTime());
        model.setUserId(selectedUserModel.getUserId());
        model.setVerificationMedia(verificationMedia);

        boolean isContinue = false;
        String lc = TenmaLog.getInstance().openLog();
        try {
            codeBankHelper.insertNewVerificationCodeBank(model);
            isContinue = true;
        } catch (Exception e) {
            if (e.getMessage().contains(Constants.ADD_ALREADY_EXIST))

                TenmaLog.getInstance().log(lc, TenmaLog.LOG_FOR_INFO, new StringBuffer().append("Create Verification Code ").append(notifyCode).append(" -> result ").append(e.getMessage()).toString());
        }
        if (isContinue) {

            if (Constants.VERIFICATION_MEDIA.BY_PHONE.getValue().equals(verificationMedia)) {
                sendVerificationByPhone(notifyCode);
            } else
                sendVerificationByEmail(notifyCode);
        }
    }

    private void sendVerificationByEmail(String notifyCode) throws Exception {

        CommunityModel communityModel = new CommunityModel();
        communityModel.setCommunityId(selectedUserModel.getDefCommunityId());

        CommunityHelper hml = new CommunityHelper();
        communityModel = hml.getCommunityDetail(communityModel);
        String communityName = "";
        if (communityModel != null) communityName = communityModel.getCommunityName();

        HashMap map = new HashMap();
        map.put("emailAddress", selectedMemberModel.getEmailAddress());
        map.put("verificationCode", notifyCode);
        map.put("userFullName", selectedMemberModel.getMemberName());
        map.put("communityName", communityName);
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendUserNotificationEmail(map, selectedMemberModel.getEmailAddress(), Constants.EMAIL_TYPE.VERIFY_RESET_PASSWORD, null, null);
    }

    private void sendVerificationByPhone(String notifyCode) throws Exception {

        CommunityModel communityModel = new CommunityModel();
        communityModel.setCommunityId(selectedUserModel.getDefCommunityId());

        CommunityHelper hml = new CommunityHelper();
        communityModel = hml.getCommunityDetail(communityModel);
        String communityName = "";
        if (communityModel != null) communityName = communityModel.getCommunityName();

        String mylabel = param.getLabel("signup.verificationCodeViewByPhone");
        mylabel = mylabel.replaceFirst("#@@communityName@@#", communityName);

        SMSModel sms = new SMSModel();
        sms.setSmsFrom("SYSTEM");
        sms.setSmsTo(selectedMemberModel.getMobilePhone().trim());
        sms.setSmsMessage(new StringBuffer().append(mylabel).append(" : ").append(notifyCode).toString());
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendSMSNotification(sms, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM);
    }

    @Override
    public boolean saveCurrentStage() throws Exception {
        boolean isCont = true;
        dosendVerification();

        String verificationMedia = Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue();
        if (selectionVerificationOption != null)
            verificationMedia = (String) selectionVerificationOption.getValue();

        parentComponent.dataVerification.put(parentComponent.VERIFICATION_MEDIA, verificationMedia);
        return true;
    }

    public void doFieldFocus() {
    }
}
