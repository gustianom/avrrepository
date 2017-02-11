package com.tenma.fam.bean.jurnal;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.dao.jurnal.JournalDetailDao;
import com.tenma.model.fam.JournalDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 5:36 PM
 */
public class JournalDetailHelper extends TenmaHelper {
    public int insertNewJournalDetail(JournalDetailModel model, Locale local) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.JOURNAL_DETAIL_SEQUENCE, true);
            String seqId = tools.addZeroPadWithYear(seq, 12, 4);
            model.setJournalDetailNumber(seqId);
            result = dao.insertJournalDetail(model);
            session.commit();
            //todo Depreciation Proses
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);

        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateJournalDetail(JournalDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            ret = dao.updateJournalDetail(model);
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
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public int countHistoryLedgerReport(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.countHistoryLedgerReport(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.getJournalDetailList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public List getListLedgerReportGenerate(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.getLedgerReportGenerate(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getHistoryLedgerReport(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.getHistoryLedgerReport(mapList);
        } finally {
            session.close();
        }
    }

    public JournalDetailModel getJournalDetailDetail(JournalDetailModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            JournalDetailDao dao = new JournalDetailDao(session);
            return dao.getJournalDetailDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteJournalDetail(JournalDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            JournalDetailDao dao = new JournalDetailDao(session);


            logger.info("model = " + model);
            ret = dao.deleteJournalDetail(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }
}




