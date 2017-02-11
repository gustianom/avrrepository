package com.tenma.common.gui.main.taskaction;

import com.tenma.common.bean.reftaskmember.TaskMemberHelper;
import com.tenma.common.bean.task.TaskHelper;
import com.tenma.common.bean.taskaction.TaskActionHelper;
import com.tenma.common.gui.main.notification.Notification;
import com.tenma.common.util.Constants;
import com.tenma.model.common.TaskActionModel;
import com.tenma.model.common.TaskMemberModel;
import com.tenma.model.common.TaskModel;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by PT Tenma Bright Sky
 * User: gustianom
 * Date: 12/5/13
 * Time: 3:32 PM
 */
public class Task {
    private Task() {
    }

    public static void doGenerate(final GENERATE action, final String assignedCommuntyId, final String assignedUserId, final String creatorUserId, final String creatorRemoteIpAddress, final boolean isSystemMandatoryTask) throws Exception {
        System.out.println("Task.doGenerate");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    proceedTaskGeneration(action, assignedCommuntyId, assignedUserId, creatorUserId, creatorRemoteIpAddress, isSystemMandatoryTask);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void doGenerate(final int actionId, final String assignedCommuntyId, final String assignedUserId, final String creatorUserId, final String creatorRemoteIpAddress, final boolean isSystemMandatoryTask) throws Exception {
        System.out.println("Task.doGenerate");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    proceedTaskGeneration(actionId, assignedCommuntyId, assignedUserId, creatorUserId, creatorRemoteIpAddress, isSystemMandatoryTask);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private synchronized static void proceedTaskGeneration(GENERATE action, String assignedCommuntyId, String assignedUserId, String creatorUserId, String creatorRemoteIpAddress, boolean isSystemMandatoryTask) throws Exception {
        int vl = action.getValue();
        proceedGeneration(vl, assignedCommuntyId, assignedUserId, creatorUserId, creatorRemoteIpAddress, isSystemMandatoryTask);
    }

    private synchronized static void proceedTaskGeneration(int actionId, String assignedCommuntyId, String assignedUserId, String creatorUserId, String creatorRemoteIpAddress, boolean isSystemMandatoryTask) throws Exception {
        proceedGeneration(actionId, assignedCommuntyId, assignedUserId, creatorUserId, creatorRemoteIpAddress, isSystemMandatoryTask);
    }

    private static void proceedGeneration(int actionId, String assignedCommuntyId, String assignedUserId, String creatorUserId, String creatorRemoteIpAddress, boolean isSystemMandatoryTask) throws Exception {
        TaskActionHelper hlp = new TaskActionHelper();

        SqlSession session = hlp.sqlSessionFactory.openSession();
        try {

            TaskActionModel md = new TaskActionModel();
            md.setActionId(actionId);

            md = hlp.getTaskActionDetail(session, md);
            if (md == null)
                throw new Exception(Constants.OBJEC_NOT_EXIST);
            else {
                TaskModel model = new TaskModel();
                model.setTaskName(md.getActionName());
                model.setTaskDesc(md.getActionDesc());
                model.setTaskActionId(actionId);
                model.setTaskActionClass(md.getActionClass());
                model.setPicMemberType(String.valueOf(Constants.TASK_MEMBER_TYPE.PIC.getValue()));
                model.setCommunityId(assignedCommuntyId);
                model.setPriority(Constants.TASK_PRIORITY_TYPE.HIGHT.getValue());

                model.setCreatedFrom(creatorRemoteIpAddress);
                model.setUpdatedFrom(creatorRemoteIpAddress);
                model.setCreatedBy(creatorUserId);
                model.setUpdatedBy(creatorUserId);
                model.setSystemMandatory(isSystemMandatoryTask ? Constants.TASK_SYSTEM_MANDATORY_STATUS.MANDATORY.getValue() : Constants.TASK_SYSTEM_MANDATORY_STATUS.NORMAL.getValue());


                TaskHelper taskHelper = new TaskHelper();
                int res = 0;
                res = taskHelper.insertNewTask(session, model);
                if (res != 0) {
                    TaskMemberModel m = new TaskMemberModel();
                    m.setTaskId(model.getTaskId());
                    m.setTodoStatus(Constants.TODO_STATUS.NEW.getValue());
                    m.setCommunityId(assignedCommuntyId);
                    m.setUserId(assignedUserId);
                    m.setPicMember(model.getPicMemberType());
                    m.setCreatedBy(creatorUserId);
                    m.setUpdatedBy(creatorUserId);
                    m.setCreatedFrom(model.getCreatedFrom());
                    m.setUpdatedFrom(model.getUpdatedFrom());


                    TaskMemberHelper memberHelper = new TaskMemberHelper();
                    res = memberHelper.insertTaskMember(session, m);
                }
                session.commit();

                if (res != 0)
                    Notification.addNotification(assignedCommuntyId, creatorUserId, assignedUserId, model.getTaskName(), Constants.NOTIFICATION_TYPE.TASK, model.getTaskId());

            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    /**
     * This field is related to table M_TASK_ACTION
     */
    @Deprecated //move to constants TASK_GENERATE_ID
    public static enum GENERATE {
        CORPORATE_SETUP_ACCOUNT(1),
        CORPORATE_SETUP_SYSTEM_ACCOUNT(2),
        COMMUNITY_INVITE_MEMBER(3),
        SMS_THRESHOLD_REACHED(4),
        SMS_BALANCE_EMPTY(5);

        private int methodvalue = 1;

        private GENERATE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }


        @Override
        public String toString() {
            return String.valueOf(getValue());
        }
    }


}