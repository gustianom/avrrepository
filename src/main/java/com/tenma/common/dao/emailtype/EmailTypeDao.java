package com.tenma.common.dao.emailtype;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.EmailTypeModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 5/27/13
 * Time: 9:38 AM
 */
public class EmailTypeDao extends Dao {
    public EmailTypeDao(SqlSession session) {
        super(session);
    }

    @Override
    public int countTotalList(HashMap map) {
        Integer count = (Integer) session.selectOne("getEmailTypeCount", map);
        return count == null ? 0 : count.intValue();
    }

    public EmailTypeModel getEmailTypeDetail(EmailTypeModel menu) {
        return (EmailTypeModel) session.selectOne("getEmailTypeObject", menu);
    }

    public int insertEmailType(EmailTypeModel menu) {
        return session.insert("insertEmailType", menu);
    }

    public int updateEmailType(EmailTypeModel menu) {

        return session.update("updateEmailType", menu);
    }

    public int deleteEmailType(EmailTypeModel menu) {
        return session.delete("deleteEmailType", menu);
    }

    public List getEmailTypeList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("getEmailTypeList", parameterObject);
        } else {
            list = session.selectList("getAllEmailTypeList", parameterObject);
        }
        return list;
    }

    public int getEmailTypeSequence() {
        Integer sid = (Integer) session.selectOne("getEmailTypeSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }
}

