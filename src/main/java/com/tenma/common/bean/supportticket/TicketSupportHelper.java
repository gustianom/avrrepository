package com.tenma.common.bean.supportticket;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.supportticket.TicketSupportDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
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
public class TicketSupportHelper extends TenmaHelper {
    public int insertNewTicketSupport(TicketSupportModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            TicketSupportModel grpModel = new TicketSupportModel();
            grpModel.setCommunityId(model.getCommunityId());
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(model.getCommunityId(), SEQUENCE_Constants.TICKET_SUPPORT_SEQUENCE, false);
            model.setTicketId(seq);
            grpModel.setTicketId(seq);
            grpModel = dao.getTicketSupport(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertTicketSupport(model);
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

    public int updateTicketSupport(TicketSupportModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            ret = dao.updateTicketSupport(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int pinnedTicketSupportResponse(TicketSupportModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            ret = dao.pinnedTicketSupportResponse(model);
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
            TicketSupportDao dao = new TicketSupportDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            return dao.listAvailableTicketSupportMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TicketSupportModel getTicketSupportDetail(TicketSupportModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            return dao.getTicketSupport(model);
        } finally {
            session.close();
        }
    }

    public int deleteTicketSupport(TicketSupportModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            ret = dao.deleteTicketSupport(m);
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

    public List listTicketSupport() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDao dao = new TicketSupportDao(session);
            return dao.listTicketSupport();
        } finally {
            session.close();
        }
    }
}