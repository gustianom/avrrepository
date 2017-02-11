package com.tenma.common.gui.main.forgotpassword;

import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.auth.util.logon.TenmaEncrypt;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.community.CommunityMemberHelper;
import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.tenma.common.util.Params;
import com.tenma.common.util.TenmaConverter;
import com.tenma.common.util.web.ServerUtility;
import com.tenma.model.common.VerificationCodeBankModel;
import com.tenma.model.sms.SMSModel;
import com.tenma.sms.bean.smscommunityprofile.SmsCommunityProfileHelper;
import com.tenma.sms.model.SmsCommunityProfileModel;
import com.tenma.util.SMS_Constants;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 1:34 PM
 */
public class ForgotStage2 extends TenmaNewPanel implements ForgotInterface {
    private final ForgotPassword parentComponent;
    private final String domainName;
    private SmsCommunityProfileModel smsCommunityProfileModel;
    private UserModel selectedUserModel;
    private OptionGroup selectionVerificationOption;


    public ForgotStage2(ForgotPassword components) {
        super();
        parentComponent = components;

        selectedUserModel = (UserModel) components.dataForgot.get(components.USER_MODEL);
        smsCommunityProfileModel = getSMSProfile(selectedUserModel);

        HttpServletRequest request = (HttpServletRequest) VaadinService.getCurrentRequest();

        domainName = new StringBuffer()
                .append(request.getScheme()).append("://")
                .append(request.getServerName())
                .append(request.getServerPort() == 80 ? "" : ":")
                .append(request.getServerPort() == 80 ? "" : request.getServerPort())
                .append(request.getContextPath())
                .toString();


        param = Params.getInstance(getLocale());
        setCompositionRoot(createLayoutForgot());

//        String mobPhone = selectedUserModel.getMobilePhone();
//        String emailAddress = selectedUserModel.getEmailAddress();
        //if (!((mobPhone != null) && (mobPhone.trim().length() != 0)))

    }

    private VerticalLayout createLayoutForgot() {
        VerticalLayout lay = new VerticalLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);
        lay.setMargin(true);


        StringBuffer buf = new StringBuffer()
                .append("<span class=\"orange\"><b>")
                .append(param.getLabel("forgotPassword.passwordHelpFor"))
                .append("</b></span>")
                .append("<span class=\"orange\"><b>")
                .append(" ").append(selectedUserModel.getUserFullName())
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);
        lay.addComponent(new Label());
        lay.addComponent(new Label());
        Label label = new Label(param.getLabel("forgotPassword.selectVerificationMedia"));
        lay.addComponent(label);
        lay.addComponent(creatVerificationSelection());

        return lay;
    }

    private Component creatVerificationSelection() {
        String mobPhone = selectedUserModel.getMobilePhone();
        String emailAddress = selectedUserModel.getEmailAddress();
        VerticalLayout ly = new VerticalLayout();
        ly.setSpacing(true);
        ly.setWidth("100%");

        selectionVerificationOption = new OptionGroup();
        if ((mobPhone != null) && (mobPhone.trim().length() != 0)) {
            if (smsCommunityProfileModel != null) {
                selectionVerificationOption.addItem(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue());
                selectionVerificationOption.setItemCaption(Constants.VERIFICATION_MEDIA.BY_PHONE.getValue(), new StringBuffer().append(param.getLabel("forgotPassword.verificationMediaByPhone")).append(" : ").append(TenmaConverter.formatSecuredAccountName(mobPhone)).toString());
            }
        }
        selectionVerificationOption.addItem(Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue());
        selectionVerificationOption.setItemCaption(Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue(), new StringBuffer().append(param.getLabel("forgotPassword.verificationMediaByEmail")).append(" : ").append(TenmaConverter.formatSecuredAccountName(emailAddress)).toString());
        ly.addComponent(selectionVerificationOption);
        return ly;
    }

    private SmsCommunityProfileModel getSMSProfile(UserModel model) {
        SmsCommunityProfileModel smsProfileModel = null;
        SmsCommunityProfileHelper helper = new SmsCommunityProfileHelper();
        if (model.getDefCommunityId() != null && model.getDefCommunityId().trim().length() != 0) {
            smsProfileModel = new SmsCommunityProfileModel();
            smsProfileModel.setCommunityId(model.getDefCommunityId());
            smsProfileModel = helper.getSmsCommunityProfileDetail(smsProfileModel);
        }
        boolean isAvailable = false;
        if (smsProfileModel != null) {

            SMS_Constants.SMS_PROFILE_CODE sCode = getSMSPRofile(smsProfileModel);
            if (SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY.equals(sCode))
                isAvailable = true;
        }

        if (!isAvailable) {
            CommunityMemberModel memberModel = new CommunityMemberModel();
            memberModel.setMemberId(model.getMemberId());

            CommunityMemberHelper memberHelper = new CommunityMemberHelper();
            HashMap map = new HashMap();
            map.put("memberId", model.getMemberId());

            List<CommunityMemberModel> ls = memberHelper.getListRenderer(map, false);
            if (ls != null && ls.size() != 0)
                for (CommunityMemberModel cm : ls) {
                    if (!cm.getCommunityId().equals(model.getDefCommunityId())) {
                        smsProfileModel.setCommunityId(model.getDefCommunityId());
                        smsProfileModel = helper.getSmsCommunityProfileDetail(smsProfileModel);
                        SMS_Constants.SMS_PROFILE_CODE sCode = getSMSPRofile(smsProfileModel);
                        if (SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY.equals(sCode)) {
                            isAvailable = true;
                            break;
                        }
                    }
                }
        }
        return smsProfileModel;

    }

    private SMS_Constants.SMS_PROFILE_CODE getSMSPRofile(SmsCommunityProfileModel smsProfileModel) {
        SMSModel model = new SMSModel();
        model.setSmsMessage("123455667");
        return ServerUtility.doCheckProfileEligibleSMS(smsProfileModel, model, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM);
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

        String encryptedEmail = new StringBuffer().append(selectedUserModel.getEmailAddress()).append("|").append(notifyCode).toString();
        encryptedEmail = TenmaEncrypt.doEncrypt(encryptedEmail);
        encryptedEmail = URLEncoder.encode(encryptedEmail, "UTF-8");

        StringBuilder verifyLink = new StringBuilder()
                .append(domainName)
                .append("/main/verifychpwd?code=").append(encryptedEmail);

        HashMap map = new HashMap();
        map.put("emailAddress", selectedUserModel.getEmailAddress());
        map.put("verificationCode", notifyCode);
        map.put("userFullName", selectedUserModel.getUserFullName());
        map.put("communityName", communityName);
        map.put("verifyLink", verifyLink.toString());
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendUserNotificationEmail(map, selectedUserModel.getEmailAddress(), Constants.EMAIL_TYPE.VERIFY_RESET_PASSWORD, null, null);
    }

    private void sendVerificationByPhone(String notifyCode) throws Exception {

        CommunityModel communityModel = new CommunityModel();
        communityModel.setCommunityId(selectedUserModel.getDefCommunityId());

        CommunityHelper hml = new CommunityHelper();
        communityModel = hml.getCommunityDetail(communityModel);
        String communityName = "";
        if (communityModel != null) communityName = communityModel.getCommunityName();

        String mylabel = param.getLabel("forgotPassword.verificationCodeViewByPhone");
        mylabel = mylabel.replaceFirst("#@@fullName@@#", selectedUserModel.getUserFullName());
        mylabel = mylabel.replaceFirst("#@@communityName@@#", communityName);

        SMSModel sms = new SMSModel();
        sms.setSmsFrom("SYSTEM");
        sms.setSmsTo(selectedUserModel.getMobilePhone().trim());
        sms.setSmsMessage(new StringBuffer().append(mylabel).append(" : ").append(notifyCode).toString());
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendSMSNotification(sms, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM);
    }

    public boolean saveCurrentStage() throws Exception {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("ForgotStage2.saveCurrentStage");
        boolean isCont = true;
        dosendVerification();

        String verificationMedia = Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue();
        if (selectionVerificationOption != null)
            verificationMedia = (String) selectionVerificationOption.getValue();

        parentComponent.dataForgot.put(parentComponent.VERIFICATION_MEDIA, verificationMedia);
        return true;
    }

    public void doFieldFocus() {
    }
}
