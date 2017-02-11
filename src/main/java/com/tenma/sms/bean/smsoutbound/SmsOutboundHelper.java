package com.tenma.sms.bean.smsoutbound;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.sms.dao.smsoutbound.SmsOutboundDao;
import com.tenma.sms.model.SmsOutboundModel;
import com.tenma.util.SMS_Constants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Sat Dec 14 13:17:42 WIT 2013
 */
public class SmsOutboundHelper extends TenmaDaoHelper {
    public int insertNewSmsOutbound(SmsOutboundModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewSmsOutbound(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewSmsOutbound(SqlSession session, SmsOutboundModel model) throws Exception {
        int result = 0;
        SmsOutboundDao dao = new SmsOutboundDao(session);
        result = dao.insertSmsOutbound(model);
        if (result == 0)
            throw new Exception(SMS_Constants.ADD_FAILED);
        return result;
    }

    public int updateSmsOutbound(SmsOutboundModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            ret = dao.updateSmsOutbound(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(SMS_Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            return dao.listAvailableSmsOutboundMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public SmsOutboundModel getSmsOutboundDetail(SmsOutboundModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            return dao.getSmsOutbound(model);
        } finally {
            session.close();
        }
    }

    public int deleteSmsOutbound(SmsOutboundModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            ret = dao.deleteSmsOutbound(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(SMS_Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listSmsOutbound() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsOutboundDao dao = new SmsOutboundDao(session);
            return dao.listSmsOutbound();
        } finally {
            session.close();
        }
    }
}