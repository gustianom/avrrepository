package com.tenma.util.sms.services;

import com.tenma.model.sms.SMSModel;
import com.tenma.model.sms.SMSProviderResponseModel;
import com.tenma.model.sms.SMSUndeliveredModel;
import com.tenma.sms.bean.smscommunityprofile.SmsCommunityProfileHelper;
import com.tenma.sms.bean.smsoutbound.SmsOutboundHelper;
import com.tenma.sms.bean.smsundelivered.SMSUndeliveredHelper;
import com.tenma.sms.model.SmsCommunityProfileModel;
import com.tenma.sms.model.SmsOutboundModel;
import com.tenma.util.SMS_Constants;
import com.tenma.util.sms.Converter;
import org.apache.ibatis.session.SqlSession;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gustianom on 08/05/14.
 */
public class SMSUtil {
    /**
     * <p/> Description of SMS Source Type
     * <p/> SMS_Constants.SMS_SOURCE_TYPE.SYSTEM is SMS from system, the quota, system will deduct quota when SMS_SYSTEM_DEDUCTED field on SMS Community Profile is 1 (YES)
     * <p/> SMS_Constants.SMS_SOURCE_TYPE.NORMAL system will normally deduct quota of sms
     *
     * @param creatorUserId
     * @param remoteIPAddress
     * @param model
     * @param communityId
     * @param smsSourceType
     * @throws Exception
     */
    public static SMS_Constants.SMS_RESPONSE_CODE sendSms(final String creatorUserId, final String remoteIPAddress, final SMSModel model, final String communityId, final SMS_Constants.SMS_SOURCE_TYPE smsSourceType) throws Exception {
        //check sms profile
        SmsCommunityProfileHelper help = new SmsCommunityProfileHelper();
        SMS_Constants.SMS_RESPONSE_CODE smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.FAILED_COMMUNITY_NO_SMS_PROFILE;

        boolean isContinue = false;

        SmsCommunityProfileModel tmpProf = new SmsCommunityProfileModel();
        tmpProf.setCommunityId(communityId);

        final SmsCommunityProfileModel profileModel = help.getSmsCommunityProfileDetail(tmpProf);

        boolean checkEligible = false;

//        if(SMS_Constants.DEFAULT_SINGUP_COMMUNITY_ID.equals(communityId))
//            communityId = SMS_Constants.TENMA_COMMUNITY_ID;
//        else if(SMS_Constants.DEFAULT_SINGUP_CORPORATE_ID.equals(communityId))
//            communityId = SMS_Constants.TENMA_COMMUNITY_ID;

        if (SMS_Constants.TENMA_COMMUNITY_ID.equals(communityId) || SMS_Constants.DEFAULT_SINGUP_COMMUNITY_ID.equals(communityId) || SMS_Constants.DEFAULT_SINGUP_CORPORATE_ID.equals(communityId)) {
            checkEligible = true;
            smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.DELIVERED;
        } else {

            checkEligible = false;
            SMS_Constants.SMS_PROFILE_CODE smsProfileCode = doCheckProfileEligibleSMS(profileModel, model, smsSourceType);
            if (smsProfileCode.equals(SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY)) {
                checkEligible = true;
                smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.DELIVERED;
            } else if (smsProfileCode.equals(SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY_SMS_THRESHOLD_REACHED)) {
                checkEligible = true;
                smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.DELIVERED_AND_TRESHOLD_REACHED;
            } else if (smsProfileCode.equals(SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_NO_SMS_PROFILE))
                smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.FAILED_COMMUNITY_NO_SMS_PROFILE;
            else if (smsProfileCode.equals(SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY))
                smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY;
            else if (smsProfileCode.equals(SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY_AND_REACH_CREDIT_LIMIT))
                smsReponseCode = SMS_Constants.SMS_RESPONSE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY_AND_REACH_CREDIT_LIMIT;
        }

        final boolean isEligibleSMS = checkEligible;

        System.out.println("SMSUtil.sendSms " + checkEligible);

        isContinue = isEligibleSMS;

        final SMS_Constants.SMS_RESPONSE_CODE[] rescode = {null};
        if (isContinue) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        model.setDeliveryDate(Calendar.getInstance().getTime());
                        String response = SMSProviderUtil.sendSms(model);
                        doParsingReponse(creatorUserId, remoteIPAddress, response, model, profileModel, smsSourceType, isEligibleSMS);
                    } catch (FileNotFoundException fl) {
                        rescode[0] = SMS_Constants.SMS_RESPONSE_CODE.SMS_SERVICES_NOT_FOUND;
                        registerUndeliveredSMS(creatorUserId, remoteIPAddress, model, profileModel, smsSourceType);
                    } catch (UnknownHostException fl) {
                        rescode[0] = SMS_Constants.SMS_RESPONSE_CODE.SMS_SERVICES_NOT_FOUND;
                        registerUndeliveredSMS(creatorUserId, remoteIPAddress, model, profileModel, smsSourceType);
                    } catch (Exception fl) {
                        fl.printStackTrace();
                    }
                }
            }).start();
        }
        if (rescode[0] != null)
            smsReponseCode = rescode[0];
        return smsReponseCode;
    }

    private static void registerUndeliveredSMS(String creatorUserId, String remoteIPAddress, SMSModel model, SmsCommunityProfileModel profile, SMS_Constants.SMS_SOURCE_TYPE smsSourceType) {
        SMSUndeliveredModel undeliveredModel = new SMSUndeliveredModel();

        undeliveredModel.setCommunityId(profile != null ? profile.getCommunityId() : SMS_Constants.TENMA_COMMUNITY_ID);
        undeliveredModel.setClientCommunityId(profile != null ? profile.getCommunityId() : SMS_Constants.TENMA_COMMUNITY_ID);
        undeliveredModel.setSmsFrom(model.getSmsFrom());
        undeliveredModel.setSmsTo(model.getSmsTo());
        undeliveredModel.setSmsMessage(model.getSmsMessage());

        undeliveredModel.setSmsType(smsSourceType.getValue());

        String idCreator = SMS_Constants.SYSTEM;
        if (smsSourceType.equals(SMS_Constants.SMS_SOURCE_TYPE.NORMAL))
            if ((creatorUserId != null) && (creatorUserId.trim().length() != 0))
                idCreator = creatorUserId;

        String remoteIP = SMS_Constants.SYSTEM;
        if ((remoteIPAddress != null) && (remoteIPAddress.trim().length() != 0))
            remoteIP = remoteIPAddress;

        undeliveredModel.setSmsDeliveredDate(model.getDeliveryDate());
        undeliveredModel.setSmsDeliveredDate(model.getDeliveryDate());
        undeliveredModel.setCreatedFrom(remoteIP);
        undeliveredModel.setUpdatedFrom(remoteIP);
        undeliveredModel.setCreatedBy(idCreator);
        undeliveredModel.setUpdatedBy(idCreator);

        SMSUndeliveredHelper hlp = new SMSUndeliveredHelper();
        try {
            hlp.insertNewUndeliveredSms(undeliveredModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SmsCommunityProfileModel getCommunitySMSProfile(String communityId) throws Exception {
        SmsCommunityProfileHelper help = new SmsCommunityProfileHelper();
        SmsCommunityProfileModel tmpProf = new SmsCommunityProfileModel();
        tmpProf.setCommunityId(communityId);

        SmsCommunityProfileModel profileModel = help.getSmsCommunityProfileDetail(tmpProf);
        return profileModel;
    }

    public static SMS_Constants.SMS_PROFILE_CODE doCheckProfileEligibleSMS(SmsCommunityProfileModel profileModel, SMSModel model, SMS_Constants.SMS_SOURCE_TYPE smsSourceType) {

        int countSMS = Converter.countSMS(model.getSmsMessage());
        SMS_Constants.SMS_PROFILE_CODE smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_NO_SMS_PROFILE;

        boolean taskGenerate = true;

        if (SMS_Constants.SMS_SOURCE_TYPE.SYSTEM.getValue() == smsSourceType.getValue())
            smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY;
        else
            try {
                if (profileModel != null) {
                    if (profileModel.getSmsSubscribtionType().intValue() != SMS_Constants.SMS_PROFILE_SUBSCRIBTION_TYPE.NO_SMS.getValue()) {

                        int balamt = profileModel.getSmsCurrentBalance().intValue() - countSMS;

                        //System.out.println();
                        //System.out.println();
                        //System.out.println();
                        //System.out.println();
                        //System.out.println("ServerUtility.doCheckProfileEligibleSMS Balance " + balamt);
                        //System.out.println("profileModel.getSmsThresholdValue() = " + profileModel.getSmsThresholdValue());
                        //System.out.println("profileModel.getSmsBalanceThreshold().intValue = " + profileModel.getSmsBalanceThreshold().intValue());


                        if (balamt >= 0) {
                            smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY;
                            if (profileModel.getSmsThresholdValue().intValue() <= balamt) {
                                if (profileModel.getSmsBalanceThreshold().intValue() == SMS_Constants.DEFAULT_SYSTEM_OPTION.YES.getValue())
                                    smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY_SMS_THRESHOLD_REACHED;
                            }
                        } else {
                            smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY;
                            if (profileModel.getSmsSubscribtionType().intValue() != SMS_Constants.SMS_PROFILE_SUBSCRIBTION_TYPE.PROMO.getValue()) {
                                if (profileModel.getSmsPaymentType().intValue() == SMS_Constants.SMS_PROFILE_PAYMENT_TYPE.POSTPAID.getValue()) {
                                    if (profileModel.getSmsCreditLimit().intValue() >= countSMS) {
                                        smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.AVAILABLE_FOR_DELIVERY;
                                    } else {
                                        //doNotifyTaskBalanceSMS(Task.GENERATE.SMS_BALANCE_EMPTY, profileModel.getCommunityId(), profileModel.getUserId(), taskGenerate);
                                        smsProfileCode = SMS_Constants.SMS_PROFILE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY_AND_REACH_CREDIT_LIMIT;
                                    }
                                }
                                //else doNotifyTaskBalanceSMS(Task.GENERATE.SMS_BALANCE_EMPTY, profileModel.getCommunityId(), profileModel.getUserId(), taskGenerate);
                            }
                        }
                    }
                } else {
                    //DO NOT NOTIFY SMS , because there is/are community with sms off flag
                    //doNotifyTaskBalanceSMS(locale, communityId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        return smsProfileCode;
    }


    private static void doParsingReponse(String creatorUserId, String remoteIPAddress, String response, SMSModel model, SmsCommunityProfileModel profile, SMS_Constants.SMS_SOURCE_TYPE smsSourceType, boolean eligibleSMS) {

        SmsOutboundModel outboundModel = new SmsOutboundModel();
        outboundModel.setCommunityId(profile != null ? profile.getCommunityId() : SMS_Constants.TENMA_COMMUNITY_ID);
        outboundModel.setClientId(profile != null ? profile.getCommunityId() : SMS_Constants.TENMA_COMMUNITY_ID);
        outboundModel.setSmsFrom(model.getSmsFrom());
        outboundModel.setSmsTo(model.getSmsTo());
        outboundModel.setSmsMessage(model.getSmsMessage());

        outboundModel.setSmsType(smsSourceType.getValue());

        String idCreator = SMS_Constants.SYSTEM;
        if (smsSourceType.equals(SMS_Constants.SMS_SOURCE_TYPE.NORMAL))
            if ((creatorUserId != null) && (creatorUserId.trim().length() != 0))
                idCreator = creatorUserId;

        String remoteIP = SMS_Constants.SYSTEM;
        if ((remoteIPAddress != null) && (remoteIPAddress.trim().length() != 0))
            remoteIP = remoteIPAddress;

        outboundModel.setCreatedFrom(remoteIP);
        outboundModel.setUpdatedFrom(remoteIP);
        outboundModel.setCreatedBy(idCreator);
        outboundModel.setUpdatedBy(idCreator);


        SmsOutboundHelper helper = new SmsOutboundHelper();
        SqlSession session = helper.sqlSessionFactory.openSession();

        try {

            SMSProviderResponseModel rmodel = translateProviderResponse(response);
            if (rmodel != null) {
                if (rmodel.getStatus().intValue() != -1) {  // success
                    outboundModel.setProviderDeliveryId(rmodel.getMessageId());
                    outboundModel.setDeliveryStatus(SMS_Constants.SMS_PROFILE_DELIVERY_STATUS.SEND.getValue());
                    if (SMS_Constants.SMS_SOURCE_TYPE.SYSTEM.equals(smsSourceType)) {
                        if (profile != null) {
                            if (profile.getSmsSystemDeducted().intValue() == SMS_Constants.DEFAULT_SYSTEM_OPTION.YES.getValue())
                                insertIntoSMSOutboundTransaction(session, outboundModel, profile, true);
                            else {
                                outboundModel.setCommunityId(SMS_Constants.TENMA_COMMUNITY_ID);
                                insertIntoSMSOutboundTransaction(session, outboundModel, profile, false); // inser outbound for tenma
                            }
                        } else
                            insertIntoSMSOutboundTransaction(session, outboundModel, profile, false);   // insert outbound for Tenma
                    } else {
                        outboundModel.setDeliveryStatus(SMS_Constants.SMS_PROFILE_DELIVERY_STATUS.SEND.getValue());
                        insertIntoSMSOutboundTransaction(session, outboundModel, profile, true);
                    }
                } else {
                    if (SMS_Constants.SMS_SOURCE_TYPE.SYSTEM.equals(smsSourceType))
                        outboundModel.setCommunityId(SMS_Constants.TENMA_COMMUNITY_ID); // delivered by tenma system

                    outboundModel.setDeliveryStatus(SMS_Constants.SMS_PROFILE_DELIVERY_STATUS.PROVIDER_FAILED_SEND.getValue());
                    insertIntoSMSOutboundTransaction(session, outboundModel, profile, false);
                }
            } else {
                if (SMS_Constants.SMS_SOURCE_TYPE.SYSTEM.equals(smsSourceType))
                    outboundModel.setCommunityId(SMS_Constants.TENMA_COMMUNITY_ID); // delivered by tenma system
                outboundModel.setDeliveryStatus(SMS_Constants.SMS_PROFILE_DELIVERY_STATUS.PROVIDER_FAILED_SEND.getValue());
                insertIntoSMSOutboundTransaction(session, outboundModel, profile, false);
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void insertIntoSMSOutboundTransaction(SqlSession session, SmsOutboundModel outboundModel, SmsCommunityProfileModel profile, boolean isDeductSMS) throws Exception {
        //System.out.println("ServerUtility.insertIntoSMSOutboundTransaction with Deduction " + isDeductSMS);

        SmsOutboundHelper helper = new SmsOutboundHelper();

        int countSMS = Converter.countSMS(outboundModel.getSmsMessage());


        boolean isInternational = checkInternationalPhoneDestination(outboundModel.getSmsTo());
        if (isInternational) {
            int internationalFactor = 1;
            if (profile != null)
                internationalFactor = profile.getSmsForeignFactor() != null ? profile.getSmsForeignFactor() : internationalFactor;
            countSMS = countSMS * internationalFactor;
            outboundModel.setSmsRecipientType(SMS_Constants.SMS_RECIPIENT_NUMBER_TYPE.INTERNATIONAL_NUMBER.getValue());
        } else
            outboundModel.setSmsRecipientType(SMS_Constants.SMS_RECIPIENT_NUMBER_TYPE.INDONESIAN_NUMBER.getValue());


        int res = helper.insertNewSmsOutbound(session, outboundModel);
        if (res != 0 && isDeductSMS) {
            SmsCommunityProfileHelper profileHelper = new SmsCommunityProfileHelper();
            SmsCommunityProfileModel profileModel = new SmsCommunityProfileModel();
            profileModel.setCommunityId(outboundModel.getCommunityId());
            profileModel.setSmsTransactionValue(countSMS);
            profileModel.setCreatedBy(outboundModel.getCreatedBy());
            profileModel.setUpdatedBy(outboundModel.getUpdatedBy());
            profileModel.setCreatedFrom(outboundModel.getUpdatedFrom());
            profileModel.setUpdatedFrom(outboundModel.getUpdatedFrom());

            profileHelper.deductCommunitySMSBalance(profileModel);
        }
    }

    private static boolean checkInternationalPhoneDestination(String destinationSmsNumber) {
        boolean bol = true;
        if (destinationSmsNumber != null && destinationSmsNumber.trim().length() != 0) {
            if (destinationSmsNumber.startsWith("+62"))
                bol = false;
            else if (destinationSmsNumber.startsWith("62"))
                bol = false;
            else if (destinationSmsNumber.startsWith("0"))
                bol = false;
        }
        return bol;
    }

    private static SMSProviderResponseModel translateProviderResponse(String response) {

        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println("ServerUtility.translateProviderResponse");
        //System.out.println("response = " + response);

        SAXBuilder builder = new SAXBuilder();
        SMSProviderResponseModel resModel = null;
        try {

            ByteArrayInputStream stream = new ByteArrayInputStream(response.getBytes());
            Document document = (Document) builder.build(stream);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("message");

            resModel = new SMSProviderResponseModel();
            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                String messageId = node.getChildText("messageId");
                String to = node.getChildText("to");
                String status = node.getChildText("status");
                String statusText = node.getChildText("text");

                int msgId = messageId != null && messageId.trim().length() != 0 ? new Integer(messageId) : -1;
                int ists = status != null && status.trim().length() != 0 ? new Integer(status) : -1;

                resModel.setMessageId(msgId);
                resModel.setTo(to != null && to.trim().length() != 0 ? to : "");
                resModel.setStatus(ists);
                resModel.setStatusMessage(statusText);
            }

        } catch (Exception io) {
            //System.out.println(io.getMessage());
        }
        //System.out.println("ServerUtility.translateProviderResponse " + resModel.getStatus());
        return resModel;
    }

    public static String checkTenmaProviderBalance() throws Exception {
        return SMSProviderUtil.checkTenmaProviderBalance();
    }

    public static String deleteInboxSMS(int smsId) throws Exception {
        return SMSProviderUtil.deleteInboxSMS(smsId);
    }

    public static String getSMSInbound() throws Exception {
        return SMSProviderUtil.getSMSInbound();
    }

}
