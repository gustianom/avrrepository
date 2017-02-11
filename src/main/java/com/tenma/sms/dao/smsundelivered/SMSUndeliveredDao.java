package com.tenma.sms.dao.smsundelivered;

import com.tenma.auth.dao.Dao;
import com.tenma.model.sms.SMSUndeliveredModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Aug 13 11:01:54 WIB 2015
 */
public class SMSUndeliveredDao extends Dao {
    public SMSUndeliveredDao(SqlSession session) {
        super(session);
    }

    public SMSUndeliveredModel getUndeliveredSms(SMSUndeliveredModel model) {
        return (SMSUndeliveredModel) session.selectOne("getCOREUndeliveredSms", model);
    }

    public int insertUndeliveredSms(SMSUndeliveredModel systemProperty) {
        int result = 0;
        result = session.insert("insertCOREUndeliveredSms", systemProperty);
        return result;
    }

    public int deleteUndeliveredSms(SMSUndeliveredModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCOREUndeliveredSms", systemProperty);
        return result;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCOREUndeliveredSms", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<SMSUndeliveredModel> listAvailableUndeliveredSmsMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCOREUndeliveredSmsMap", parameterObject);
        else
            return session.selectList("listAllCOREUndeliveredSmsMap", parameterObject);
    }
}
