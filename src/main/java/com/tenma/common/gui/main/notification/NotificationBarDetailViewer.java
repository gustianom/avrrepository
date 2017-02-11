package com.tenma.common.gui.main.notification;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.notification.NotificationHelper;
import com.tenma.common.bean.reminder.ReminderHelper;
import com.tenma.common.bean.taskaction.TaskActionHelper;
import com.tenma.common.bean.todo.TodoHelper;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.NotificationModel;
import com.tenma.model.common.ReminderModel;
import com.tenma.model.common.TaskActionModel;
import com.tenma.model.common.TodoModel;
import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.lang.reflect.Constructor;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 4:41 PM
 */
public class NotificationBarDetailViewer extends VerticalLayout implements LayoutEvents.LayoutClickListener {

    private NotificationBar parentComponent;
    private NotificationModel viewModel;


    public NotificationBarDetailViewer(NotificationModel model, NotificationBar parentComponent) {

        this.parentComponent = parentComponent;
        viewModel = model;
        setPrimaryStyleName("notificationRowStyle");
        addLayoutClickListener(this);
        initLayout();
    }


    private void initLayout() {

        String createdByProfilePicture = "";
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

        boolean isTask = false;

        if (Constants.NOTIFICATION_TYPE.REMINDER.getValue() == viewModel.getNotifyType().intValue())
            notifySystemSource = TA.getCurrent().getParams().getLabel("reminder.title");
        else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == viewModel.getNotifyType().intValue()) {
            notifySystemSource = TA.getCurrent().getParams().getLabel("task.assigned");
            isTask = true;
        } else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == viewModel.getNotifyType().intValue()) {
            notifySystemSource = TA.getCurrent().getParams().getLabel("approval.request");
        }


        StringBuffer bufName = new StringBuffer()
                .append("<span class=\"styleNotificationFrom\">")
                .append(createdByUserName)
                .append("</span>")
                .append(" ")
                .append("<span class=\"styleNotificationSource\">")
                .append(notifySystemSource)
                .append("</span>")
                .append(" ")
                .append("<span class=\"styleNotificationSubjectMessage\">")
                .append(viewModel.getNotifySubject())
                .append("</span>");


        Label labelSubjectMessage = new Label();
        labelSubjectMessage.setContentMode(ContentMode.HTML);
        labelSubjectMessage.setValue(bufName.toString());

        StringBuffer bufLog = new StringBuffer()
                .append(TenmaConverter.dateToStringWord(viewModel.getCreatedDate(), TA.getCurrent().getLocale()))
                .append(" ")
                .append(TenmaConverter.dateToString(viewModel.getCreatedDate(), "dd-MM-yyyy hh:mm a", TA.getCurrent().getLocale()));
        Label labelDateLog = new Label();
        labelDateLog.setValue(bufLog.toString());


//        Label labelDateRemaining = new Label();
//        labelDateRemaining.setValue(ConverterUtil.dateToStringWordRemaining(dt, TA.getCurrent().getLocale()));

        HorizontalLayout dateLog = new HorizontalLayout();
        dateLog.setSpacing(true);
        dateLog.addComponent(labelDateLog);
//        dateLog.addComponent(labelDateRemaining);

//        setSpacing(true);
        addComponent(labelSubjectMessage);
        addComponent(dateLog);


    }

    @Override
    public void layoutClick(LayoutEvents.LayoutClickEvent event) {

        if (Constants.NOTIFICATION_TYPE.REMINDER.getValue() == viewModel.getNotifyType().intValue())
            doDeleteReminder();
        else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == viewModel.getNotifyType().intValue())
            doViewTask();
        else if (Constants.NOTIFICATION_TYPE.APPROVAL_DIRECTORY_MEMBER.getValue() == viewModel.getNotifyType().intValue())
            doExecuteApprovalRequest();

    }

    private void doExecuteApprovalRequest() {
        int approvalReqId = new Integer(viewModel.getNotifySourceId());

        TaskActionModel actionModel = new TaskActionModel();
        actionModel.setActionId(approvalReqId);

        TaskActionHelper hlp = new TaskActionHelper();
        actionModel = hlp.getTaskActionDetail(actionModel);
        if (actionModel != null)
            if (actionModel.getActionClass() != null && actionModel.getActionClass().trim().length() != 0) {
                try {

                    Class panelClass = Class.forName(actionModel.getActionClass().trim());
                    Constructor moduleConstructor = panelClass.getConstructor(new Class[]{String.class, String.class, Integer.class, Integer.class});

                    int fromMember = new Integer(viewModel.getNotifyFrom());
                    int toMember = new Integer(viewModel.getNotifyTo());
                    NotificationHelper notificationHelper = new NotificationHelper();
                    notificationHelper.deleteNotification(viewModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private void doViewTask() {
        System.out.println("share.tenma.common.gui.main.notification.NotificationBarDetailViewer.doViewTask " + viewModel.getNotifySourceId());

        System.out.println("share.tenma.common.gui.main.notification.NotificationBarDetailViewer.doViewTask" + viewModel.getCommunityId());
        System.out.println("share.tenma.common.gui.main.notification.NotificationBarDetailViewer.doViewTask" + viewModel.getNotifyTo());
        TodoModel m = new TodoModel();
        m.setTaskId(viewModel.getNotifySourceId());
        m.setCommunityId(viewModel.getCommunityId());
//        m.setNotifyTo(viewModel.getNotifyTo());

        setVisible(false);

        TodoHelper helper = new TodoHelper();
        m = helper.getTodoDetail(m);
        if (m != null) {
            if (m.getTaskActionClass() != null && m.getTaskActionClass().trim().length() != 0) {
                try {
                    System.out.println("share.tenma.common.gui.main.notification.NotificationBarDetailViewer.doViewTask " + m.getTaskActionClass());
                    Class panelClass = Class.forName(m.getTaskActionClass());
                    Constructor moduleConstructor = panelClass.getConstructor();
                    TenmaPanel module = (TenmaPanel) moduleConstructor.newInstance();
                    module.setTaskId(m.getTaskId());
                    module.setSizeFull();
                    TA.getCurrent().updateWorkingArea(module);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doDeleteReminder() {
        System.out.println("share.tenma.common.gui.main.notification.NotificationBarDetailViewer.doDeleteReminder");
        ReminderModel rem = new ReminderModel();
        rem.setReminderId(new Integer(viewModel.getNotifySourceId().trim()));
        rem.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        rem.setUpdatedBy(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        rem.setCreatedBy(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        rem.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
        rem.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        ReminderHelper hlp = new ReminderHelper();
        try {
            hlp.deleteReminder(rem);
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}