package com.tenma.common.dao.supportticket.detail;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TicketDetailSupportModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Tue Apr 22 16:31:48 ICT 2014
 */
public class TicketSupportDetailDao extends Dao {
    public TicketSupportDetailDao(SqlSession session) {
        super(session);
    }

    public TicketDetailSupportModel getTicketSupportDetail(TicketDetailSupportModel model) {
        return (TicketDetailSupportModel) session.selectOne("getTicketSupportDetail", model);
    }

    public int insertTicketSupportDetail(TicketDetailSupportModel systemProperty) {
        int result = 0;
        result = session.insert("insertTicketSupportDetail", systemProperty);
        return result;
    }

    public int updateTicketSupportDetail(TicketDetailSupportModel systemProperty) {
        int result = 0;
        result = session.update("updateTicketSupportDetail", systemProperty);
        return result;
    }

    public int deleteTicketSupportDetail(TicketDetailSupportModel systemProperty) {
        int result = 0;
        result = session.delete("deleteTicketSupportDetail", systemProperty);
        return result;
    }


    public List listTicketSupportDetail() {
        List list = session.selectList("listTicketSupportDetail", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTicketSupportDetail", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TicketDetailSupportModel> listAvailableTicketSupportDetailMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listTicketSupportDetailMap", parameterObject);
        else
            return session.selectList("listAllTicketSupportDetailMap", parameterObject);
    }
}
