package com.tenma.common.bean.reftaskmember;

import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.reference.reftaskmember.TaskMemberDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.TaskMemberModel;
import com.tenma.model.common.TaskModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Nov 23 13:09:00 WIT 2013
 */
public class TaskMemberHelper extends TenmaHelper {
    public int insertTaskMember(TaskModel model, int memberType, List<CommunityMemberModel> listOfMemberDetailInvited) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertTaskMember(session, model, memberType, listOfMemberDetailInvited);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertTaskMember(SqlSession session, TaskModel model, int memberType, List<CommunityMemberModel> listOfMemberDetailInvited) throws Exception {

        int result = 0;
        TaskMemberDao dao = new TaskMemberDao(session);

        if (listOfMemberDetailInvited != null) {
            for (int i = 0; i < listOfMemberDetailInvited.size(); i++) {
                CommunityMemberModel userModel = listOfMemberDetailInvited.get(i);

                TaskMemberModel grpModel = new TaskMemberModel();
                grpModel.setTaskId(model.getTaskId());
                grpModel.setUserId(userModel.getUserId());
                grpModel.setCommunityId(model.getCommunityId());
                grpModel.setMemberType(memberType);

                TaskMemberModel tmp = dao.getRefTaskMember(grpModel);
                if (tmp == null) {
                    grpModel.setCreatedBy(model.getCreatedBy());
                    grpModel.setCreatedDate(model.getCreatedDate());
                    grpModel.setCreatedFrom(model.getCreatedFrom());

                    grpModel.setUpdatedBy(model.getUpdatedBy());
                    grpModel.setUpdatedDate(model.getUpdatedDate());
                    grpModel.setUpdatedFrom(model.getUpdatedFrom());

                    result += insertTaskMember(session, grpModel);
                }
            }
        }
        return result;
    }

    public int insertTaskMember(SqlSession session, TaskMemberModel taskMemberModel) throws Exception {
        TaskMemberDao dao = new TaskMemberDao(session);
        return dao.insertRefTaskMember(taskMemberModel);
    }

    public int updateRefTaskMember(TaskMemberModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            ret = updateRefTaskMember(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateRefTaskMember(SqlSession session, TaskMemberModel model) throws Exception {
        int ret = 0;
        TaskMemberDao dao = new TaskMemberDao(session);
        ret = dao.updateRefTaskMember(model);
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<TaskMemberModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            return dao.listAvailableRefTaskMemberMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TaskMemberModel getRefTaskMemberDetail(TaskMemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            return dao.getRefTaskMember(model);
        } finally {
            session.close();
        }
    }

    public int deleteRefTaskMember(TaskMemberModel m) throws Exception {
        int ret = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            deleteRefTaskMember(session, m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ret;
    }

    public int deleteRefTaskMember(SqlSession session, TaskMemberModel m) throws Exception {
        int ret = 0;
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            ret = dao.deleteRefTaskMember(m);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        }
        return ret;

    }

    public int softDeleteRefTaskMember(TaskMemberModel m) throws Exception {
        int ret = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ret = softDeleteRefTaskMember(session, m);
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
        return ret;

    }

    public int softDeleteRefTaskMember(SqlSession session, TaskMemberModel m) throws Exception {
        int ret = 0;
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            ret = dao.softDeleteRefTaskMember(m);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        }
        return ret;

    }

    public int softRestoreRefTaskMember(SqlSession session, TaskMemberModel m) throws Exception {
        int ret = 0;
        TaskMemberDao dao = new TaskMemberDao(session);
        ret = dao.softRestoreRefTaskMember(m);
        return ret;

    }

    public List listRefTaskMember() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskMemberDao dao = new TaskMemberDao(session);
            return dao.listRefTaskMember();
        } finally {
            session.close();
        }
    }
}