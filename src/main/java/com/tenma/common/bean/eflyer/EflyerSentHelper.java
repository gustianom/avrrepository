package com.tenma.common.bean.eflyer;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.eflyer.EflyerSentDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.EflyerSentModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Apr 02 13:05:01 ICT 2015
 */
public class EflyerSentHelper extends TenmaHelper {
    public int insertNewEflyerSent(EflyerSentModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            EflyerSentModel grpModel = new EflyerSentModel();
            grpModel.setSendId(model.getSendId());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getEflyerSent(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertEflyerSent(model);
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

    public int insertNewEflyerSent(EflyerSentModel model, SqlSession session) throws Exception {
        int result = 0;
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            EflyerSentModel grpModel = new EflyerSentModel();
            grpModel.setSendId(model.getSendId());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getEflyerSent(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertEflyerSent(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        }
        return result;
    }

    public int updateEflyerSent(EflyerSentModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            ret = dao.updateEflyerSent(model);
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
            EflyerSentDao dao = new EflyerSentDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            return dao.listAvailableEflyerSentMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListRendererTrack(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            return dao.listAvailableEflyerSentTrackMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public EflyerSentModel getEflyerSentDetail(EflyerSentModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            return dao.getEflyerSent(model);
        } finally {
            session.close();
        }
    }

    public int deleteEflyerSent(EflyerSentModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            ret = dao.deleteEflyerSent(m);
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

    public List listEflyerSent() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            return dao.listEflyerSent();
        } finally {
            session.close();
        }
    }

    public int updateEflyerSent(EflyerSentModel model, SqlSession session) throws Exception {
        int ret = 0;
        try {
            EflyerSentDao dao = new EflyerSentDao(session);
            ret = dao.updateEflyerSent(model);
        } catch (Exception e) {
            throw new Exception(Constants.UPDATE_FAILED);
        }
        return ret;
    }

    public int updateEflyerSent(List<EflyerSentModel> listFailedSent) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            for (EflyerSentModel ef : listFailedSent) {
                updateEflyerSent(ef, session);
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }
}