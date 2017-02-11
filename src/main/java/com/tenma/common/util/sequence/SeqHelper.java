package com.tenma.common.util.sequence;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jul 10 15:06:33 ICT 2013
 */
public class SeqHelper extends TenmaDaoHelper {

    public int insertNewSeq(SeqModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SeqDao dao = new SeqDao(session);
            result = dao.insertSeq(model);
            if (result == 0)
                throw new Exception(TenmaConstants.ADD_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateSeq(SeqModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SeqDao dao = new SeqDao(session);
            ret = dao.updateSeq(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }


    public int getLastSeq(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SeqDao dao = new SeqDao(session);
            return dao.getLastCounter(mapList);
        } finally {
            session.close();
        }
    }


    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        return 0;
    }

    @Override
    public List getListRenderer(HashMap mapList, boolean navigated) throws Exception {
        return null;
    }
}