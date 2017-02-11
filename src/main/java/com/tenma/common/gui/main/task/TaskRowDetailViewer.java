package com.tenma.common.gui.main.task;

import com.tenma.common.TA;
import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.gui.display.TenmaButtonList;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.model.common.TaskModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;

import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 11/15/13
 * Time: 4:41 PM
 */
public class TaskRowDetailViewer extends ColumnDetailViewer {
    private final String BUTTON_EDIT = "TASK_BUTTON_EDIT";
    private final String BUTTON_WATCH = "TASK_BUTTON_WATCH";
    private final String BUTTON_PERSON = "TASK_BUTTON_PERSON";
    private final String BUTTON_CONVERSATION = "TASK_BUTTON_CONVERSATION";
    private final String BUTTON_DELETE = "TASK_BUTTON_DELETE";
    private final String BUTTON_TASK_ACTION = "TASK_BUTTON_ACTION";
    private TenmaButtonList parentComponent;
    private TaskModel viewModel;


    public TaskRowDetailViewer(TaskModel model, TenmaButtonList parentComponent) {
        super();
        this.parentComponent = parentComponent;
        viewModel = model;
        initLayout();
    }

    public TaskRowDetailViewer(TaskModel model) {
        super();
        viewModel = model;
        initLayout();
    }

    private void initLayout() {
        setContentCaption(viewModel.getTaskName());
        setCaption(TenmaConverter.dateToString(viewModel.getDeadline(), "dd MMM yyyy"));
        setContentDetail(viewModel.getTaskDesc());
        setDateLog(viewModel.getCreatedDate());
        buildCommandArea();
    }


    private void buildCommandArea() {
        System.out.println("------------->>> " + TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        Button taskAction = null;
        if (viewModel.getCreatedBy().equals(TA.getCurrent().getClientInfo().getClientUserModel().getUserId())) {
            Button buttonWatch = createTaskButton(BUTTON_WATCH, "layouts/images/16/eye.png", "buttonRowView");
            Button buttonPerson = createTaskButton(BUTTON_PERSON, "layouts/images/share.png", "buttonRowView");
            Button buttonEdit = createTaskButton(BUTTON_EDIT, "layouts/images/16/pencil.png", "buttonRowView");
            Button buttonDelete = createTaskButton(BUTTON_DELETE, "layouts/images/deletered.png", "buttonRowView");

            getButtonArea().addComponent(buttonWatch);
            getButtonArea().addComponent(buttonPerson);
            getButtonArea().addComponent(buttonEdit);
            getButtonArea().addComponent(buttonDelete);

            generateShareCount(buttonPerson);
        } else {
            taskAction = createTaskButton(BUTTON_TASK_ACTION, "layouts/images/16/set-gr.png", "buttonRowView");
            getButtonArea().addComponent(taskAction);
        }

        if (!viewModel.getCreatedBy().equals(Constants.SYSTEM)) {
            Button buttonConversation = createTaskButton(BUTTON_CONVERSATION, "layouts/images/16/030.png", "buttonRowView");
            getButtonArea().addComponent(buttonConversation);
        }


    }

    private Button createTaskButton(String buttonId, String pathLayout, String cssStyle) {
        Button btn = new Button();
        btn.setId(buttonId);
        btn.setImmediate(true);
        btn.setIcon(new ThemeResource(pathLayout));
        btn.setPrimaryStyleName(cssStyle);
        btn.addClickListener(this);
        return btn;
    }

    private void generateShareCount(Button buttonPerson) {
        HashMap parameter = new HashMap();
        parameter.put("taskId", viewModel.getTaskId());
        parameter.put(Constants.COMMUNITY_ID, viewModel.getCommunityId());
        TaskMemberHelper hlp = new TaskMemberHelper();
        int cnt = hlp.countTotalList(parameter);
        if (cnt != 0)
            buttonPerson.setCaption(" " + cnt);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        String id = clickEvent.getButton().getId();
        if (BUTTON_WATCH.equals(id)) {
        } else if (BUTTON_PERSON.equals(id)) {
        } else if (BUTTON_CONVERSATION.equals(id)) {
        } else if (BUTTON_EDIT.equals(id))
            clickUpdate();
        else if (BUTTON_TASK_ACTION.equals(id))
            clickAction();
        else if (BUTTON_DELETE.equals(id))
            clickDelete();
    }

    private void clickAction() {

    }

    private void clickUpdate() {
        if (parentComponent != null) {
            parentComponent.setSelectedObject(viewModel);
            //parentComponent.clickModify(TenmaMasterFormModify.UPDATE_MODE);
        }
    }

    private void clickDelete() {
        if (parentComponent != null) {
            parentComponent.setSelectedObject(viewModel);
            //parentComponent.clickDelete();
        }
    }


}