package com.tenma.common.dao.supportticket;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TicketSupportModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Tue Apr 22 16:23:58 ICT 2014
 */
public class TicketSupportDao extends Dao {
    public TicketSupportDao(SqlSession session) {
        super(session);
    }

    public TicketSupportModel getTicketSupport(TicketSupportModel model) {
        return (TicketSupportModel) session.selectOne("getTicketSupport", model);
    }

    public int insertTicketSupport(TicketSupportModel systemProperty) {
        int result = 0;
        result = session.insert("insertTicketSupport", systemProperty);
        return result;
    }

    public int updateTicketSupport(TicketSupportModel systemProperty) {
        int result = 0;
        result = session.update("updateTicketSupport", systemProperty);
        return result;
    }

    public int pinnedTicketSupportResponse(TicketSupportModel systemProperty) {
        int result = 0;
        result = session.update("pinnedTicketSupportResponse", systemProperty);
        return result;
    }

    public int deleteTicketSupport(TicketSupportModel systemProperty) {
        int result = 0;
        result = session.delete("deleteTicketSupport", systemProperty);
        return result;
    }


    public List listTicketSupport() {
        List list = session.selectList("listTicketSupport", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTicketSupport", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TicketSupportModel> listAvailableTicketSupportMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listTicketSupportMap", parameterObject);
        else
            return session.selectList("listAllTicketSupportMap", parameterObject);
    }
}
