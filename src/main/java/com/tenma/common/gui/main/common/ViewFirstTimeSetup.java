package com.tenma.common.gui.main.common;

import com.tenma.common.TA;

import com.tenma.common.bean.todo.TodoHelper;
import com.tenma.common.gui.main.TenmaBaseApplication;
import com.tenma.common.gui.main.task.TodoRowDetailViewer;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TodoModel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 2/3/14.
 */
public class ViewFirstTimeSetup extends CommonSetupPanel {
    private TenmaBaseApplication application;

    public ViewFirstTimeSetup(TenmaBaseApplication tenmaApplication) {
        this.application = tenmaApplication;
    }

    @Override
    public void doDisplayContent() {
        TodoHelper helper = new TodoHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        p.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
        p.put("systemMandatory", Constants.TASK_SYSTEM_MANDATORY_STATUS.MANDATORY.getValue());
        List<TodoModel> processingList = helper.getListRenderer(p, false);

        int sz = 1;

        if ((processingList != null) && (processingList.size() != 0))
            sz = processingList.size();


        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(sz);

        gridLayout.setSpacing(true);
        gridLayout.setSizeFull();
        gridLayout.setHeight("100%");
        if ((processingList != null) && (processingList.size() != 0)) {
            for (TodoModel m : processingList) {
                TodoRowDetailViewer viewer = new TodoRowDetailViewer(m, this);
                gridLayout.addComponent(viewer);
            }
        } else {
            gridLayout.addComponent(new Label(TA.getCurrent().getParams().getMessage("record.notfound"), ContentMode.HTML));
        }
        Panel panel = new Panel();
        panel.setWidth("100%");
        panel.setContent(gridLayout);
        updateContent("todo.list", panel);
    }


    public void refreshTable() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("share.tenma.common.gui.main.signup.common.ViewFirstTimeSetup.refreshTable");
        application.logonSuccessFully();
    }
}
