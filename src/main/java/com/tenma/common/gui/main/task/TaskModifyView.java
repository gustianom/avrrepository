package com.tenma.common.gui.main.task;

import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.CrudCode;
import com.tenma.model.common.TaskMemberModel;
import com.tenma.model.common.TaskModel;
import com.tenma.share.gui.lookup.LookupMember;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskModifyView extends TenmaMasterFormModify {
    private TaskModify parentWindow;
    private TaskModel modifyModel;
    private Upload upload;

    private List<CommunityMemberModel> listOfMemberPICInvited;
    private List<CommunityMemberModel> listOfMemberCCInvited;

    public TaskModifyView(TaskModel model, int modifyMode, TenmaPanel parentComponent) throws Exception {
        super(parentComponent, modifyMode);
        setTitle("task.title");
        modifyModel = model;
        initializeComponent();
    }

    public TaskModifyView(TaskModel model, int modifyMode, TaskModify components) throws Exception {
        super(null, modifyMode);
        parentWindow = components;
        setTitle("task.title");
        modifyModel = model;

        initializeComponent();
    }

    private void initializeComponent() {
        BeanItem<TaskModel> item = new BeanItem<TaskModel>(modifyModel);
        getPersonForm().setItemDataSource(item);

        populateMemberInvited();

        TextField taskName = new TextField(param.getLabel("task.taskName"));
        taskName.setRequired(true);
        taskName.focus();
        taskName.setWidth("280");

        NativeSelect priority = new NativeSelect(param.getLabel("task.priority"));
        priority.addItem(Constants.TASK_PRIORITY_TYPE.LOW.getValue());
        priority.addItem(Constants.TASK_PRIORITY_TYPE.NORMAL.getValue());
        priority.addItem(Constants.TASK_PRIORITY_TYPE.HIGHT.getValue());
        priority.setItemCaption(Constants.TASK_PRIORITY_TYPE.LOW.getValue(), param.getLabel("default.low"));
        priority.setItemCaption(Constants.TASK_PRIORITY_TYPE.NORMAL.getValue(), param.getLabel("default.normal"));
        priority.setItemCaption(Constants.TASK_PRIORITY_TYPE.HIGHT.getValue(), param.getLabel("default.hight"));
        priority.setValue(Constants.TASK_PRIORITY_TYPE.NORMAL.getValue());

        TextField responsible = new TextField(param.getLabel("task.responsible"));
        responsible.setWidth("80px");
        responsible.setEnabled(false);

        TextField participant = new TextField(param.getLabel("task.participant"));
        participant.setEnabled(false);
        participant.setWidth("80px");

        PopupDateField deadline = new PopupDateField(param.getLabel("task.deadline"));
        Calendar cal = Calendar.getInstance(TA.getCurrent().getLocale());
        deadline.setValue(cal.getTime());
        deadline.setDateFormat("yyyy-MMM-dd");
        deadline.setWidth("120px");

        RichTextArea taskDesc = new RichTextArea(param.getLabel("default.description"));

        upload = new Upload();
        upload.setCaption(param.getLabel("task.document"));
        upload.setEnabled(false);


        HorizontalLayout layPIC = createActorLayout(null, Constants.TASK_MEMBER_TYPE.PIC.getValue());
        HorizontalLayout layCC = createActorLayout(param.getLabel("default.cc"), Constants.TASK_MEMBER_TYPE.CC.getValue());

        HorizontalLayout lytmpPic = new HorizontalLayout();
        lytmpPic.setCaption(param.getLabel("default.pic"));
        lytmpPic.setSpacing(true);
        lytmpPic.addComponent(layPIC);

        TextField taskId = new TextField();
        taskId.setVisible(false);

        getPersonForm().getFieldGroup().bind(taskId, "taskId");
        getPersonForm().getFieldGroup().bind(taskName, "taskName");
        getPersonForm().getFieldGroup().bind(taskDesc, "taskDesc");
        getPersonForm().getFieldGroup().bind(priority, "priority");
        getPersonForm().getFieldGroup().bind(deadline, "deadline");
        getPersonForm().getFieldGroup().bind((NativeSelect) layPIC.getComponent(0), "picMemberType");
        getPersonForm().getFieldGroup().bind((NativeSelect) layCC.getComponent(0), "ccMemberType");

        getPersonForm().addComponent(taskId);
        taskId.setStyleName("textField");
        getPersonForm().addComponent(taskName);
        getPersonForm().addComponent(priority);
        getPersonForm().addComponent(deadline);
        getPersonForm().addComponent(taskDesc);
        getPersonForm().addComponent(upload);
        getPersonForm().addComponent(lytmpPic);
        getPersonForm().addComponent(layCC);

    }

    private void populateMemberInvited() {
        List listMember = null;
        if (modifyModel.getTaskId() != null && modifyModel.getTaskId().trim().length() != 0) {
            HashMap parameter = new HashMap();
            parameter.put("taskId", modifyModel.getTaskId());
            parameter.put("defCommunityId", modifyModel.getCommunityId());
            TaskMemberHelper hlp = new TaskMemberHelper();
            listMember = hlp.getListRenderer(parameter, false);
        }

        listOfMemberPICInvited = new ArrayList();
        listOfMemberCCInvited = new ArrayList();

        if (listMember != null) {
            for (int i = 0; i < listMember.size(); i++) {
                TaskMemberModel mem = (TaskMemberModel) listMember.get(i);
//                if (mem.getMemberType() == Constants.TASK_MEMBER_TYPE.PIC.getValue())
//                    listOfMemberPICInvited.add((CommunityMemberModel) mem);
//                else if (mem.getMemberType() == Constants.TASK_MEMBER_TYPE.CC.getValue())
//                    listOfMemberPICInvited.add((CommunityMemberModel) mem);
            }
        }


    }

    private HorizontalLayout createActorLayout(String caption, int type) {
        List ls = new ArrayList();
        if (type == Constants.TASK_MEMBER_TYPE.PIC.getValue())
            ls = listOfMemberPICInvited;
        else if (type == Constants.TASK_MEMBER_TYPE.CC.getValue())
            ls = listOfMemberCCInvited;

        LookupMember lookupIEDMember = new LookupMember(this, ls, LookupMember.LOOKUP_MEMBER_TYPE.COMMUNITY, true, true);
        lookupIEDMember.setCaption(param.getLabel("default.invite"));
        lookupIEDMember.setOnUpdatedCaption(param.getLabel("default.peopleInvited"));
        lookupIEDMember.setIcon(new ThemeResource("layouts/images/16/002.png"));

        HorizontalLayout layStatus = new HorizontalLayout();
        if ((caption != null) && (caption.trim().length() != 0))
            layStatus.setCaption(caption);

        layStatus.setSpacing(true);

        NativeSelect viewStatus = new NativeSelect();
        viewStatus.addItem(0);
        viewStatus.addItem(1);
        viewStatus.setItemCaption(0, param.getLabel("default.public"));
        viewStatus.setItemCaption(1, param.getLabel("default.custom"));
//        viewStatus.addValueChangeListener(createViewStatusListener(lookupIEDMember));
        viewStatus.setImmediate(true);

        layStatus.addComponent(viewStatus);
        layStatus.addComponent(lookupIEDMember);
        return layStatus;
    }

    private Property.ValueChangeListener createViewStatusListener(final LookupMember lookupIEDMember) {
        Property.ValueChangeListener listener = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                System.out.println("event------>>> " + event.getProperty().getValue());
                System.out.println("event------>>> " + event.getProperty().getValue());
                if (event.getProperty().getValue() != null) {
                    Integer ievent = new Integer(event.getProperty().getValue().toString());
                    if (ievent == 0)
                        lookupIEDMember.setVisible(false);
                    else {
                        lookupIEDMember.setVisible(true);
                        openInvitationWindow();
                    }
                }

            }
        };
        return listener;
    }

    private void openInvitationWindow() {

    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        TaskHelper helper = new TaskHelper();
        setAuditTrail(modifyModel);
        String lc = getLogger().openLog();


        System.out.println("TaskModifyView.doSave");
        if (ADD_MODE == getModifyMode())
            doInsert(helper, lc);
        else
            doUpdate(helper, lc);
    }

    private int doSoftDelete(SqlSession session, TaskMemberHelper memberHelper, HashMap mapExecutedMember) throws Exception {
        List ls = new ArrayList();
        if (listOfMemberPICInvited != null)
            ls.addAll(listOfMemberPICInvited);
        if (listOfMemberCCInvited != null)
            ls.addAll(listOfMemberCCInvited);

        int res = 0;
        for (int i = 0; i < ls.size(); i++) {
            UserModel userModel = (UserModel) ls.get(i);
            if (!mapExecutedMember.containsKey(userModel.getUserId())) {

                TaskMemberModel m = new TaskMemberModel();
                m.setTaskId(modifyModel.getTaskId());
                m.setCommunityId(modifyModel.getCommunityId());
                m.setUserId(userModel.getUserId());
                setAuditTrail(m);
                res = memberHelper.softDeleteRefTaskMember(session, m);
            }
        }
        return res;

    }

    private void doUpdate(TaskHelper helper, String lc) {
        int res = 0;
        TaskMemberHelper refHelper = new TaskMemberHelper();


        SqlSession session = helper.sqlSessionFactory.openSession();

        HashMap mapExecutedMember = new HashMap();

        try {

            getLogger().log(lc, TenmaLog.LOG_FOR_UPDATE, "Update Task =" + modifyModel.getTaskId());
            res = helper.updateTask(session, modifyModel);
            if (res != 0) {
                if (modifyModel.getPicMemberType().equals(1))
                    doRestoreTaskMember(session, mapExecutedMember, Constants.TASK_MEMBER_TYPE.PIC.getValue());
                if (modifyModel.getCcMemberType().equals(1))
                    doRestoreTaskMember(session, mapExecutedMember, Constants.TASK_MEMBER_TYPE.CC.getValue());
            }

            doSoftDelete(session, refHelper, mapExecutedMember);

            session.commit();
            doBack2List();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.UPDATE, e.getMessage(), param.getMessage("system.error.saving"));
        } finally {
            session.close();
        }

    }

    private void doInsert(TaskHelper helper, String lc) {
        if (modifyModel.getTaskName() != null) {
            int res = 0;
            SqlSession session = helper.sqlSessionFactory.openSession();
            try {
                getLogger().log(lc, TenmaLog.LOG_FOR_ADD, "ADD Task =" + modifyModel.getTaskId());
                setAuditTrail(modifyModel);
                res = helper.insertNewTask(session, modifyModel);
                if (modifyModel.getPicMemberType().equals(1))
                    insertTaskMember(session, Constants.TASK_MEMBER_TYPE.PIC.getValue());
                if (modifyModel.getCcMemberType().equals(1))
                    insertTaskMember(session, Constants.TASK_MEMBER_TYPE.CC.getValue());
                session.commit();

                doBack2List();
            } catch (Exception e) {
                session.rollback();
                e.printStackTrace();
                commonMessageHandler(Notification.Type.ERROR_MESSAGE, CrudCode.CREATE, e.getMessage(), param.getMessage("system.error.saving"));
            } finally {
                session.close();
            }
            if (res != 0)
                try {
                    createNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }

    private void createNotification() throws Exception {
        List<CommunityMemberModel> ls = new ArrayList();
        if (listOfMemberPICInvited != null)
            ls.addAll(listOfMemberPICInvited);
        if (listOfMemberCCInvited != null)
            ls.addAll(listOfMemberCCInvited);

        int res = 0;
        HashMap checkMap = new HashMap();
        for (CommunityMemberModel memberModel : ls) {
            if (!checkMap.containsKey(memberModel.getUserId())) {
                checkMap.put(memberModel.getUserId(), memberModel.getUserId());
                com.tenma.common.gui.main.notification.Notification.addNotification(
                        modifyModel.getCommunityId(), modifyModel.getCreatedBy(), memberModel.getUserId(), modifyModel.getTaskName(), Constants.NOTIFICATION_TYPE.TASK, modifyModel.getTaskId()
                );
            }
        }
    }

    private int doRestoreTaskMember(SqlSession session, HashMap mapCheckMember, int memberType) throws Exception {
        List<CommunityMemberModel> ls = null;
        int result = 0;
        if (Constants.TASK_MEMBER_TYPE.PIC.getValue() == memberType)
            ls = listOfMemberPICInvited;
        else if (Constants.TASK_MEMBER_TYPE.CC.getValue() == memberType)
            ls = listOfMemberCCInvited;

        if (ls != null) {
            TaskMemberHelper memberHelper = new TaskMemberHelper();
            for (int i = 0; i < ls.size(); i++) {

                CommunityMemberModel communityMember = ls.get(i);

                boolean isMemberAlreadyExecuted = mapCheckMember.containsKey(communityMember.getUserId());

                if (!isMemberAlreadyExecuted) {
                    TaskMemberModel memberModel = new TaskMemberModel();

                    memberModel.setUserId(communityMember.getUserId());
                    memberModel.setTaskId(modifyModel.getTaskId());
                    memberModel.setCommunityId(modifyModel.getCommunityId());
                    memberModel.setMemberType(memberType);
                    setAuditTrail(memberModel);

                    int res = memberHelper.softRestoreRefTaskMember(session, memberModel);
                    if (res == 0) {
                        System.out.println("TaskModifyView.doRestoreTaskMember " + res);
                        res = memberHelper.insertTaskMember(session, memberModel);
                    }
                    result += res;
                    mapCheckMember.put(communityMember.getUserId(), memberModel);
                }
            }
        }
        return result;

    }


    private int insertTaskMember(SqlSession session, int memberType) throws Exception {
        TaskMemberHelper refHelper = new TaskMemberHelper();
        int res = 0;
        if (Constants.TASK_MEMBER_TYPE.PIC.getValue() == memberType)
            res = refHelper.insertTaskMember(session, modifyModel, memberType, listOfMemberPICInvited);
        else if (Constants.TASK_MEMBER_TYPE.CC.getValue() == memberType)
            res = refHelper.insertTaskMember(session, modifyModel, memberType, listOfMemberCCInvited);
        return res;
    }


    private void doBack2List() {
        if (parentWindow != null) {
//            parentWindow.doRefresh("refreshTask");
        } else if (getParentContainer() != null) {
            TaskList list = (TaskList) getParentContainer();
            TA.getCurrent().updateWorkingArea(list);
            list.refreshTable();
        }
    }
}