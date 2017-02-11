package com.tenma.common.dao.reference.reftaskmember;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TaskMemberModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Nov 23 13:09:00 WIT 2013
 */
public class TaskMemberDao extends Dao {
    public TaskMemberDao(SqlSession session) {
        super(session);
    }

    public TaskMemberModel getRefTaskMember(TaskMemberModel model) {
        return (TaskMemberModel) session.selectOne("getRefTaskMember", model);
    }

    public int insertRefTaskMember(TaskMemberModel systemProperty) {
        int result = 0;
//        result = session.insert("insertRefTaskMember", systemProperty);
        return result;
    }

    public int updateRefTaskMember(TaskMemberModel systemProperty) {
        int result = 0;
        result = session.update("updateRefTaskMember", systemProperty);

        return result;
    }

    public int deleteRefTaskMember(TaskMemberModel systemProperty) {
        int result = 0;
        result = session.delete("deleteRefTaskMember", systemProperty);
        return result;
    }

    public int softDeleteRefTaskMember(TaskMemberModel systemProperty) {
        int result = 0;
        result = session.delete("softDeleteRefTaskMember", systemProperty);
        return result;
    }

    public int softRestoreRefTaskMember(TaskMemberModel systemProperty) {
        int result = 0;
        result = session.delete("softRestoreRefTaskMember", systemProperty);
        return result;
    }

    public List listRefTaskMember() {
        List list = session.selectList("listRefTaskMember", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalRefTaskMember", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TaskMemberModel> listAvailableRefTaskMemberMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listRefTaskMemberMap", parameterObject);
        else
            return session.selectList("listAllRefTaskMemberMap", parameterObject);
    }
}
