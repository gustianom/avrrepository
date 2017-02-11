package com.tenma.sms.bean.smsundelivered;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.sms.SMSUndeliveredModel;
import com.tenma.sms.dao.smsundelivered.SMSUndeliveredDao;
import com.tenma.util.SMS_Constants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Aug 13 11:01:54 WIB 2015
 */
public class SMSUndeliveredHelper extends TenmaDaoHelper {
    public int insertNewUndeliveredSms(SMSUndeliveredModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SMSUndeliveredDao dao = new SMSUndeliveredDao(session);
            result = dao.insertUndeliveredSms(model);
            if (result == 0)
                throw new Exception(SMS_Constants.ADD_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SMSUndeliveredDao dao = new SMSUndeliveredDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SMSUndeliveredDao dao = new SMSUndeliveredDao(session);
            return dao.listAvailableUndeliveredSmsMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public SMSUndeliveredModel getUndeliveredSmsDetail(SMSUndeliveredModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SMSUndeliveredDao dao = new SMSUndeliveredDao(session);
            return dao.getUndeliveredSms(model);
        } finally {
            session.close();
        }
    }

    public int deleteUndeliveredSms(SMSUndeliveredModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SMSUndeliveredDao dao = new SMSUndeliveredDao(session);
            ret = dao.deleteUndeliveredSms(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(SMS_Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(SMS_Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }
}