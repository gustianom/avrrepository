package com.tenma.common.dao.communitysosmed;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.CommunitySosmedModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Dec 23 21:11:11 WIT 2013
 */
public class CommunitySosmedDao extends Dao {
    public CommunitySosmedDao(SqlSession session) {
        super(session);
    }

    public CommunitySosmedModel getCommunitySosmed(CommunitySosmedModel model) {
        return (CommunitySosmedModel) session.selectOne("getCommunitySosmed", model);
    }

    public int insertCommunitySosmed(CommunitySosmedModel systemProperty) {
        int result = 0;
        result = session.insert("insertCommunitySosmed", systemProperty);
        return result;
    }

    public int updateCommunitySosmed(CommunitySosmedModel systemProperty) {
        int result = 0;
        result = session.update("updateCommunitySosmed", systemProperty);
        return result;
    }

    public int deleteCommunitySosmed(CommunitySosmedModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCommunitySosmed", systemProperty);
        return result;
    }


    public List listCommunitySosmed() {
        List list = session.selectList("listCommunitySosmed", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunitySosmed", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<CommunitySosmedModel> listAvailableCommunitySosmedMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCommunitySosmedMap", parameterObject);
        else
            return session.selectList("listAllCommunitySosmedMap", parameterObject);
    }
}
