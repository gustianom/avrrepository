package com.tenma.common.bean.smstype;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.smstype.SmsTypeDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.SmsTypeModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Thu May 08 12:14:04 ICT 2014
 */
public class SmsTypeHelper extends TenmaHelper {
    public int insertNewSmsType(SmsTypeModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            SmsTypeModel grpModel = new SmsTypeModel();
            grpModel.setCommunityStructure(model.getCommunityStructure());
            grpModel.setSmsId(model.getSmsId());
            grpModel.setSmsType(model.getSmsType());
            grpModel = dao.getSmsType(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertSmsType(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
                session.commit();
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateSmsType(SmsTypeModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            ret = dao.updateSmsType(model);
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
            SmsTypeDao dao = new SmsTypeDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            return dao.listAvailableSmsTypeMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public SmsTypeModel getSmsTypeDetail(SmsTypeModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            return dao.getSmsType(model);
        } finally {
            session.close();
        }
    }

    public int deleteSmsType(SmsTypeModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            ret = dao.deleteSmsType(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listSmsType() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsTypeDao dao = new SmsTypeDao(session);
            return dao.listSmsType();
        } finally {
            session.close();
        }
    }
}