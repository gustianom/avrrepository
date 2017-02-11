package com.tenma.common.dao.reminder;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.ReminderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jan 15 16:35:09 WIT 2014
 */
public class ReminderDao extends Dao {
    public ReminderDao(SqlSession session) {
        super(session);
    }

    public ReminderModel getReminder(ReminderModel model) {
        return (ReminderModel) session.selectOne("getReminder", model);
    }

    public int insertReminder(ReminderModel systemProperty) {
        int result = 0;
        result = session.insert("insertReminder", systemProperty);
        return result;
    }

    public int updateReminder(ReminderModel systemProperty) {
        int result = 0;
        result = session.update("updateReminder", systemProperty);
        return result;
    }

    public int deleteReminder(ReminderModel systemProperty) {
        int result = 0;
        result = session.delete("deleteReminder", systemProperty);
        return result;
    }


    public List listReminder() {
        List list = session.selectList("listReminder", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalReminder", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<ReminderModel> listAvailableReminderMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listReminderMap", parameterObject);
        else
            return session.selectList("listAllReminderMap", parameterObject);
    }
}
