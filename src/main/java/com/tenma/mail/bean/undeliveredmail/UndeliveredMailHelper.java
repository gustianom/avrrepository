package com.tenma.mail.bean.undeliveredmail;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.mail.dao.undeliveredmail.UndeliveredMailDao;
import com.tenma.model.email.UndeliveredMailModel;
import com.tenma.util.SMS_Constants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Mar 05 10:41:17 WIB 2015
 */
public class UndeliveredMailHelper extends TenmaDaoHelper {
    public int insertNewUndeliveredMail(UndeliveredMailModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            UndeliveredMailModel grpModel = new UndeliveredMailModel();
            result = dao.insertUndeliveredMail(model);
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

    public int updateUndeliveredMail(UndeliveredMailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            ret = dao.updateUndeliveredMail(model);
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
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<UndeliveredMailModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            return dao.listAvailableUndeliveredMailMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public UndeliveredMailModel getUndeliveredMailDetail(UndeliveredMailModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            return dao.getUndeliveredMail(model);
        } finally {
            session.close();
        }
    }

    public int deleteUndeliveredMail(UndeliveredMailModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            UndeliveredMailDao dao = new UndeliveredMailDao(session);
            ret = dao.deleteUndeliveredMail(m);
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