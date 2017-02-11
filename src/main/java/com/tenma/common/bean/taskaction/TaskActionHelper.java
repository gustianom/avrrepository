package com.tenma.common.bean.taskaction;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.taskaction.TaskActionDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.TaskActionModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Tue Dec 03 12:10:42 WIT 2013
 */
public class TaskActionHelper extends TenmaHelper {
    public int insertNewTaskAction(TaskActionModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskActionDao dao = new TaskActionDao(session);
            TaskActionModel grpModel = new TaskActionModel();
            if (model.getActionClass() != null && model.getActionClass().trim().length() != 0)
                grpModel.setActionClass(model.getActionClass());
            if (model.getActionClass() != null && model.getActionClass().trim().length() != 0)
                grpModel.setActionClass(model.getActionClass());
            else
                grpModel.setActionName(model.getActionName());

            grpModel = dao.getTaskAction(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertTaskAction(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
                session.commit();
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateTaskAction(TaskActionModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TaskActionDao dao = new TaskActionDao(session);
            ret = dao.updateTaskAction(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskActionDao dao = new TaskActionDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskActionDao dao = new TaskActionDao(session);
            return dao.listAvailableTaskActionMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TaskActionModel getTaskActionDetail(TaskActionModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        TaskActionModel m = null;
        try {
            m = getTaskActionDetail(session, model);
        } finally {
            session.close();
        }
        return m;
    }

    public TaskActionModel getTaskActionDetail(SqlSession session, TaskActionModel model) {
        TaskActionDao dao = new TaskActionDao(session);
        return dao.getTaskAction(model);
    }

    public int deleteTaskAction(TaskActionModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TaskActionDao dao = new TaskActionDao(session);
            ret = dao.deleteTaskAction(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listTaskAction() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskActionDao dao = new TaskActionDao(session);
            return dao.listTaskAction();
        } finally {
            session.close();
        }
    }
}