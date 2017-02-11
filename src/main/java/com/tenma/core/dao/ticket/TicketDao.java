package com.tenma.core.dao.ticket;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.TicketModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Nov 05 11:12:54 WIB 2015
 */
public class TicketDao extends Dao {
    public TicketDao(SqlSession session) {
        super(session);
    }

    public TicketModel getTicket(TicketModel model) {
        return (TicketModel) session.selectOne("getCORETicket", model);
    }

    public TicketModel getTicketForUpdate(TicketModel model) {
        return (TicketModel) session.selectOne("getCORETicketForUpdate", model);
    }

    public int insertTicket(TicketModel systemProperty) {
        int result = 0;
        result = session.insert("insertCORETicket", systemProperty);
        return result;
    }

    public int updateTicket(TicketModel systemProperty) {
        int result = 0;
        result = session.update("updateCORETicket", systemProperty);
        return result;
    }

    public int deleteTicket(TicketModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCORETicket", systemProperty);
        return result;
    }


    public List listTicket() {
        List list = session.selectList("listCORETicket", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCORETicket", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<TicketModel> listAvailableTicketMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCORETicketMap", parameterObject);
        else
            return session.selectList("listAllCORETicketMap", parameterObject);
    }
}
