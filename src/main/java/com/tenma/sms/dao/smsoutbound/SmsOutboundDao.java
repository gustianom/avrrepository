package com.tenma.sms.dao.smsoutbound;

import com.tenma.auth.dao.Dao;
import com.tenma.sms.model.SmsOutboundModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Dec 14 13:17:42 WIT 2013
 */
public class SmsOutboundDao extends Dao {
    public SmsOutboundDao(SqlSession session) {
        super(session);
    }

    public SmsOutboundModel getSmsOutbound(SmsOutboundModel model) {
        return (SmsOutboundModel) session.selectOne("getSmsOutbound", model);
    }

    public int insertSmsOutbound(SmsOutboundModel systemProperty) {
        int result = 0;
        result = session.insert("insertSmsOutbound", systemProperty);
        return result;
    }

    public int updateSmsOutbound(SmsOutboundModel systemProperty) {
        int result = 0;
        result = session.update("updateSmsOutbound", systemProperty);

        return result;
    }

    public int deleteSmsOutbound(SmsOutboundModel systemProperty) {
        int result = 0;
        result = session.delete("deleteSmsOutbound", systemProperty);
        return result;
    }


    public List listSmsOutbound() {
        List list = session.selectList("listSmsOutbound", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalSmsOutbound", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<SmsOutboundModel> listAvailableSmsOutboundMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listSmsOutboundMap", parameterObject);
        else
            return session.selectList("listAllSmsOutboundMap", parameterObject);
    }
}
