package com.tenma.common.dao.eflyer;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.EflyerTrackModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Apr 02 13:07:09 ICT 2015
 */
public class EflyerTrackDao extends Dao {
    public EflyerTrackDao(SqlSession session) {
        super(session);
    }

    public EflyerTrackModel getEflyerTrack(EflyerTrackModel model) {
        return (EflyerTrackModel) session.selectOne("getUDWEflyerTrack", model);
    }

    public int insertEflyerTrack(EflyerTrackModel systemProperty) {
        int result = 0;
        result = session.insert("insertUDWEflyerTrack", systemProperty);
        return result;
    }

    public int updateEflyerTrack(EflyerTrackModel systemProperty) {
        int result = 0;
        result = session.update("updateUDWEflyerTrack", systemProperty);
        return result;
    }

    public int deleteEflyerTrack(EflyerTrackModel systemProperty) {
        int result = 0;
        result = session.delete("deleteUDWEflyerTrack", systemProperty);
        return result;
    }


    public List listEflyerTrack() {
        List list = session.selectList("listUDWEflyerTrack", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalUDWEflyerTrack", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<EflyerTrackModel> listAvailableEflyerTrackMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUDWEflyerTrackMap", parameterObject);
        else
            return session.selectList("listAllUDWEflyerTrackMap", parameterObject);
    }
}
