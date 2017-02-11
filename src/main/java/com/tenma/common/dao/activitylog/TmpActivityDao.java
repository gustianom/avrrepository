package com.tenma.common.dao.activitylog;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TmpActivityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Tue Feb 25 11:15:02 ICT 2014
 */
public class TmpActivityDao extends Dao {
    public TmpActivityDao(SqlSession session) {
        super(session);
    }

    public TmpActivityModel getTmpActivity(TmpActivityModel model) {
        return (TmpActivityModel) session.selectOne("getTmpActivity", model);
    }

    public int insertTmpActivity(TmpActivityModel systemProperty) {
        int result = 0;
        result = session.insert("insertTmpActivity", systemProperty);
        return result;
    }

    public int updateTmpActivity(TmpActivityModel systemProperty) {
        int result = 0;
        result = session.update("updateTmpActivity", systemProperty);
        return result;
    }

    public int deleteTmpActivity(TmpActivityModel systemProperty) {
        int result = 0;
        result = session.delete("deleteTmpActivity", systemProperty);
        return result;
    }


    public List listTmpActivity() {
        List list = session.selectList("listTmpActivity", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTmpActivity", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TmpActivityModel> listAvailableTmpActivityMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listTmpActivityMap", parameterObject);
        else
            return session.selectList("listAllTmpActivityMap", parameterObject);
    }
}
