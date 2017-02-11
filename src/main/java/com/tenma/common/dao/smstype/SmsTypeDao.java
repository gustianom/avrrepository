package com.tenma.common.dao.smstype;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.SmsTypeModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Thu May 08 12:14:04 ICT 2014
 */
public class SmsTypeDao extends Dao {
    public SmsTypeDao(SqlSession session) {
        super(session);
    }

    public SmsTypeModel getSmsType(SmsTypeModel model) {
        return (SmsTypeModel) session.selectOne("getSmsType", model);
    }

    public int insertSmsType(SmsTypeModel systemProperty) {
        int result = 0;
        result = session.insert("insertSmsType", systemProperty);
        return result;
    }

    public int updateSmsType(SmsTypeModel systemProperty) {
        int result = 0;
        result = session.update("updateSmsType", systemProperty);
        return result;
    }

    public int deleteSmsType(SmsTypeModel systemProperty) {
        int result = 0;
        result = session.delete("deleteSmsType", systemProperty);
        return result;
    }


    public List listSmsType() {
        List list = session.selectList("listSmsType", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalSmsType", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<SmsTypeModel> listAvailableSmsTypeMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listSmsTypeMap", parameterObject);
        else
            return session.selectList("listAllSmsTypeMap", parameterObject);
    }
}
