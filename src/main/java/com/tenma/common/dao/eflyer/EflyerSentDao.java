package com.tenma.common.dao.eflyer;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.EFlyerSentTrackModel;
import com.tenma.model.common.EflyerSentModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Apr 02 13:05:01 ICT 2015
 */
public class EflyerSentDao extends Dao {
    public EflyerSentDao(SqlSession session) {
        super(session);
    }

    public EflyerSentModel getEflyerSent(EflyerSentModel model) {
        return (EflyerSentModel) session.selectOne("getUDWEflyerSent", model);
    }

    public int insertEflyerSent(EflyerSentModel systemProperty) {
        int result = 0;
        result = session.insert("insertUDWEflyerSent", systemProperty);
        return result;
    }

    public int updateEflyerSent(EflyerSentModel systemProperty) {
        int result = 0;
        result = session.update("updateUDWEflyerSent", systemProperty);
        return result;
    }

    public int deleteEflyerSent(EflyerSentModel systemProperty) {
        int result = 0;
        result = session.delete("deleteUDWEflyerSent", systemProperty);
        return result;
    }


    public List listEflyerSent() {
        List list = session.selectList("listUDWEflyerSent", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalUDWEflyerSent", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<EflyerSentModel> listAvailableEflyerSentMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUDWEflyerSentMap", parameterObject);
        else
            return session.selectList("listAllUDWEflyerSentMap", parameterObject);
    }

    public List<EFlyerSentTrackModel> listAvailableEflyerSentTrackMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUDWEflyerSentTrackMap", parameterObject);
        else
            return session.selectList("listAllUDWEflyerSentTrackMap", parameterObject);
    }
}
