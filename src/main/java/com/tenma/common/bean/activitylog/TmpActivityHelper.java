package com.tenma.common.bean.activitylog;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.activitylog.TmpActivityDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.TenmaMonitorModel;
import com.tenma.model.common.TmpActivityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Tue Feb 25 11:15:02 ICT 2014
 */
public class TmpActivityHelper extends TenmaHelper {

    public int insertNewTmpActivity(String userId, TenmaMonitorModel model) throws Exception {
        logger.info("insertNewTmpActivity" + model.getMenuId());
        TmpActivityModel m = new TmpActivityModel();
        m.setCommunityId(model.getCommunityId());
        m.setMenuid(model.getMenuId());
        m.setUserId(userId);
        m.setCounter(model.getCounter());
        m.setStartlog(model.getStartLog());
        m.setEndlog(model.getEndLog());
        m.setNextMenu(model.getNextMenu());
        return insertNewTmpActivity(m);
    }

    public int insertNewTmpActivity(TmpActivityModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TmpActivityDao dao = new TmpActivityDao(session);
            result = dao.insertTmpActivity(model);
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateTmpActivity(String userId, TenmaMonitorModel model) throws Exception {
        logger.info("TmpActivityHelper.updateTmpActivity");
        TmpActivityModel m = new TmpActivityModel();
        m.setCommunityId(model.getCommunityId());
        m.setMenuid(model.getMenuId());
        m.setUserId(userId);
        m.setCounter(model.getCounter());
        m.setStartlog(model.getStartLog());
        m.setEndlog(model.getEndLog());
        m.setNextMenu(model.getNextMenu());
        return updateTmpActivity(m);
    }

    public int updateTmpActivity(TmpActivityModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TmpActivityDao dao = new TmpActivityDao(session);
            ret = dao.updateTmpActivity(model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
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
            TmpActivityDao dao = new TmpActivityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        if (sqlSessionFactory == null) {
            logger.info("sqlSessionFactory NULL  ");
        } else {

            SqlSession session = sqlSessionFactory.openSession();
            try {
                TmpActivityDao dao = new TmpActivityDao(session);
                return dao.listAvailableTmpActivityMap(mapList, navigated);
            } finally {
                session.close();
            }
        }
        return new ArrayList();
    }

    public TmpActivityModel getTmpActivityDetail(TmpActivityModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TmpActivityDao dao = new TmpActivityDao(session);
            return dao.getTmpActivity(model);
        } finally {
            session.close();
        }
    }


    public int deleteTmpActivity(TmpActivityModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TmpActivityDao dao = new TmpActivityDao(session);
            TmpActivityModel mm = new TmpActivityModel();
            mm.setCommunityId(m.getCommunityId());
            mm.setUserId(m.getUserId());
            ret = dao.deleteTmpActivity(mm);
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

    public List listTmpActivity() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TmpActivityDao dao = new TmpActivityDao(session);
            return dao.listTmpActivity();
        } finally {
            session.close();
        }
    }
}