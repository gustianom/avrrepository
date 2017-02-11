/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */

package com.tenma.fam.bean.template;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.dao.template.TransactionTemplaterDao;
import com.tenma.fam.dao.template.TransactionTemplaterDetailDao;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import com.tenma.model.fam.TransactionTemplateModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TransactionTemplateHelper extends TenmaHelper implements CommonPagingHelper {
    public int insertNewTemplateHeader(TransactionTemplateModel model, List listTemplateDetail) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            TransactionTemplaterDetailDao jdao = new TransactionTemplaterDetailDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.TEMPLATE_HEADER_SEQUENCE, false);
            String seqId = tools.addZeroPadWithYear(seq, 14, 2);
            model.setTemplateId(seqId);
            result = dao.insertTemplateHeader(model);

            for (int a = 0; a < listTemplateDetail.size(); a++) {

                TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) listTemplateDetail.get(a);
                m.setTemplateId(model.getTemplateId());
                int seqDetail = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.TEMPLATE_DETAIL_SEQUENCE, false);
                String seqIdDetail = tools.addZeroPadWithYear(seqDetail, 14, 2);
                m.setTemplateDetailId(seqIdDetail);
                m.setCommunityId(model.getCommunityId());
                m.setTemplateId(model.getTemplateId());
                jdao.insertTemplateDetail(m);
            }
            session.commit();
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);

        } catch (Exception e) {
            logger.info("e = " + e);
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateTemplateHeader(TransactionTemplateModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            ret = dao.updateTemplateHeader(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateTemplateHeader(TransactionTemplateModel model, List updateList, List insertList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            TransactionTemplaterDetailDao jdao = new TransactionTemplaterDetailDao(session);
            ret = dao.updateTemplateHeader(model);
            for (int a = 0; a < updateList.size(); a++) {
                TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) updateList.get(a);
                jdao.deleteTemplateDetail(m);
            }
            for (int a = 0; a < insertList.size(); a++) {
                TransactionTemplateDetailModel m = (TransactionTemplateDetailModel) insertList.get(a);
                m.setTemplateId(model.getTemplateId());
                int seqDetail = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.TEMPLATE_DETAIL_SEQUENCE, false);
                String seqIdDetail = tools.addZeroPadWithYear(seqDetail, 14, 2);
                m.setTemplateDetailId(seqIdDetail);
                m.setCommunityId(model.getCommunityId());
                jdao.insertTemplateDetail(m);
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            return dao.getTemplateHeaderList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TransactionTemplateModel getTemplateHeaderDetail(TransactionTemplateModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            return dao.getTemplateHeaderDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteTemplateHeader(TransactionTemplateModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);


            logger.info("model = " + model);
            ret = dao.deleteTemplateHeader(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public Integer getSummaryRow() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDao dao = new TransactionTemplaterDao(session);
            return dao.getTemplateHeaderSequence();
        } finally {
            session.close();
        }


    }

    public Double getCountAmount(String journalHeaderNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);
            return dao.getCountAmount(journalHeaderNumber);
        } finally {
            session.close();
        }
    }
}





