package com.tenma.common.gui.main.task;

import com.tenma.common.TA;
import com.tenma.common.bean.todo.TodoHelper;
import com.tenma.common.gui.display.TenmaButtonList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TodoModel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import java.util.HashMap;
import java.util.List;

public class TodoList extends TenmaButtonList {
    public TodoList() {

        preparingContent();

    }

    private void preparingContent() {
        setTitle("todo.title");
        getAddButton().setVisible(false);
        getUpdateButton().setVisible(false);
        getDeleteButton().setVisible(false);
        getPrintButton().setVisible(false);
        doRefreshData();

    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {
            TaskModify modify = new TaskModify(this, update_mode);
            modify.setCaption(update_mode == TenmaMasterFormModify.ADD_MODE ? param.getLabel(Constants.LABEL_NEW) : param.getLabel(Constants.LABEL_UPDATE));
//            modify.setModal(true);
            modify.setWidth(60, Unit.PERCENTAGE);
            modify.setHeight(90, Unit.PERCENTAGE);
//            modify.setResizable(false);
//            UI.getCurrent().addWindow(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int doDeletion() throws Exception {
        return 0;
    }

    @Override
    public void refreshTable() {
        doRefreshData();
    }


    @Override
    public void executingDataPreparation() {

    }

    @Override
    public void doSearch() {
        setPageIndex(0);
        doRefreshData();
    }

    private void doRefreshData() {
        getDataListComponent().removeAllComponents();
        createListLayout();
    }

    public void createListLayout() {
        List<TodoModel> processingList = populateCurrentTodoList();
        int sz = 1;

        if ((processingList != null) && (processingList.size() != 0))
            sz = processingList.size();


        GridLayout p = getDataListComponent();
        p.setColumns(1);
        p.setRows(sz);

        p.setSpacing(true);
        p.setSizeFull();
        p.setHeight("100%");
        if ((processingList != null) && (processingList.size() != 0)) {
            for (TodoModel m : processingList) {
                TodoRowDetailViewer viewer = new TodoRowDetailViewer(m, this);
                p.addComponent(viewer);
            }
        } else {
            p.addComponent(new Label(TA.getCurrent().getParams().getMessage("record.notfound"), ContentMode.HTML));
        }

    }

    private List<TodoModel> populateCurrentTodoList() {
        HashMap param = new HashMap();

        String skey = (String) getTextSearch().getValue();
        if ((skey != null) && (skey.trim().length() != 0))
            param.put(Constants.HEADER_SEARCH, "%" + skey + "%");

        param.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        param.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());

        TodoHelper helper = new TodoHelper();
        List<TodoModel> ls = helper.getListRenderer(param, false);
        return ls;
    }

    public void doPrint() {
        TodoHelper help = new TodoHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("task.title"), "task/taskList.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }
}

