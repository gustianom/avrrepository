package com.tenma.common.gui.main.task;

import com.tenma.common.TA;
import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.TaskMemberModel;
import com.tenma.model.common.TodoModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.lang.reflect.Constructor;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 4:41 PM
 */
public class TodoRowDetailViewer extends ColumnDetailViewer {
    private final String BUTTON_ACTION = "TASK_BUTTON_ACTION";
    private final String BUTTON_DONE = "TASK_BUTTON_DONE";
    private final String BUTTON_POSTPONE = "TASK_BUTTON_POSTPONE";
    private final String BUTTON_ARCHIEVE = "TASK_BUTTON_ARCHIEVE";
    private TenmaPanel parentComponent;
    private TodoModel viewModel;


    public TodoRowDetailViewer(TodoModel model, TenmaPanel parentComponent) {
        this.parentComponent = parentComponent;
        viewModel = model;
        initLayout();
    }

    private void initLayout() {
        StringBuffer buf = new StringBuffer()
                .append(viewModel.getTaskName())
                .append(", ")
                .append(parentComponent.param.getLabel("default.by"))
                .append(" ")
//                .append(viewModel.getCreatedBy() != null ? viewModel.getCreatedBy().startsWith(Constants.SYSTEM) ? parentComponent.param.getLabel("default.system") : viewModel.getCreatorFullName() : parentComponent.param.getLabel("default.system"));
                .append(viewModel.getCreatedBy() != null ? viewModel.getCreatedBy().startsWith(Constants.SYSTEM) ? parentComponent.param.getLabel("default.system") : "" : parentComponent.param.getLabel("default.system"));
        setContentCaption(buf.toString());
        setCaption(TenmaConverter.dateToString(viewModel.getDeadline(), "dd MMM yyyy"));
        setContentDetail(viewModel.getTaskDesc());
        setDateLog(viewModel.getCreatedDate());
        setDateRemaining(viewModel.getDeadline());
        buildCommandArea();
    }


    private void buildCommandArea() {

        if ((viewModel.getTaskActionClass() != null) && (viewModel.getTaskActionClass().trim().length() != 0)) {
            Button buttonConversation = new Button();
            buttonConversation.setId(BUTTON_ACTION);
            buttonConversation.setCaption(parentComponent.param.getLabel("default.execute"));
            buttonConversation.setIcon(new ThemeResource("layouts/images/16/131.png"));
            buttonConversation.setPrimaryStyleName("buttonRowView");
            buttonConversation.addClickListener(this);

            getButtonArea().addComponent(buttonConversation);
        } else {

            Button postpone = new Button();
            postpone.setId(BUTTON_DONE);
            postpone.setCaption(parentComponent.param.getLabel("default.postpone"));
            postpone.setIcon(new ThemeResource("layouts/images/16/087.png"));
            postpone.setPrimaryStyleName("buttonRowView");
            postpone.addClickListener(this);

            Button done = new Button();
            done.setId(BUTTON_DONE);
            done.setCaption(parentComponent.param.getLabel("default.done"));
            done.setIcon(new ThemeResource("layouts/images/16/102.png"));
            done.setPrimaryStyleName("buttonRowView");
            done.addClickListener(this);

            Button archieve = new Button();
            archieve.setId(BUTTON_DONE);
            archieve.setCaption(parentComponent.param.getLabel("default.done"));
            archieve.setIcon(new ThemeResource("layouts/images/16/102.png"));
            archieve.setPrimaryStyleName("buttonRowView");
            archieve.addClickListener(this);

            boolean ph = true;
            boolean dn = true;
            boolean ar = true;

            if (viewModel.getTodoStatus() == Constants.TODO_STATUS.NEW.getValue()) {
                ph = true;
                dn = true;
                ar = false;
            } else if (viewModel.getTodoStatus() == Constants.TODO_STATUS.DONE.getValue()) {
                ph = false;
                dn = false;
                ar = true;
            } else if (viewModel.getTodoStatus() == Constants.TODO_STATUS.POSTPONE.getValue()) {
                ph = false;
                dn = true;
            }

            getButtonArea().removeAllComponents();
            getButtonArea().addComponent(postpone);
            getButtonArea().addComponent(done);
            getButtonArea().addComponent(archieve);

            postpone.setVisible(ph);
            done.setVisible(dn);
            archieve.setVisible(ar);
        }

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        String id = clickEvent.getButton().getId();
        if (BUTTON_ACTION.equals(id))
            doExecuteTask();
        else if (BUTTON_DONE.equals(id))
            updateStatusTask(Constants.TODO_STATUS.DONE.getValue());
        else if (BUTTON_POSTPONE.equals(id))
            updateStatusTask(Constants.TODO_STATUS.POSTPONE.getValue());
        else if (BUTTON_ARCHIEVE.equals(id)) {
            updateStatusTask(Constants.TODO_STATUS.ARCHIEVE.getValue());
            this.setVisible(false);
        }
    }

    private void updateStatusTask(int status) {
        TaskMemberModel m = new TaskMemberModel();
        m.setTaskId(viewModel.getTaskId());
        m.setUserId(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        m.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        m.setTodoStatus(status);
        m.setCreatedBy(TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        m.setUpdatedBy(m.getCreatedBy());
        m.setCreatedFrom(TA.getCurrent().getClientInfo().getIpAddress());
        m.setUpdatedFrom(m.getCreatedFrom());

        TaskMemberHelper helper = new TaskMemberHelper();
        int res = 0;
        try {
            res = helper.updateRefTaskMember(m);
            viewModel.setTodoStatus(status);
            initLayout();
//            TODO-TMP - required update for replace commonMessageHandler that was removed.
//            parentComponent.commonMessageHandler(Notification.Type.TRAY_NOTIFICATION, CrudCode.UPDATE, parentComponent.param.getMessage("system.info.update"), parentComponent.param.getMessage("system.info.success"));
        } catch (Exception e) {
            e.printStackTrace();
//            TODO-TMP - required update for replace commonMessageHandler that was removed.
//            parentComponent.commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.UPDATE, parentComponent.param.getMessage("system.error.saving"), null);
        }
    }

    private void doExecuteTask() {
        if (parentComponent != null) {
//            TODO-TMP - required update for replace setSelectedObject that was removed.
//            parentComponent.setSelectedObject(viewModel);

            if ((viewModel.getTaskActionClass() != null) && (viewModel.getTaskActionClass().trim().length() != 0)) {
                try {
                    Class panelClass = Class.forName(viewModel.getTaskActionClass());
                    Constructor moduleConstructor = panelClass.getConstructor();
                    TenmaNewPanel module = (TenmaNewPanel) moduleConstructor.newInstance();

                    module.setTaskId(viewModel.getTaskId());
                    module.setSizeFull();

                    Window window = new Window();
                    window.setCaption(viewModel.getTaskName());
                    window.setContent(module);
//                    window.setWidth((TA.getCurrent()getBrowserWidth() - 320) + "px");
                    window.setWidth((UI.getCurrent().getWidth() - 320) + "px");
//                    window.setHeight((TA.getCurrent()getBrwoserHeight() - 160) + "px");
                    window.setHeight(UI.getCurrent().getHeight() - 160 + "px");
                    window.setModal(true);
                    window.setClosable(true);
                    window.setStyleName("windowreport");
                    window.addCloseListener(new Window.CloseListener() {
                        @Override
                        public void windowClose(Window.CloseEvent e) {
                            refreshTodoList();
                        }
                    });
                    UI.getCurrent().addWindow(window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refreshTodoList() {
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
        System.out.println("share.tenma.common.gui.main.task.TodoRowDetailViewer.refreshTodoList");
//        parentComponent.refreshTable();
    }

}