package com.tenma.fam.bean.jurnal;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.dao.jurnal.JournalDetailDao;
import com.tenma.fam.dao.jurnal.JournalHeaderDao;
import com.tenma.fam.dao.pettycash.PettycashCycleDao;
import com.tenma.model.fam.JournalDetailModel;
import com.tenma.model.fam.JournalHeaderModel;
import com.tenma.model.fam.PettycashCycleModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 5:15 PM
 */
public class JournalHeaderHelper extends TenmaHelper {

    public int insertNewJournalHeader(JournalHeaderModel model, List listJournalDetail) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            JournalDetailDao jdao = new JournalDetailDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(model.getCommunityId(), SEQUENCE_Constants.JOURNAL_HEADER_SEQUENCE, true);
            String seqId = tools.addZeroPadWithYear(seq, 12, 4);
            model.setJournalHeaderNumber(seqId);
            result = dao.insertJournalHeader(model);

            for (int a = 0; a < listJournalDetail.size(); a++) {
                JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                m.setCommunityId(model.getCommunityId());
                m.setUpdatedBy(model.getUpdatedBy());
                m.setUpdatedFrom(model.getUpdatedFrom());
                String seqIdDetail = m.getJournalHeaderNumber() + m.getOrderNumber();
                m.setJournalDetailNumber(seqIdDetail);
                jdao.insertJournalDetail(m);
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

    public int insertNewJournalHeader(JournalHeaderModel model, List listJournalDetail, SqlSession session, Locale local) throws Exception {
        int result = 0;
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            JournalDetailDao jdao = new JournalDetailDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_HEADER_SEQUENCE, true);
            String seqId = tools.addZeroPadWithYear(seq, 12, 4);
            model.setJournalHeaderNumber(seqId);
            result = dao.insertJournalHeader(model);

            for (int a = 0; a < listJournalDetail.size(); a++) {

                JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                m.setCommunityId(model.getCommunityId());
                m.setCreatedBy(model.getCreatedBy());
                m.setCommunityId(model.getCommunityId());
                m.setCreatedFrom(model.getCreatedFrom());
                m.setUpdatedBy(model.getUpdatedBy());
                m.setUpdatedFrom(model.getUpdatedFrom());
                int seqDetail = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_DETAIL_SEQUENCE, true);
                String seqIdDetail = tools.addZeroPadWithYear(seqDetail, 12, 4);
                m.setJournalDetailNumber(seqIdDetail);
                jdao.insertJournalDetail(m);
            }
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public int updateJournalHeader(JournalHeaderModel model, List listJournalDetail, List olist) throws Exception {
        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        int ret = 0;
        int rs = 0;
        JournalHeaderDao dao = new JournalHeaderDao(session1);
        JournalDetailDao jdao1 = new JournalDetailDao(session1);
        JournalDetailDao jdao2 = new JournalDetailDao(session2);

        try {

            try {
                ret = dao.updateJournalHeader(model);
                JournalDetailModel dm = new JournalDetailModel();
                dm.setCommunityId(model.getCommunityId());

                dm.setJournalHeaderNumber(model.getJournalHeaderNumber());
                rs = jdao2.deleteJournalDetail(dm);
                session2.commit();
            } catch (Exception e) {
                e.printStackTrace();
                rs = -1;
            }

            if (rs > 0) {

                for (int a = 0; a < listJournalDetail.size(); a++) {
                    JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                    if (!m.isDeleted()) {
                        m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                        m.setCommunityId(model.getCommunityId());
                        m.setUpdatedBy(model.getUpdatedBy());
                        m.setUpdatedFrom(model.getUpdatedFrom());
                        String seqIdDetail = m.getJournalHeaderNumber() + m.getOrderNumber();
                        m.setJournalDetailNumber(seqIdDetail);
                        ret = ret + jdao1.insertJournalDetail(m);
                    }
                }
            }

            session1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session1.rollback();
            if (rs > 0) {
                for (int a = 0; a < olist.size(); a++) {
                    JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                    jdao2.insertJournalDetail(m);
                }
            }
            session2.commit();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session1.close();
        }
        return ret;
    }

    public int updateJournalHeaderTemplate(JournalHeaderModel model, List listJournalDetail, List olist) throws Exception {
        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        int ret = 0;
        int rs = 0;
        JournalHeaderDao dao = new JournalHeaderDao(session1);
        JournalDetailDao jdao1 = new JournalDetailDao(session1);
        JournalDetailDao jdao2 = new JournalDetailDao(session2);

        try {

            try {
                ret = dao.updateJournalHeaderTemplate(model);
                JournalDetailModel dm = new JournalDetailModel();
                dm.setCommunityId(model.getCommunityId());

                dm.setJournalHeaderNumber(model.getJournalHeaderNumber());
                rs = jdao2.deleteJournalDetail(dm);
                session2.commit();
            } catch (Exception e) {
                e.printStackTrace();
                rs = -1;
            }

            if (rs > 0) {

                for (int a = 0; a < listJournalDetail.size(); a++) {
                    JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                    m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                    m.setCommunityId(model.getCommunityId());
                    m.setUpdatedBy(model.getUpdatedBy());
                    m.setUpdatedFrom(model.getUpdatedFrom());
                    String seqIdDetail = m.getJournalHeaderNumber() + m.getOrderNumber();
                    m.setJournalDetailNumber(seqIdDetail);
                    ret = ret + jdao1.insertJournalDetail(m);

                }
            }
//
            session1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session1.rollback();
            if (rs > 0) {
                for (int a = 0; a < olist.size(); a++) {
                    JournalDetailModel m = (JournalDetailModel) listJournalDetail.get(a);
                    jdao2.insertJournalDetail(m);
                }
            }
            session2.commit();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session1.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.getJournalHeaderList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListHistoryRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.getJournalHistoryHeaderList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListRendererTemplate(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.getJournalHeaderListTemplate(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public JournalHeaderModel getJournalHeaderDetail(JournalHeaderModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.getJournalHeaderDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteJournalHeader(JournalHeaderModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);


            logger.info("model = " + model);
            ret = dao.deleteJournalHeader(model);
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
            JournalHeaderDao dao = new JournalHeaderDao(session);
            return dao.getJournalHeaderSequence();
        } finally {
            session.close();
        }


    }

    public Double getCountAmount(String journalHeaderNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.getCountAmount(journalHeaderNumber);
        } finally {
            session.close();
        }


    }

    public int insertPettyCashJournal(JournalHeaderModel model, HashMap accountDetail, HashMap detail, PettycashCycleModel petModel) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        logger.info("accountDetail.size() = " + accountDetail.size());
        logger.info("detail.size() = " + detail.size());
        try {
            JournalHeaderDao dao = new JournalHeaderDao(session);
            JournalDetailDao jdao = new JournalDetailDao(session);
            PettycashCycleDao petDao = new PettycashCycleDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_HEADER_SEQUENCE, true);
            String seqId = tools.addZeroPadWithYear(seq, 12, 4);
            model.setJournalHeaderNumber(seqId);
            result = dao.insertJournalHeader(model);
            for (int a = 0; a < accountDetail.size(); a++) {
                logger.info("a = " + a);
                JournalDetailModel m = (JournalDetailModel) accountDetail.get(a);
                logger.info("m = " + m);
                m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                m.setCommunityId(model.getCommunityId());
                m.setCreatedBy(model.getCreatedBy());
                m.setCreatedFrom(model.getCreatedFrom());
                m.setUpdatedBy(model.getUpdatedBy());
                m.setUpdatedFrom(model.getUpdatedFrom());
                int seqDetail = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_DETAIL_SEQUENCE, true);
                String seqIdDetail = tools.addZeroPadWithYear(seqDetail, 12, 4);
                m.setJournalDetailNumber(seqIdDetail);
                logger.info("m = " + m);
                result = jdao.insertJournalDetail(m);
            }

            for (int a = 0; a < detail.size(); a++) {
                JournalDetailModel m = (JournalDetailModel) detail.get(a);
                m.setJournalHeaderNumber(model.getJournalHeaderNumber());
                m.setCreatedBy(model.getCreatedBy());
                m.setCreatedFrom(model.getCreatedFrom());
                m.setUpdatedBy(model.getUpdatedBy());
                m.setCommunityId(model.getCommunityId());
                m.setUpdatedFrom(model.getUpdatedFrom());
                int seqDetail = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_DETAIL_SEQUENCE, true);
                String seqIdDetail = tools.addZeroPadWithYear(seqDetail, 12, 4);
                m.setJournalDetailNumber(seqIdDetail);
                result = jdao.insertJournalDetail(m);
            }
            petModel.setUpdatedBy(model.getUpdatedBy());
            petModel.setUpdatedFrom(model.getUpdatedFrom());
            petDao.updatePettycashCycle(petModel);
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
}




