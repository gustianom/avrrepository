package com.tenma.common.bean.task;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.task.TaskDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.model.common.TaskModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TaskHelper extends TenmaDaoHelper {
    public int insertNewTask(TaskModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewTask(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewTask(SqlSession session, TaskModel model) throws Exception {
        int result = 0;
        TaskDao dao = new TaskDao(session);
        SequenceTool tools = SequenceTool.getInstance();
        int seq = tools.getNewCounter(model.getCommunityId(), SEQUENCE_Constants.TASK_SEQUENCE, false);
        String seqId = tools.addZeroPadWithYear(seq, 14, 2);
        model.setTaskId(seqId);

        result = dao.insertTask(model);
        if (result == 0)
            throw new Exception(Constants.ADD_FAILED);
        //}
        return result;
    }

    public int updateTask(TaskModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateTask(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateTask(SqlSession session, TaskModel model) throws Exception {
        int ret = 0;
        TaskDao dao = new TaskDao(session);
        ret = dao.updateTask(model);
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskDao dao = new TaskDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskDao dao = new TaskDao(session);
            return dao.getTaskList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public TaskModel getTaskDetail(TaskModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TaskDao dao = new TaskDao(session);
            return dao.getTaskDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteTask(TaskModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TaskDao dao = new TaskDao(session);
            ret = dao.deleteTask(m);
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

}
