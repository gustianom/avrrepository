package com.tenma.common.dao.session;

import com.tenma.auth.dao.Dao;
import com.tenma.common.model.SessionCounterModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 6/24/13
 * Time: 2:49 PM
 */
public class SessionCounterDao extends Dao {
    public SessionCounterDao(SqlSession session) {
        super(session);
    }

    public SessionCounterModel getSessionCounterDetail(SessionCounterModel model) {
        return (SessionCounterModel) session.selectOne("getsessionCounter", model);
    }

    public List getSessionCounterList(SessionCounterModel model) {
        return session.selectList("getsessionCounter", model);
    }

    public int insertSessionCounter(SessionCounterModel sessionCounter) {
        int result = 0;
        result = session.insert("insertsessionCounter", sessionCounter);
        return result;
    }

    public int remarkDeleteSessionCounter(SessionCounterModel sessionCounter) {
        int result = 0;
        result = session.delete("remarkDeleteSessionCounter", sessionCounter);
        return result;
    }


    public int physicalDeleteSessionCounter(SessionCounterModel sessionCounter) {
        int result = 0;
        result = session.delete("physicalDeleteSessionCounter", sessionCounter);
        return result;
    }

    public int physicalDeleteSessionCounterFromFile(SessionCounterModel sessionCounter) {
        int result = 0;
        result = session.delete("physicalDeleteSessionCounterFromFile", sessionCounter);
        return result;
    }


    public int countTotalList(HashMap parameterObject) {
        return 0;
    }
}
