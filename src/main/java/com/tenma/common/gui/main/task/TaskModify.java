package com.tenma.common.gui.main.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenma.auth.model.MemberModel;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.gui.display.TenmaButtonList;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskModel;
import com.tenma.share.gui.display.component.MemberAutoField;
import com.tenma.share.gui.display.component.MemberSelection;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/13/13
 * Time: 3:27 PM
 */
public class TaskModify extends TenmaMasterFormModify implements MemberSelection {
    List listMemberPic = new ArrayList<>();
    HashMap mapPic = new HashMap();
    private TaskModifyView modifyView;
    private TenmaButtonList parentComponent;
    private VerticalLayout lyPic = new VerticalLayout();
    private TaskModel modifyModel;
    private MemberAutoField tToPIC;
    private CCSelection tCC;
    private PopupDateField tDate;
    private TextField tTitle;
    private TextArea tDesc;
    private PopupDateField tDueDate;
    private CheckBox tPrivate;
    private CheckBox tAllowCC;
    private Button btnSave;
    private Button btnCancel;


    public TaskModify(TenmaPanel parentContainer, int modifyMode) {
        super(parentContainer, modifyMode);
        GUI();
    }

    private void GUI() {
        this.getHeader().setVisible(false);
        Calendar cal = Calendar.getInstance();
        tToPIC = new MemberAutoField(this);
        tCC = new CCSelection();
        tCC.setVisible(false);
//        tCC.setCaption("CC");
        tDate = new PopupDateField("Date");
        tDate.setValue(cal.getTime());
        tDate.setEnabled(false);
        tTitle = new TextField("Title");
        tDesc = new TextArea("Desc");
        tDueDate = new PopupDateField("Due Date");
        tPrivate = new CheckBox("Private");
        tAllowCC = new CheckBox("");
        tAllowCC.addValueChangeListener(event -> actionClick(event));

        tTitle.setWidth("300px");
        tDesc.setWidth("400px");

        btnCancel = getButCancel();
        btnSave = getButSave();

        Label lTitle = new Label("TASK");
        VerticalLayout vlPic = new VerticalLayout(tToPIC, lyPic);
        HorizontalLayout hPic = new HorizontalLayout(vlPic, new Label("cc"), tAllowCC, tCC);
        hPic.setSpacing(true);
        hPic.setCaption("PIC");

        FormLayout form = new FormLayout();
        form.addComponent(hPic);
        form.addComponent(tDate);
        form.addComponent(tTitle);
        form.addComponent(tDesc);
        form.addComponent(tDueDate);
        form.addComponent(tPrivate);

        HorizontalLayout hHeader = new HorizontalLayout();
        lTitle.setPrimaryStyleName("headerTitle");

        hHeader.addComponent(lTitle);
        hHeader.setWidth("800px");
        hHeader.setHeight("35px");
        hHeader.setStyleName("metro-slate-grey");

        HorizontalLayout hFooter = new HorizontalLayout();
        HorizontalLayout hBtnLay = new HorizontalLayout();
        hBtnLay.addComponent(btnSave);
        hBtnLay.addComponent(btnCancel);
        hFooter.addComponent(hBtnLay);
        hFooter.setComponentAlignment(hBtnLay, Alignment.MIDDLE_RIGHT);
        hFooter.setWidth("800px");
        hFooter.setHeight("75px");
        hFooter.setStyleName("metro-metro-grey");

        VerticalLayout customUI = new VerticalLayout();

        getPersonForm().setWidth("800px");
        getPersonForm().addComponent(hFooter);
        getPersonForm().setPrimaryStyleName("metro-layout");


        HorizontalLayout hzRoot = new HorizontalLayout(form);
        hzRoot.setWidth("100%");

        getPersonForm().addComponent(hzRoot);

        customUI.addComponent(hHeader);
        customUI.addComponent(getPersonForm());
        customUI.addComponent(hFooter);
        customUI.setComponentAlignment(hHeader, Alignment.TOP_CENTER);
        customUI.setComponentAlignment(getPersonForm(), Alignment.TOP_CENTER);
        customUI.setComponentAlignment(hFooter, Alignment.BOTTOM_CENTER);
        customUI.setMargin(new MarginInfo(true, true, false, true));

        getPersonForm().setSpacing(true);
        getPersonForm().setMargin(true);
        this.setContent(customUI);


        if (isAddMode()) {
            modifyModel = new TaskModel();
        } else {
            modifyModel = (TaskModel) ((TenmaPanel) getParentContainer()).getSelectedObject();
            TaskModel tmp = new TaskModel();
            tmp.setCommunityId(modifyModel.getCommunityId());
            tmp.setTaskId(modifyModel.getTaskId());
            modifyModel = new TaskHelper().getTaskDetail(tmp);

            tDueDate.setValue(modifyModel.getTaskDate());
            tTitle.setValue(modifyModel.getTaskName());
            tDesc.setValue(modifyModel.getTaskDesc());
            tDate.setValue(modifyModel.getTaskDate());
            tDueDate.setValue(modifyModel.getDeadline());
            if (modifyModel.getIsPrivate() != null && modifyModel.getIsPrivate())
                tPrivate.setValue(modifyModel.getIsPrivate());
            Type listType = new TypeToken<List<Integer>>() {
            }.getType();
            listMemberPic = new Gson().fromJson(modifyModel.getPicMemberType(), listType);
            System.out.println("listMemberPic = " + listMemberPic);
            if (listMemberPic.size() > 0)
                setMemberMapPic();
            generateMemberPIC();
            List listMemberCc = new Gson().fromJson(modifyModel.getCcMemberType(), listType);
            if (listMemberCc != null)
                if (listMemberCc.size() > 0) {
                    tAllowCC.setValue(true);
                    tCC.setListMemberCc(listMemberCc);

                }
        }
    }

    private void actionClick(Property.ValueChangeEvent click) {
        Boolean val = (Boolean) click.getProperty().getValue();
        if (val != null)
            tCC.setVisible(val);
    }

    @Override
    public void doCancel() {
        doBack2List();
    }

    private String getStringJsonMemberPIC() {
        return new Gson().toJson(listMemberPic);
    }

    @Override
    public void doSave() throws Exception {
        String lc = getLogger().openLog();
        TaskHelper helper = new TaskHelper();
        System.out.println("tCC.getStringJsonMemberCc() = " + tCC.getStringJsonMemberCc());

        modifyModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        modifyModel.setOwnerId(TA.getCurrent().getClientInfo().getClientUserModel().getMemberId());
        modifyModel.setPicMemberType(getStringJsonMemberPIC());
        if (tAllowCC.getValue()) {
            System.out.println("TaskModify.doSave : " + tCC.getStringJsonMemberCc());
            modifyModel.setCcMemberType(tCC.getStringJsonMemberCc());
        }
        modifyModel.setTaskDate(tDate.getValue());
        modifyModel.setTaskName(tTitle.getValue());
        modifyModel.setTaskDesc(tDesc.getValue());
        if (tDueDate.getValue() != null) {
            modifyModel.setDeadline(tDueDate.getValue());
        }
        modifyModel.setIsPrivate(tPrivate.getValue());
        modifyModel.setTaskStatus(Constants.TASK_STATUS.OPEN.getValue());
        setAuditTrail(modifyModel);
        int res = 0;
        if (isAddMode()) {
            try {
                res = helper.insertNewTask(modifyModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                System.out.println("modifyModel.getIsPrivate() = " + modifyModel.getIsPrivate());
                System.out.println("modifyModel.getIsPrivate() = " + modifyModel.getIsPrivate());
                System.out.println("modifyModel.getIsPrivate() = " + modifyModel.getIsPrivate());
                System.out.println("modifyModel.getIsPrivate() = " + modifyModel.getIsPrivate());
                res = helper.updateTask(modifyModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (res != 0) {
            if (isAddMode()) {
                getLogger().log(lc, TenmaLog.LOG_FOR_ADD, "ADD TransactionReguler CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());
            } else {
                getLogger().log(lc, TenmaLog.LOG_FOR_UPDATE, "UPDATE TransactionReguler CommunityID = " + TA.getCurrent().getCommunityModel().getCommunityId());

            }
            doBack2List();
        } else {
            Notification.show(param.getMessage("system.error.saving"), Notification.Type.ERROR_MESSAGE);
        }
    }


    private void doBack2List() {
        TaskViewList list = new TaskViewList();
        TA.getCurrent().updateWorkingArea(list);
    }

    private void setMemberMapPic() {
        MemberHelper memberHelper = new MemberHelper();
        HashMap map = new HashMap();
        map.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
        List list = memberHelper.getListRenderer(map, false);
        for (int x = 0; x < listMemberPic.size(); x++) {
            Object obj = listMemberPic.get(x);
            mapPic.put(obj, obj);
        }

        for (int x = 0; x < list.size(); x++) {
            MemberModel memberModel = (MemberModel) list.get(x);
            if (mapPic.containsKey(memberModel.getMemberId())) {
                mapPic.put(memberModel.getMemberId(), memberModel.getMemberName());
            }
        }
    }

    @Override
    public void setMemberModel(MemberModel memberModel) {
        if (!mapPic.containsKey(memberModel.getMemberId())) {
            mapPic.put(memberModel.getMemberId(), memberModel.getMemberName());
            listMemberPic.add(memberModel.getMemberId());
        }
        generateMemberPIC();
    }

    private void generateMemberPIC() {
        System.out.println("TaskModify.generateMemberPIC");
        lyPic.removeAllComponents();
        lyPic.setMargin(false);
        if (listMemberPic.size() > 0) {
            int counter = 0;
            HorizontalLayout hz = new HorizontalLayout();
            lyPic.addComponent(hz);
            for (int i = 0; i < listMemberPic.size(); i++) {
                Object m = listMemberPic.get(i);
                counter++;
                if (counter > 2) {
                    counter = 0;
                    hz = new HorizontalLayout();
                    lyPic.addComponent(hz);
                }
                hz.setMargin(false);
                Button btn = new Button();
                btn.addClickListener(click -> actionDeleteMember(m));
                btn.setIcon(new ThemeResource("layouts/images/16/closed.png"));
                btn.setPrimaryStyleName("btnClosedwelcome");
                Label space = new Label();
                space.setWidth("20px");
                HorizontalLayout h = new HorizontalLayout(new Label(String.valueOf(mapPic.get(m))), space, btn);
                h.setStyleName("studentroot");
                hz.addComponent(h);
            }
        }
    }

    private void actionDeleteMember(Object m) {
        listMemberPic.remove(m);
        generateMemberPIC();
    }


    //@ Deprecated
//    public TaskModify(TenmaRootApplication apps, TenmaButtonList parentComponent, int modifyMode) throws Exception {
//        super(apps);
//        this.parentComponent = parentComponent;
//
//        if (modifyMode == TenmaMasterFormModify.UPDATE_MODE) {
//            modifyModel = (TaskModel) parentComponent.getSelectedObject();
//            TaskModel m = new TaskModel();
//            TaskHelper helper = new TaskHelper();
//            m.setTaskId(modifyModel.getTaskId());
//            m.setCommunityId(getTenmaApplication().getCommunityModel().getCommunityId());
//            modifyModel = helper.getTaskDetail(m);
//        } else {
//            modifyModel = new TaskModel();
//            modifyModel.setCommunityId(getTenmaApplication().getCommunityModel().getCommunityId());
//            modifyModel.setDeadline(Calendar.getInstance(getTenmaApplication().getLocale()).getTime());
//            modifyModel.setPriority(0);
//            modifyModel.setTaskId("");
//            modifyModel.setTaskDesc("");
//            modifyModel.setPicMemberType("");
//            modifyModel.setCcMemberType("");
//            modifyModel.setTaskName("");
//            modifyModel.setTaskActionId(null); //null value means manual
//
//        }
//        modifyView = new TaskModifyView(apps, modifyModel, modifyMode, this);
//        modifyView.setWidth("50%");
//        setContent(modifyView);
//    }
//
//    @Override
//    public void doRefresh(Object newData) {
//        UI.getCurrent().removeWindow(this);
//        parentComponent.refreshTable();
//    }
//
//    @Override
//    public Object getSelectedObject() {
//        return null;
//    }
//
//    @Override
//    public void setSelectedObject(Object obj) {
//
//    }
}
