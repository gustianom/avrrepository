package com.tenma.core.dao.communityprofile;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.CommunityProfileModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Oct 05 23:28:51 ICT 2015
 */
public class CommunityProfileDao extends Dao {
    public CommunityProfileDao(SqlSession session) {
        super(session);
    }

    public CommunityProfileModel getCommunityProfile(CommunityProfileModel model) {
        return (CommunityProfileModel) session.selectOne("getCORECommunityProfile", model);
    }

    public int insertCommunityProfile(CommunityProfileModel systemProperty) {
        int result = 0;
        result = session.insert("insertCORECommunityProfile", systemProperty);
        return result;
    }

    public int updateCommunityProfile(CommunityProfileModel systemProperty) {
        int result = 0;
        result = session.update("updateCORECommunityProfile", systemProperty);
        return result;
    }

    public int deleteCommunityProfile(CommunityProfileModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCORECommunityProfile", systemProperty);
        return result;
    }


    public List listCommunityProfile() {
        List list = session.selectList("listCORECommunityProfile", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCORECommunityProfile", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<CommunityProfileModel> listAvailableCommunityProfileMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCORECommunityProfileMap", parameterObject);
        else
            return session.selectList("listAllCORECommunityProfileMap", parameterObject);
    }
}
