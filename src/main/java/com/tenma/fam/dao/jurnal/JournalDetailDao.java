package com.tenma.fam.dao.jurnal;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.JournalDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 5:35 PM
 */
public class JournalDetailDao extends Dao {
    public JournalDetailDao(SqlSession session) {
        super(session);
    }

    public JournalDetailModel getJournalDetailDetail(JournalDetailModel journalDetail) {
        return (JournalDetailModel) session.selectOne("getJournalDetailDetail", journalDetail);
    }

    public int insertJournalDetail(JournalDetailModel journalDetail) {
        return session.insert("insertJournalDetail", journalDetail);
    }

    public int updateJournalDetail(JournalDetailModel journalDetail) {

        return session.update("updateJournalDetail", journalDetail);
    }

    public int deleteJournalDetail(JournalDetailModel journalDetail) {
        return session.update("deleteJournalDetail", journalDetail);
    }

    public int deleteJournalDetailAll(JournalDetailModel journalDetail) {
        return session.update("deleteJournalDetailAll", journalDetail);
    }

    public int softDeleteJournalDetail(JournalDetailModel journalDetail) {
        return session.update("softDeleteJournalDetail", journalDetail);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalJournalDetail", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List getJournalDetailList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listJournalDetailMap", parameterObject);
        } else {
            list = session.selectList("listAllJournalDetailMap", parameterObject);
        }
        return list;
    }

    public List getLedgerReportGenerate(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        list = session.selectList("listLedgerReportGenerateMap", parameterObject);
        return list;
    }

    public List getHistoryLedgerReport(HashMap parameterObject) {
        return session.selectList("getHistoryLedgerReport", parameterObject);

    }

    public int countHistoryLedgerReport(HashMap map) {
        Integer count = (Integer) session.selectOne("countHistoryLedgerReport", map);
        return count == null ? 0 : count.intValue();
    }

    public int countJournalDetailReference(int journalDetailId) {
        Integer count = (Integer) session.selectOne("countJournalDetailReference", new Integer(journalDetailId));
        return count == null ? 0 : count.intValue();
    }

    public int countJournalDetailChild(int journalDetailId) {
        Integer count = (Integer) session.selectOne("countJournalDetailChild", new Integer(journalDetailId));
        return count == null ? 0 : count.intValue();
    }

    public int getJournalDetailSequence() {
        Integer sid = (Integer) session.selectOne("getJournalDetailSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public Double getCountAmount(String journalHeaderNumber) {
        Double inNext;
        Double sid = (Double) session.selectOne("countAmount", journalHeaderNumber);
        inNext = sid.doubleValue();

        return inNext;
    }

    public int countTotalBankList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalJournalDetailBank", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getJournalBankDetailList(HashMap parameterObject) {
        List list = null;
        list = session.selectList("listJournalDetailBankMap", parameterObject);
        return list;
    }


}




