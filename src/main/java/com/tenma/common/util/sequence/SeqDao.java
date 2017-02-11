package com.tenma.common.util.sequence;

import com.tenma.auth.dao.Dao;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jul 10 15:06:33 ICT 2013
 */
public class SeqDao extends Dao {
    public SeqDao(SqlSession session) {
        super(session);
    }

    public int insertSeq(SeqModel systemProperty) {
        int result = 0;
        result = session.insert("insertSeq", systemProperty);
        return result;
    }

    public int updateSeq(SeqModel systemProperty) {
        int result = 0;
        result = session.update("updateSeq", systemProperty);
        session.commit();
        return result;
    }


    public int getLastCounter(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("getLastSeq", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    @Override
    public int countTotalList(HashMap map) {
        return 0;
    }
}
