package com.tenma.common.gui.main.task;

import com.google.gson.Gson;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.gui.display.custom.TenmaPanelCustomList;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskModel;
import com.tenma.model.common.TenmaTableContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma-01 on 27/01/16.
 */
public class TaskViewList extends TenmaPanelCustomList {
    private final String TASK_DATE = "taskDate";
    private final String TASK_TITLE = "taskTitle";
    private final String TASK_STATUS = "taskStatus";
    HashMap mapIO = new HashMap();
    private Table table;
    private TenmaTableContainer ic;
    private CheckBox chkInbox = new CheckBox("Inbox");
    private CheckBox chkOutbox = new CheckBox("Outbox");

    public TaskViewList() {
        super();
        setHeaderCaption(new Label(param.getLabel("Task List")));
        setUI();
    }

    private void setUI() {
        HorizontalLayout hLay = new HorizontalLayout();
        table = new Table();
        table.setSizeFull();
        preparingAreaSearch();

        chkInbox.addValueChangeListener(event -> actionInbox(event));
        chkOutbox.addValueChangeListener(event -> actionOutbox(event));
        HorizontalLayout hChk = new HorizontalLayout(chkInbox, chkOutbox);
        hChk.setSpacing(true);

        getUpdateButton().setVisible(false);
        getDeleteButton().setVisible(false);
        HorizontalLayout titleLayout = gethTitle();
        HorizontalLayout buttonLayout = gethButtonLay();
        getpHeader().removeAllComponents();
        getpHeader().addComponent(titleLayout);
        getpHeader().addComponent(hChk);
        getpHeader().addComponent(buttonLayout);

        getpHeader().setComponentAlignment(titleLayout, Alignment.MIDDLE_LEFT);
        getpHeader().setComponentAlignment(hChk, Alignment.MIDDLE_RIGHT);
        getpHeader().setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
        Panel header = getHeaderPanel();
        getBtnBackMetro().setVisible(false);
        HorizontalLayout hPaging = getCustomPaging();

        VerticalLayout vMain = new VerticalLayout();
        vMain.setWidth(90, Unit.PERCENTAGE);
        vMain.addComponent(header);
        vMain.addComponent(table);
        vMain.addComponent(hPaging);
        vMain.setMargin(new MarginInfo(true, false, false, false));
        hLay.addComponent(vMain);
        hLay.setWidth("100%");
        hLay.setComponentAlignment(vMain, Alignment.TOP_CENTER);

        this.setContent(hLay);
        preparingAreaSearch();
        setTableUI();
    }

    private void actionOutbox(Property.ValueChangeEvent event) {
        doRefreshData();
    }

    private void actionInbox(Property.ValueChangeEvent event) {
        doRefreshData();
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
        super.buttonClick(clickEvent);
        if (clickEvent.getButton().equals(getAddButton())) {
            try {
                TaskModify modify = new TaskModify(this, Constants.ITEM_CRUD.CREATE.getValue());
                UI.getCurrent().getUI().setContent(modify);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (clickEvent.getButton().equals(getUpdateButton())) {
            if (isRowSelected()) {
                try {
                    TaskModify modify = new TaskModify(this, Constants.ITEM_CRUD.UPDATE.getValue());
                    UI.getCurrent().getUI().setContent(modify);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (clickEvent.getButton().equals(getSearchButton())) {
            doRefreshData();
        }

    }


    private void preparingAreaSearch() {

        getSearchText().addShortcutListener(new ShortcutListener("textSeacrList" + getId(), ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target.equals(getSearchText())) {
                    doRefreshData();
                }
            }
        });
    }

    public void setTableUI() {
        ic = new TenmaTableContainer();
        ic.addContainerProperty(TASK_DATE, String.class, "");
        ic.addContainerProperty(TASK_TITLE, String.class, "");
        ic.addContainerProperty(TASK_STATUS, String.class, "");
        table.setContainerDataSource(ic);
        table.commit();

        table.setColumnHeaders(
                getLabel("default.date"),
                getLabel("Task"),
                getLabel("default.status")

        );
        table.setColumnCollapsingAllowed(true);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setMultiSelect(false);
        table.addValueChangeListener(new Table.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                TaskModel ac = (TaskModel) valueChangeEvent.getProperty().getValue();
                if (ac != null) {
                    setSelectedObject(ac);
                } else {
                    System.out.println("Null");
                }


            }
        });
        doRefreshData();
    }


    private void doRefreshData() {
        ic.removeAllItems();
        ic = collectData();
        table.setPageLength(table.getItemIds().size());
    }

    private TenmaTableContainer collectData() {
        System.out.println("TaskViewList.collectData mapIO : " + mapIO);
        Integer member = TA.getCurrent().getClientInfo().getClientMemberModel().getMemberId();
        HashMap parameterMap = new HashMap();
        parameterMap.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
//        parameterMap.put(Constants.RECORDSELECT_SKIP, 0);
//        parameterMap.put(Constants.RECORDSELECT_MAX, 5);

        parameterMap.put(Constants.RECORDSELECT_SORTEDFIELD, "taskDate");
        parameterMap.put(Constants.RECORDSELECT_SORTORDER, Constants.SORTING_TYPE.DESCENDING);
        if (chkInbox.getValue()) {
            List<Integer> list = new ArrayList<>();
            list.add(member);
            parameterMap.put("picMemberType", new Gson().toJson(list));
        }

        if (chkOutbox.getValue()) {
            parameterMap.put("owner", member);
        }
        String inputKey = getSearchText().getValue();
        parameterMap.put("searchValue", "%" + inputKey.trim() + "%");

        TaskHelper helper = new TaskHelper();
        List ls = helper.getListRenderer(parameterMap, false);
        TaskHelper help = new TaskHelper();
        int totalSize = help.countTotalList(parameterMap);
        ls = getCommonPaging().loadPagingData(ls, " List ", false);
        getCommonPaging().setTotalDataRows(totalSize);

        for (int i = 0; i < ls.size(); i++) {
            TaskModel m = (TaskModel) ls.get(i);
            Item id = ic.addItem(m);
            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
            id.getItemProperty(TASK_DATE).setValue(dt.format(m.getTaskDate()));
            id.getItemProperty(TASK_TITLE).setValue(m.getTaskName());
            id.getItemProperty(TASK_STATUS).setValue(getStatusName(m.getTaskStatus()));
        }
        table.setPageLength(ls.size());
        return ic;
    }

    private Object getStatusName(Integer taskStatus) {
        String status = "";
        switch (taskStatus) {
            case 0:
                status = "Open";
                break;
            case 1:
                status = "Close";
                break;
        }
        return status;
    }


    @Override
    public void doDeletion() throws Exception {
        String lc = getLogger().openLog();
        TaskHelper helper = new TaskHelper();
        TaskModel model = (TaskModel) getSelectedObject();
        int rest = helper.deleteTask(model);
        if (rest > 0) doRefreshData();
        else getLogger().log(lc, TenmaLog.LOG_FOR_DELETE, "DELETE Task = " + model.getTaskId());
    }

    @Override
    public void refreshUI() {

    }
}
