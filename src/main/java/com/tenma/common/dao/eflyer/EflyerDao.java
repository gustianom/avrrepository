package com.tenma.common.dao.eflyer;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.EflyerModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Tue Mar 17 15:58:47 ICT 2015
 */
public class EflyerDao extends Dao {
    public EflyerDao(SqlSession session) {
        super(session);
    }

    public EflyerModel getEflyer(EflyerModel model) {
        return (EflyerModel) session.selectOne("getUDWEflyer", model);
    }

    public int insertEflyer(EflyerModel systemProperty) {
        int result = 0;
        result = session.insert("insertUDWEflyer", systemProperty);
        return result;
    }

    public int updateEflyer(EflyerModel systemProperty) {
        int result = 0;
        result = session.update("updateUDWEflyer", systemProperty);
        return result;
    }

    public int deleteEflyer(EflyerModel systemProperty) {
        int result = 0;
        result = session.delete("deleteUDWEflyer", systemProperty);
        return result;
    }


    public List listEflyer() {
        List list = session.selectList("listUDWEflyer", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalUDWEflyer", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<EflyerModel> listAvailableEflyerMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUDWEflyerMap", parameterObject);
        else
            return session.selectList("listAllUDWEflyerMap", parameterObject);
    }

    public List listAllEflyerWithoutJson(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listEflyerWithoutJson", parameterObject);
        else
            return session.selectList("listAllEflyerWithoutJson", parameterObject);
    }
}
