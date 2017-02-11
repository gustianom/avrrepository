/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.util.web;

import com.tenma.common.TA;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.gui.main.taskaction.Task;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskModel;
import com.tenma.model.sms.SMSModel;
import com.tenma.sms.model.SmsCommunityProfileModel;
import com.tenma.util.SMS_Constants;
import com.tenma.util.sms.services.SMSUtil;
import com.vaadin.ui.UI;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:12 AM
 */
public class ServerUtility {

    /**
     * Save Object to Request
     *
     * @param attributeName {@link String}
     * @param object        {@link Object}
     */
    public static void saveObjectToRequest(String attributeName, Object object) {
        TA.getCurrent().getHttpRequest().getSession().setAttribute(attributeName, object);
    }

    public static String getCurrentUserId() {
        ClientInfo us = TA.getCurrent().getClientInfo();
        if (us != null) {
            return us.getClientUserModel().getUserId();
        } else {
            return null;
        }
    }

    private static void doNotifyTaskBalanceSMS(Task.GENERATE generateType, String communityId, String assignedUserId, boolean isTaskGenerated) throws Exception {
        if (isTaskGenerated) {
            TaskHelper hlp = new TaskHelper();
            TaskModel taskModel = new TaskModel();
            taskModel.setTaskActionId(generateType.getValue());
            taskModel.setTaskStatus(Constants.TASK_STATUS.OPEN.getValue());
            taskModel.setCommunityId(communityId);

            taskModel = hlp.getTaskDetail(taskModel);
            if (taskModel == null)
                Task.doGenerate(generateType, communityId, assignedUserId, Constants.SYSTEM, Constants.SYSTEM, false);
        }
//        Notification.show("SMS balance empty", Notification.Type.TRAY_NOTIFICATION);
    }

    /**
     * <p/> Description of SMS Source Type
     * <p/> share.share.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.SYSTEM is SMS from system, the quota, system will deduct quota when SMS_SYSTEM_DEDUCTED field on SMS Community Profile is 1 (YES)
     * <p/> share.share.tenma.util.SMS_Constants.SMS_SOURCE_TYPE.NORMAL system will normally deduct quota of sms
     *
     * @param creatorUserId
     * @param remoteIPAddress
     * @param model
     * @param communityId
     * @param smsSourceType
     * @throws Exception
     */
    public static SMS_Constants.SMS_RESPONSE_CODE sendSms(final String creatorUserId, final String remoteIPAddress, final SMSModel model, final String communityId, final com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE smsSourceType) throws Exception {
        SMS_Constants.SMS_RESPONSE_CODE rsp = SMSUtil.sendSms(creatorUserId, remoteIPAddress, model, communityId, smsSourceType);
        System.out.println("ServerUtility.sendSms " + rsp);
        if (!rsp.equals(SMS_Constants.SMS_RESPONSE_CODE.DELIVERED)) {
            if (rsp.equals(SMS_Constants.SMS_RESPONSE_CODE.FAILED_COMMUNITY_SMS_BALANCE_EMPTY))
                doNotifyTaskBalanceSMS(Task.GENERATE.SMS_BALANCE_EMPTY, communityId, creatorUserId, true);
            else if (rsp.equals(SMS_Constants.SMS_RESPONSE_CODE.DELIVERED_AND_TRESHOLD_REACHED))
                doNotifyTaskBalanceSMS(Task.GENERATE.SMS_THRESHOLD_REACHED, communityId, creatorUserId, true);
            else
                System.out.println("NO SMS PROFILE " + communityId);

        }
        return rsp;
    }

    public static SMS_Constants.SMS_PROFILE_CODE doCheckProfileEligibleSMS(SmsCommunityProfileModel profileModel, SMSModel model, com.tenma.util.SMS_Constants.SMS_SOURCE_TYPE smsSourceType) {
        return SMSUtil.doCheckProfileEligibleSMS(profileModel, model, smsSourceType);
    }
}