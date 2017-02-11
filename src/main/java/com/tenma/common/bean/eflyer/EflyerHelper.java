package com.tenma.common.bean.eflyer;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.eflyer.EflyerDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.EflyerModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Tue Mar 17 15:58:47 ICT 2015
 */
public class EflyerHelper extends TenmaHelper {
    public int insertNewEflyer(EflyerModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerDao dao = new EflyerDao(session);
            EflyerModel grpModel = new EflyerModel();
            grpModel.setEflyerId(model.getEflyerId());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getEflyer(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertEflyer(model);
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

    public int updateEflyer(EflyerModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerDao dao = new EflyerDao(session);
            ret = dao.updateEflyer(model);
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
            EflyerDao dao = new EflyerDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerDao dao = new EflyerDao(session);
            return dao.listAvailableEflyerMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public EflyerModel getEflyerDetail(EflyerModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerDao dao = new EflyerDao(session);
            return dao.getEflyer(model);
        } finally {
            session.close();
        }
    }

    public int deleteEflyer(EflyerModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerDao dao = new EflyerDao(session);
            ret = dao.deleteEflyer(m);
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

    public List listEflyer() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerDao dao = new EflyerDao(session);
            return dao.listEflyer();
        } finally {
            session.close();
        }
    }
}