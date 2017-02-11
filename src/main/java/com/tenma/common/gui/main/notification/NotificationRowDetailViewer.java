package com.tenma.common.gui.main.notification;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.todo.TodoHelper;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.NotificationModel;
import com.tenma.model.common.TodoModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 4:41 PM
 */
public class NotificationRowDetailViewer extends TenmaNewPanel {
    private NotificationModel viewModel;


    public NotificationRowDetailViewer(NotificationModel model) {
        super();
        viewModel = model;
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
        String icon = "layouts/images/16/052.png";

        if (Constants.NOTIFICATION_TYPE.REMINDER.getValue() == viewModel.getNotifyType().intValue())
            notifySystemSource = TA.getCurrent().getParams().getLabel("reminder.title");
        else if (Constants.NOTIFICATION_TYPE.TASK.getValue() == viewModel.getNotifyType().intValue()) {
            notifySystemSource = TA.getCurrent().getParams().getLabel("task.assigned");
            icon = "layouts/images/16/079.png";
        }


        StringBuffer bufName = new StringBuffer()
                .append("<span class=\"styleNotificationFrom\">")
                .append(createdByUserName)
                .append("</span>")
                .append(" ")
                .append("<span class=\"styleNotificationSource\">")
                .append(notifySystemSource)
                .append("</span>")
                .append(" : ")
                .append("<span class=\"styleNotificationSubjectMessage\">")
                .append(viewModel.getNotifySubject())
                .append("</span>");


        StringBuffer bufLog = new StringBuffer()
                .append(TenmaConverter.dateToStringWord(viewModel.getCreatedDate(), TA.getCurrent().getLocale()))
                .append(" ")
                .append(TenmaConverter.dateToString(viewModel.getCreatedDate(), "dd-MM-yyyy HH:mm", TA.getCurrent().getLocale()));

        bufName.append(" ")
                .append(bufLog.toString());

        Label labelSubjectMessage = new Label();
        labelSubjectMessage.setContentMode(ContentMode.HTML);
        labelSubjectMessage.setPrimaryStyleName("rowContentDescription");
        labelSubjectMessage.setValue(bufName.toString());

        Label labelIcon = new Label("");
        labelIcon.setIcon(new ThemeResource(icon));
        HorizontalLayout lsub = new HorizontalLayout(labelIcon, labelSubjectMessage);

        VerticalLayout paymentBody = new VerticalLayout();
        paymentBody.setWidth(100, Unit.PERCENTAGE);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);

        layout.addComponent(lsub);
        if (viewModel.getNotifyType().intValue() == Constants.NOTIFICATION_TYPE.TASK.getValue())
            layout.addComponent(paymentBody);

        layout.setComponentAlignment(lsub, Alignment.TOP_LEFT);

        if (viewModel.getNotifyType().intValue() == Constants.NOTIFICATION_TYPE.TASK.getValue()) {
            layout.setComponentAlignment(paymentBody, Alignment.MIDDLE_LEFT);
            preparingBodyTask(paymentBody);
        }

        setCompositionRoot(layout);
    }

    private void preparingBodyTask(VerticalLayout paymentBody) {

        TodoHelper hlp = new TodoHelper();
        TodoModel todoModel = new TodoModel();
        todoModel.setTaskId(viewModel.getNotifySourceId());
        todoModel = hlp.getTodoDetail(todoModel);
        if (todoModel != null) {

//        Label labelDescription = new Label(todoModel.getTaskDesc());
//        labelDescription.setContentMode(ContentMode.HTML);
//        labelDescription.setPrimaryStyleName("rowContentDescription");
//

//            TodoRowDetailViewer viewer = new TodoRowDetailViewer( todoModel, this);

//            VerticalLayout vl = new VerticalLayout();
//            vl.setSpacing(true);
//            vl.addComponent(new Label());
//            vl.addComponent(viewer);
//            vl.addComponent(new Label());
//
//            vl.setComponentAlignment(viewer, Alignment.MIDDLE_LEFT);
//
//            vl.setSizeFull();
//
//
//            Panel panel = new Panel();
//            panel.setWidth("100%");
//            panel.setContent(vl);
//
//            paymentBody.addComponent(viewer);
        }
    }

//    private void clickUpdate() {
//        if (parentComponent != null) {
//            parentComponent.setSelectedObject(viewModel);
//            parentComponent.clickModify(TenmaMasterFormModify.UPDATE_MODE);
//        }
//    }
//
//    private void clickDelete() {
//        if (parentComponent != null) {
//            parentComponent.setSelectedObject(viewModel);
//            parentComponent.clickDelete();
//        }
//    }


}