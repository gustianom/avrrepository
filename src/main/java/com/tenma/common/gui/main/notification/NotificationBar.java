package com.tenma.common.gui.main.notification;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.notification.NotificationHelper;
import com.tenma.common.bean.taskaction.TaskActionHelper;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.NotificationModel;
import com.tenma.model.common.TaskActionModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * user gustianom on 1/18/14.
 */
public class NotificationBar {

    private final String MENU_ITEM_COMMAND_SEE_ALL = "SEE_ALL";
    private final String CLICK_NOTIFY_BAR = "CLICK_NOTIFY_BAR";
    private MenuBar.MenuItem menuItemNotification;

    private MenuBar menuBarNotify = new MenuBar();

    public NotificationBar() {

        menuBarNotify.setAutoOpen(true);
        menuItemNotification = menuBarNotify.addItem("la", new ThemeResource("layouts/images/16/045.png"), createMenuCommand(CLICK_NOTIFY_BAR));
        updateStatus();
    }

    public MenuBar getMenuBar() {
        return menuBarNotify;
    }

    private MenuBar.Command createMenuCommand(final String command) {
        final MenuBar.Command cmd = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                if (MENU_ITEM_COMMAND_SEE_ALL.equals(command))
                    viewNotificationList();
                else if (CLICK_NOTIFY_BAR.equals(command))
                    updateNotificationDBView();
            }
        };
        return cmd;
    }

    private void viewNotificationList() {
        updateNotificationDBView();
        NotificationGridList list = new NotificationGridList();
        TA.getCurrent().updateWorkingArea(list);
    }

    private void updateNotificationDBView() {
        NotificationHelper helper = new NotificationHelper();
        NotificationModel notiModel = new NotificationModel();
        TA.getCurrent().setAuditTrail(notiModel);
        notiModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        notiModel.setViewStatus(1);
        notiModel.setNotifyTo(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        try {
            helper.updateNotification(notiModel);
            menuItemNotification.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    Update Notification List and count from push
     */
    public void updateStatus() {

        HashMap paramCount = new HashMap();
        paramCount.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        paramCount.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        paramCount.put("viewStatus", 0);

        NotificationHelper helper = new NotificationHelper();

        int count = helper.countTotalList(paramCount);
        menuItemNotification.removeChildren();

        menuItemNotification.setText(count == 0 ? "" : String.valueOf(count));
        if (count == 0)
            menuItemNotification.setIcon(new ThemeResource("layouts/images/24/blackbell24.png"));
        else
            menuItemNotification.setIcon(new ThemeResource("layouts/images/16/bell.png"));

        HashMap parameterMap = new HashMap();
        parameterMap.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        parameterMap.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());

        parameterMap.put(Constants.RECORDSELECT_SKIP, 0);
        parameterMap.put(Constants.RECORDSELECT_MAX, 5);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, "createdDate");
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.DESCENDING);

        List<NotificationModel> ls = helper.getListRenderer(parameterMap, true);

        if ((ls != null) && (ls.size() != 0)) {
            for (NotificationModel m : ls) {
                String icon = "layouts/images/16/052.png";
                if (Constants.NOTIFICATION_TYPE.REMINDER.getValue() == m.getNotifyType().intValue())
                    icon = "layouts/images/16/052.png";
                else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == m.getNotifyType().intValue())
                    icon = "layouts/images/16/079.png";
                else if (Constants.NOTIFICATION_TYPE.APPROVAL_DIRECTORY_MEMBER.getValue() == m.getNotifyType().intValue())
                    icon = "layouts/images/16/45.png";

                menuItemNotification.addItem(subject2Display(m), new ThemeResource(icon), executeSelectedNotification(m));
            }

            MenuBar.MenuItem childSeeAll = menuItemNotification.addItem(TA.getCurrent().getParams().getLabel("message.see.all"), createMenuCommand(MENU_ITEM_COMMAND_SEE_ALL));
            menuItemNotification.addSeparatorBefore(childSeeAll);
        }
    }

    private MenuBar.Command executeSelectedNotification(final NotificationModel m) {
        MenuBar.Command cmd = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                if (Constants.NOTIFICATION_TYPE.APPROVAL_DIRECTORY_MEMBER.getValue() == m.getNotifyType().intValue())
                    doExecuteRequest(m);
                else
                    viewNotificationList();
            }
        };
        return cmd;
    }

    private void doExecuteRequest(NotificationModel viewModel) {
        int approvalReqId = new Integer(viewModel.getNotifySourceId().trim());

        TaskActionModel actionModel = new TaskActionModel();
        actionModel.setActionId(approvalReqId);

        TaskActionHelper hlp = new TaskActionHelper();
        actionModel = hlp.getTaskActionDetail(actionModel);
        if (actionModel != null)
            if (actionModel.getActionClass() != null && actionModel.getActionClass().trim().length() != 0) {
                try {

                    Class panelClass = Class.forName(actionModel.getActionClass().trim());
                    Constructor moduleConstructor = panelClass.getConstructor(new Class[]{String.class, String.class, Integer.class, Integer.class});

//                    int fromMember = new Integer(viewModel.getNotifyFrom());
//                    int toMember = new Integer(viewModel.getNotifyTo());
                    TA.getCurrent().setAuditTrail(viewModel);
                    UserHelper help = new UserHelper();
                    UserModel mdl = new UserModel();
                    mdl.setUserId(viewModel.getNotifyFrom());
                    mdl = help.getUserDetail(mdl);
                    NotificationHelper notificationHelper = new NotificationHelper();
                    notificationHelper.deleteNotification(viewModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private String subject2Display(NotificationModel viewModel) {
        String createdByUserName = "";

        if (!Constants.SYSTEM.equals(viewModel.getNotifyFrom())) {
            if (!viewModel.getNotifyFrom().equals(TA.getCurrent().getClientInfo().getClientUserModel().getUserId())) {
                UserModel creatorUserModel = new UserModel();
                creatorUserModel.setUserId(viewModel.getNotifyFrom());
                UserHelper hlp = new UserHelper();
                creatorUserModel = hlp.getUserDetail(creatorUserModel);
                if (creatorUserModel != null) {
//                createdByProfilePicture = creatorUserModel.getProfilePicture());
                    createdByUserName = creatorUserModel.getUserFullName();
                }
            }
        }

        String notifySystemSource = "";
        if (Constants.NOTIFICATION_TYPE.REMINDER.getValue() == viewModel.getNotifyType().intValue())
            notifySystemSource = TA.getCurrent().getParams().getLabel("reminder.title");
        else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == viewModel.getNotifyType().intValue())
            notifySystemSource = TA.getCurrent().getParams().getLabel("task.assigned");
        else if (Constants.NOTIFICATION_TYPE.APPROVAL_DIRECTORY_MEMBER.getValue() == viewModel.getNotifyType().intValue())
            notifySystemSource = TA.getCurrent().getParams().getLabel("approval.request");

        StringBuffer sbs = new StringBuffer();
        if (viewModel.getNotifySubject() != null && viewModel.getNotifySubject().trim().length() != 0) {
            if (viewModel.getNotifySubject().length() <= 50)
                sbs.append(viewModel.getNotifySubject());
            else
                sbs.append(viewModel.getNotifySubject().substring(0, 50))
                        .append("...");
        }

        StringBuffer bufName = new StringBuffer()
                .append(createdByUserName)
                .append(" ")
                .append(notifySystemSource)
                .append(" : ")
                .append(sbs.toString());


        StringBuffer bufLog = new StringBuffer()
                .append(TenmaConverter.dateToStringWord(viewModel.getCreatedDate(), TA.getCurrent().getLocale()));
//                .append(" ")
//                .append(ConverterUtil.dateToString(viewModel.getCreatedDate(), "dd-MM-yyyy HH:mm", TA.getCurrent().getLocale()));

        bufName.append(" ")
                .append(bufLog.toString());


        return bufName.toString();
    }
}
