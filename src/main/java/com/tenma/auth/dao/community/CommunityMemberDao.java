package com.tenma.auth.dao.community;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.CommunityMemberModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Fri Feb 21 16:03:48 ICT 2014
 */
public class CommunityMemberDao extends Dao {
    public CommunityMemberDao(SqlSession session) {
        super(session);
    }

    public CommunityMemberModel getCommunityMember(CommunityMemberModel model) {
        return (CommunityMemberModel) session.selectOne("getCommunityMember", model);
    }


    public int getMemberStatus(CommunityMemberModel model) {
        return (Integer) session.selectOne("getCommunityMemberStatus", model);
    }

    public int insertCommunityMember(CommunityMemberModel systemProperty) {
        int result = 0;
        result = session.insert("insertCommunityMember", systemProperty);
        return result;
    }

    public int updateCommunityMember(CommunityMemberModel systemProperty) {
        int result = 0;
        result = session.update("updateCommunityMember", systemProperty);
        session.commit();
        return result;
    }

    public int updateCommunityMemberType(CommunityMemberModel systemProperty) {
        int result = 0;
        result = session.update("updateCommunityMemberType", systemProperty);
        session.commit();
        return result;
    }


    public int deleteCommunityMember(CommunityMemberModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCommunityMember", systemProperty);
        return result;
    }


    public int countTotalActiveList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunityMemberActive", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public int countTotalNewActiveList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunityNewMember", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunityMember", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<CommunityMemberModel> listAvailableCommunityMemberMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCommunityMemberMap", parameterObject);
        else
            return session.selectList("listAllCommunityMemberMap", parameterObject);
    }


    public List<CommunityMemberModel> listCommunityNewestMemberMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCommunityNewestMemberMap", parameterObject);
        else
            return session.selectList("listAllCommunityNewestMemberMap", parameterObject);
    }

    public int checkCountExistingMember(CommunityMemberModel grpModel) {
        Integer count = (Integer) session.selectOne("checkCountExistingMember", grpModel);
        return count == null ? 0 : count.intValue();
    }
}