package com.tenma.fam.dao.jurnal;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.JournalHeaderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 2/25/13
 * Time: 5:14 PM
 */
public class JournalHeaderDao extends Dao {
    public JournalHeaderDao(SqlSession session) {
        super(session);
    }

    public JournalHeaderModel getJournalHeaderDetail(JournalHeaderModel journalHeader) {
        return (JournalHeaderModel) session.selectOne("getJournalHeaderDetail", journalHeader);
    }

    public int insertJournalHeader(JournalHeaderModel journalHeader) {
        return session.insert("insertJournalHeader", journalHeader);
    }

    public int updateJournalHeader(JournalHeaderModel journalHeader) {

        return session.update("updateJournalHeader", journalHeader);
    }

    public int updateJournalHeaderTemplate(JournalHeaderModel journalHeader) {

        return session.update("updateJournalHeader", journalHeader);
    }

    public int deleteJournalHeader(JournalHeaderModel journalHeader) {
        return session.update("deleteJournalHeader", journalHeader);
    }

    public int softDeleteJournalHeader(JournalHeaderModel journalHeader) {
        return session.update("softDeleteJournalHeader", journalHeader);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalJournalHeader", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getJournalHeaderList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listJournalHeaderMap", parameterObject);
        } else {
            list = session.selectList("listAllJournalHeaderMap", parameterObject);
        }
        return list;
    }

    public List getJournalHistoryHeaderList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listJournalHistoryHeaderMap", parameterObject);
        } else {
            list = session.selectList("listAllJournalHistoryHeaderMap", parameterObject);
        }
        return list;
    }

    public List getJournalHeaderListTemplate(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listJournalHeaderMapJoin", parameterObject);
        } else {
            list = session.selectList("listJournalHeaderMapJoin", parameterObject);
        }
        return list;
    }

    public int countJournalHeaderReference(int journalHeaderId) {
        Integer count = (Integer) session.selectOne("countJournalHeaderReference", new Integer(journalHeaderId));
        return count == null ? 0 : count.intValue();
    }

    public int countJournalHeaderChild(int journalHeaderId) {
        Integer count = (Integer) session.selectOne("countJournalHeaderChild", new Integer(journalHeaderId));
        return count == null ? 0 : count.intValue();
    }

    public int getJournalHeaderSequence() {
        Integer sid = (Integer) session.selectOne("getJournalHeaderSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }


}




