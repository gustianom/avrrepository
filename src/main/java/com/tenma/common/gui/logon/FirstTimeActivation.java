package com.tenma.common.gui.logon;

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.MemberModel;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.verificationcodebank.VerificationCodeBankHelper;
import com.tenma.common.gui.main.useractivation.UserAccountActivation;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.VerificationCodeBankModel;
import com.tenma.model.sms.SMSModel;
import org.apache.ibatis.session.SqlSession;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by gustianom on 06/05/14.
 */
public class FirstTimeActivation {
    private final UserAccountActivation parentComponent;

    public FirstTimeActivation(UserAccountActivation components) {
        parentComponent = components;
    }

    public final void notifyActivation() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("FirstTimeActivation.notifyActivation");
        VerificationCodeBankHelper codeBankHelper = new VerificationCodeBankHelper();
        SqlSession session = codeBankHelper.sqlSessionFactory.openSession();
        try {
            HashMap map = preparingMediaVerificationCode(session);
            session.commit();
            doNotify(map);
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    private HashMap preparingMediaVerificationCode(SqlSession session) {
        String notifyCodePhone = "";
        String notifyCodeEmail = "";

        String lc = TenmaLog.getInstance().openLog();
        try {
            notifyCodePhone = generateVerificationCode(session, Constants.VERIFICATION_MEDIA.BY_PHONE.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            TenmaLog.getInstance().log(lc, TenmaLog.LOG_FOR_INFO, new StringBuilder().append("Send verification code thru phone ").append(TA.getCurrent().getClientInfo().getClientMemberModel().getMobilePhone()).append(" -> result ").append(e.getMessage()).toString());
        }

        try {
            notifyCodeEmail = generateVerificationCode(session, Constants.VERIFICATION_MEDIA.BY_EMAIL.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            TenmaLog.getInstance().log(lc, TenmaLog.LOG_FOR_INFO, new StringBuilder().append("Send verification code thru email ").append(TA.getCurrent().getClientInfo().getClientMemberModel().getEmailAddress()).append(" -> result ").append(e.getMessage()).toString());
        }

        HashMap map = new HashMap();
        map.put(NOTIFICATION_CODE.PHONE.getValue(), notifyCodePhone);
        map.put(NOTIFICATION_CODE.EMAIL.getValue(), notifyCodeEmail);
        return map;
    }

    private final void doNotify(HashMap mapNotifyCode) throws Exception {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("FirstTimeActivation.doNotify " + mapNotifyCode);
        if (mapNotifyCode != null) {
            String phoneCode = mapNotifyCode.containsKey(NOTIFICATION_CODE.PHONE.getValue()) ? (String) mapNotifyCode.get(NOTIFICATION_CODE.PHONE.getValue()) : "";
            String emailCode = mapNotifyCode.containsKey(NOTIFICATION_CODE.EMAIL.getValue()) ? (String) mapNotifyCode.get(NOTIFICATION_CODE.EMAIL.getValue()) : "";
            if (phoneCode != "")
                sendVerificationByPhone(phoneCode, TA.getCurrent().getClientInfo().getClientMemberModel(), TA.getCurrent().getCommunityModel());

            if (emailCode != "")
                sendVerificationByEmail(emailCode, TA.getCurrent().getClientInfo().getClientMemberModel(), TA.getCurrent().getCommunityModel());
        }
    }


    private final void sendVerificationByEmail(String notifyCode, MemberModel memberModel, CommunityModel communityModel) throws Exception {

        HashMap map = new HashMap();
        map.put("userName", memberModel.getEmailAddress());
        map.put("emailAddress", memberModel.getEmailAddress());
        map.put("verificationCode", notifyCode);
        map.put("userFullName", memberModel.getMemberName());
        map.put("communityName", communityModel.getCommunityName());
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendUserNotificationEmail(map, memberModel.getEmailAddress(), Constants.EMAIL_TYPE.NOTIFY_SIGNUP_USER_VERIFICATION_CODE, null, null);
    }

    private final void sendVerificationByPhone(String notifyCode, MemberModel memberModel, CommunityModel communityModel) throws Exception {

        String mylabel = TA.getCurrent().getParams().getLabel("signup.verificationCodeViewByPhone");
        if (communityModel.getCommunityName() != null && communityModel.getCommunityName().trim().length() != 0)
            mylabel = mylabel.replaceFirst("#@@communityName@@#", communityModel.getCommunityName());

        if (memberModel.getMemberName() != null && memberModel.getMemberName().trim().length() != 0)
            mylabel = mylabel.replaceFirst("#@@fullName@@#", memberModel.getMemberName());

        SMSModel sms = new SMSModel();
        sms.setSmsFrom("SYSTEM");
        sms.setSmsTo(memberModel.getMobilePhone().trim());
        sms.setSmsMessage(new StringBuilder().append(mylabel).append(" : ").append(notifyCode).toString());
//        parentComponent.doSendSMSNotification(sms, communityModel.getCommunityId(), share.share.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM);
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        tools.doSendSMSNotification(sms, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM);
    }

    private String generateVerificationCode(SqlSession session, String verificationMedia) throws Exception {
        String notifyCode = TenmaConverter.generateRandomPassword(true, 6);


        VerificationCodeBankHelper codeBankHelper = new VerificationCodeBankHelper();
        VerificationCodeBankModel model = new VerificationCodeBankModel();

        Calendar cal = Calendar.getInstance();
        Calendar calNew = Calendar.getInstance();
        calNew.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

        Object o = TA.getCurrent().getParams().getProperty(Constants.VERIFICATIONCODE_LIFE_TIME_PROPERTY);
        int lf = 5;
        if (o != null) lf = new Integer(String.valueOf(o));

        calNew.add(Calendar.MINUTE, lf);


        model.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        model.setCodeid(notifyCode);
        model.setCreatedDate(cal.getTime());
        model.setDateValid(calNew.getTime());
        model.setUserId(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        model.setVerificationMedia(verificationMedia);

        String lc = TenmaLog.getInstance().openLog();
        try {
            codeBankHelper.insertNewVerificationCodeBank(session, model);
        } catch (Exception e) {
            notifyCode = null;
            e.printStackTrace();
            if (e.getMessage().contains(Constants.ADD_ALREADY_EXIST))
                TenmaLog.getInstance().log(lc, TenmaLog.LOG_FOR_INFO, new StringBuilder().append("Create Verification Code ").append(notifyCode).append(" -> result ").append(e.getMessage()).toString());
            throw new Exception(e.getMessage());
        }
        return notifyCode;
    }


    enum NOTIFICATION_CODE {
        EMAIL("NOTIFICATION_CODE_EMAIL"), PHONE("NOTIFICATION_CODE_PHONE");

        private String methodvalue = "NOTIFICATION_CODE_EMAIL";

        private NOTIFICATION_CODE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }
}
