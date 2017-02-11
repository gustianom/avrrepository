package com.tenma.common.bean.supportticket.detail;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.supportticket.detail.TicketSupportDetailDao;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
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
public class TicketSupportDetailHelper extends TenmaHelper implements CommonPagingHelper {
    public int insertNewTicketSupportDetail(TicketDetailSupportModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            TicketDetailSupportModel grpModel = new TicketDetailSupportModel();
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(model.getCommunityId(), SEQUENCE_Constants.TICKET_SUPPORT_RESPONSE_SEQUENCE, false);
            model.setResponseId(seq);
            grpModel.setResponseId(seq);
            grpModel.setTicketId(model.getTicketId());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getTicketSupportDetail(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertTicketSupportDetail(model);
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

    public int updateTicketSupportDetail(TicketDetailSupportModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            ret = dao.updateTicketSupportDetail(model);
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
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            return dao.listAvailableTicketSupportDetailMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TicketDetailSupportModel getTicketSupportDetailDetail(TicketDetailSupportModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            return dao.getTicketSupportDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteTicketSupportDetail(TicketDetailSupportModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            ret = dao.deleteTicketSupportDetail(m);
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

    public List listTicketSupportDetail() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TicketSupportDetailDao dao = new TicketSupportDetailDao(session);
            return dao.listTicketSupportDetail();
        } finally {
            session.close();
        }
    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }
}