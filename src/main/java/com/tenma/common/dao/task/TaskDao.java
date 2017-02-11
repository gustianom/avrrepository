package com.tenma.common.dao.task;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TaskModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TaskDao extends Dao {
    public TaskDao(SqlSession session) {
        super(session);
    }

    public TaskModel getTaskDetail(TaskModel model) {
        TaskModel m = null;
        m = (TaskModel) session.selectOne("getTaskObject", model);
        return m;
    }

    public int insertTask(TaskModel task) {
        int result = 0;
        result = session.insert("insertTask", task);
        return result;
    }

    public int updateTask(TaskModel task) {
        int result = 0;
        result = session.update("updateTask", task);

        return result;
    }

    public int deleteTask(TaskModel task) {
        int result = 0;
        result = session.delete("deleteTask", task);
        return result;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("getTaskCount", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List getTaskList(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("getTaskList", parameterObject);
        else
            return session.selectList("getAllTaskList", parameterObject);
    }


}

