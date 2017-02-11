package com.tenma.common.gui.main.task;

import com.tenma.common.TA;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.gui.display.TenmaButtonList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskModel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import java.util.HashMap;
import java.util.List;

public class TaskList extends TenmaButtonList {
    public TaskList() {

        setTitle("task.title");
        getUpdateButton().setVisible(false);
        getDeleteButton().setVisible(false);
        getPrintButton().setVisible(false);
        doRefreshData();
    }

    @Override
    public void doModify(TenmaPanel parentContainer, int update_mode) {
        try {

            TaskModify modify = new TaskModify(this, update_mode);
//            modify.setCaption(update_mode == TenmaMasterFormModify.ADD_MODE ? param.getLabel(Constants.LABEL_NEW) : param.getLabel(Constants.LABEL_UPDATE));
//            modify.setModal(true);
//            modify.setWidth(80, Unit.PERCENTAGE);
//            modify.setHeight(95, Unit.PERCENTAGE);
//            modify.setResizable(false);
//            UI.getCurrent().addWindow(modify);
            UI.getCurrent().getUI().setContent(modify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int doDeletion() throws Exception {
        TaskModel m = (TaskModel) getSelectedObject();
        setAuditTrail(m);
        TaskHelper help = new TaskHelper();
        int res = help.deleteTask(m);
        return res;
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
        HashMap parameterMap = new HashMap();

        parameterMap.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        parameterMap.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());

        parameterMap.put(Constants.RECORDSELECT_SKIP, 0);
        parameterMap.put(Constants.RECORDSELECT_MAX, 5);
        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, "createdDate");
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.DESCENDING);

        TaskHelper helper = new TaskHelper();
        List ls = helper.getListRenderer(parameterMap, false);

        int sz = 1;

        if ((ls != null) && (ls.size() != 0))
            sz = ls.size();


        GridLayout p = getDataListComponent();
        p.setColumns(1);
        p.setRows(sz);

        p.setSpacing(true);
        p.setSizeFull();
        p.setHeight("100%");
        if ((ls != null) && (ls.size() != 0)) {
            for (int i = 0; i < ls.size(); i++) {
                TaskModel m = (TaskModel) ls.get(i);

                System.out.println("m --------------------->>>>> = " + m);
                System.out.println("m --------------------->>>>> = " + m);
                TaskRowDetailViewer viewer = new TaskRowDetailViewer(m, this);
                p.addComponent(viewer);
            }
        } else {
            p.addComponent(new Label(TA.getCurrent().getParams().getMessage("record.notfound"), ContentMode.HTML));
        }
    }

    public void doPrint() {
        TaskHelper help = new TaskHelper();
        HashMap p = new HashMap();
        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = help.getListRenderer(p, false);
        openReport(param.getLabel("task.title"), "task/taskList.jasper", list, null, Constants.REPORT_MIME_TYPES.PDF);
    }
}

