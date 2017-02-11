package com.tenma.core.dao.groupcommunity;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.GroupCommunityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Tue Dec 02 22:19:21 ICT 2014
 */
public class GroupCommunityDao extends Dao {
    public GroupCommunityDao(SqlSession session) {
        super(session);
    }

    public GroupCommunityModel getGroupCommunity(GroupCommunityModel model) {
        return (GroupCommunityModel) session.selectOne("getCOREGroupCommunity", model);
    }

    public int insertGroupCommunity(GroupCommunityModel systemProperty) {
        int result = 0;
        result = session.insert("insertCOREGroupCommunity", systemProperty);
        return result;
    }

    public int updateGroupCommunity(GroupCommunityModel systemProperty) {
        int result = 0;
        result = session.update("updateCOREGroupCommunity", systemProperty);
        return result;
    }

    public int deleteGroupCommunity(GroupCommunityModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCOREGroupCommunity", systemProperty);
        return result;
    }


    public List listGroupCommunity() {
        List list = session.selectList("listCOREGroupCommunity", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCOREGroupCommunity", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<GroupCommunityModel> listProductQtty(HashMap parameterObject) {
        return session.selectList("listProductQtty", parameterObject);
    }

    public List<GroupCommunityModel> listAvailableGroupCommunityMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCOREGroupCommunityMap", parameterObject);
        else
            return session.selectList("listAllCOREGroupCommunityMap", parameterObject);
    }
}
