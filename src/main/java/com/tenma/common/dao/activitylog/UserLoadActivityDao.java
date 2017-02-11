package com.tenma.common.dao.activitylog;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.UserLoadActivityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Thu Feb 13 12:48:04 ICT 2014
 */
public class UserLoadActivityDao extends Dao {
    public UserLoadActivityDao(SqlSession session) {
        super(session);
    }

    public UserLoadActivityModel getUserLoadActivity(UserLoadActivityModel model) {
        return (UserLoadActivityModel) session.selectOne("getUserLoadActivity", model);
    }

    public int insertUserLoadActivity(UserLoadActivityModel systemProperty) {
        int result = 0;
        result = session.insert("insertUserLoadActivity", systemProperty);
        return result;
    }

    public int updateUserLoadActivity(UserLoadActivityModel systemProperty) {
        int result = 0;
        result = session.update("updateUserLoadActivity", systemProperty);
        return result;
    }

    public int deleteUserLoadActivity(UserLoadActivityModel systemProperty) {
        int result = 0;
        result = session.delete("deleteUserLoadActivity", systemProperty);
        return result;
    }


    public List listUserLoadActivity() {
        List list = session.selectList("listLastUserActivity", null);
        return list;
    }

    public List listUserLoadActivity(HashMap map) {
        List list = session.selectList("listLastUserActivity", map);
        return list;
    }

    public List listTopMenuActivity(HashMap map) {
        List list = session.selectList("listTopMenuActivity", map);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalUserLoadActivity", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public int countUserLoadActivity(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countUserLoadActivity", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List<UserLoadActivityModel> listAvailableUserLoadActivityMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUserLoadActivityMap", parameterObject);
        else
            return session.selectList("listAllUserLoadActivityMap", parameterObject);
    }

    public List<UserLoadActivityModel> listUserLoadActivityToExcel(HashMap parameterObject, boolean includeNavigation) {
        return session.selectList("listUserLoadActivitytoExcel", parameterObject);
    }


    public List<UserLoadActivityModel> listCountUserLoadActivity(HashMap parameterObject, boolean includeNavigation) {
        return session.selectList("listCountUserLoadActivity", parameterObject);
    }

    public List<UserLoadActivityModel> listCountUserLoadActivityByDate(HashMap parameterObject, boolean includeNavigation) {
        return session.selectList("listCountUserLoadActivityByDate", parameterObject);
    }

    public List<UserLoadActivityModel> listCountUserLoadActivityByUser(HashMap parameterObject, boolean includeNavigation) {
        return session.selectList("listCountUserLoadActivityByUser", parameterObject);
    }
}
