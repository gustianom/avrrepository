package com.tenma.sms.dao.smscommunityprofile;

import com.tenma.auth.dao.Dao;
import com.tenma.sms.model.SmsCommunityProfileModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Fri Dec 13 15:28:34 WIT 2013
 */
public class SmsCommunityProfileDao extends Dao {
    public SmsCommunityProfileDao(SqlSession session) {
        super(session);
    }

    public SmsCommunityProfileModel getSmsCommunityProfile(SmsCommunityProfileModel model) {
        return (SmsCommunityProfileModel) session.selectOne("getSmsCommunityProfile", model);
    }

    public int insertSmsCommunityProfile(SmsCommunityProfileModel systemProperty) {
        int result = 0;
        result = session.insert("insertSmsCommunityProfile", systemProperty);
        return result;
    }

    public int updateSmsCommunityProfile(SmsCommunityProfileModel systemProperty) {
        int result = 0;
        result = session.update("updateSmsCommunityProfile", systemProperty);
        return result;
    }

    public int deducSMSBalance(SmsCommunityProfileModel systemProperty) {
        int result = 0;
        result = session.update("deductSMSBalance", systemProperty);
        return result;
    }

    public int topUpSMSBalance(SmsCommunityProfileModel systemProperty) {
        int result = 0;
        result = session.update("topUpSMSBalance", systemProperty);
        return result;
    }

    public int getCurrentSMSBalance(SmsCommunityProfileModel systemProperty) {
        Integer result = 0;
        result = (Integer) session.selectOne("getCurrentSMSBalance", systemProperty);
        return result == null ? 0 : result.intValue();
    }

    public int deleteSmsCommunityProfile(SmsCommunityProfileModel systemProperty) {
        int result = 0;
        result = session.delete("deleteSmsCommunityProfile", systemProperty);
        return result;
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalSmsCommunityProfile", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<SmsCommunityProfileModel> listAvailableSmsCommunityProfileMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listSmsCommunityProfileMap", parameterObject);
        else
            return session.selectList("listAllSmsCommunityProfileMap", parameterObject);
    }
}
