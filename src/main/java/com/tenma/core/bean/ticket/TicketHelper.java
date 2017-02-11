package com.tenma.core.bean.ticket;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.ticket.TicketDao;
import com.tenma.model.core.TicketModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Nov 05 11:12:55 WIB 2015
 */
public class TicketHelper extends TenmaHelper<TicketHelper> {

    public static TicketHelper use() {
        TicketHelper helper = new TicketHelper();
        return helper;
    }

    public int insertNewTicket(TicketModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketDao dao = new TicketDao(session);
            TicketModel grpModel = new TicketModel();
            grpModel.setTicketId(model.getTicketId());
            grpModel = dao.getTicket(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertTicket(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
                session.commit();
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateTicket(TicketModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketDao dao = new TicketDao(session);
            ret = dao.updateTicket(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketDao dao = new TicketDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketDao dao = new TicketDao(session);
            return dao.listAvailableTicketMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TicketModel getTicketDetail(TicketModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketDao dao = new TicketDao(session);
            return dao.getTicket(model);
        } finally {
            session.close();
        }
    }

    public int deleteTicket(TicketModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketDao dao = new TicketDao(session);
            ret = dao.deleteTicket(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listTicket() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketDao dao = new TicketDao(session);
            return dao.listTicket();
        } finally {
            session.close();
        }
    }
}