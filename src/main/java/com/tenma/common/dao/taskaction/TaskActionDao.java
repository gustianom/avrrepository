package com.tenma.common.dao.taskaction;

import com.tenma.auth.dao.Dao;
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
public class TaskActionDao extends Dao {
    public TaskActionDao(SqlSession session) {
        super(session);
    }

    public TaskActionModel getTaskAction(TaskActionModel model) {
        return (TaskActionModel) session.selectOne("getTaskAction", model);
    }

    public int insertTaskAction(TaskActionModel systemProperty) {
        int result = 0;
        result = session.insert("insertTaskAction", systemProperty);
        return result;
    }

    public int updateTaskAction(TaskActionModel systemProperty) {
        int result = 0;
        result = session.update("updateTaskAction", systemProperty);

        return result;
    }

    public int deleteTaskAction(TaskActionModel systemProperty) {
        int result = 0;
        result = session.delete("deleteTaskAction", systemProperty);
        return result;
    }


    public List listTaskAction() {
        List list = session.selectList("listTaskAction", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTaskAction", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TaskActionModel> listAvailableTaskActionMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listTaskActionMap", parameterObject);
        else
            return session.selectList("listAllTaskActionMap", parameterObject);
    }
}
