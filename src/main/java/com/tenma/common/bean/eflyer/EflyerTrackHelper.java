package com.tenma.common.bean.eflyer;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.eflyer.EflyerTrackDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.EflyerTrackModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Apr 02 13:07:09 ICT 2015
 */
public class EflyerTrackHelper extends TenmaHelper {
    public int insertNewEflyerTrack(EflyerTrackModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            EflyerTrackModel grpModel = new EflyerTrackModel();
            grpModel.setSendId(model.getSendId());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getEflyerTrack(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertEflyerTrack(model);
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

    public int updateEflyerTrack(EflyerTrackModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            ret = dao.updateEflyerTrack(model);
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
            EflyerTrackDao dao = new EflyerTrackDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            return dao.listAvailableEflyerTrackMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public EflyerTrackModel getEflyerTrackDetail(EflyerTrackModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            return dao.getEflyerTrack(model);
        } finally {
            session.close();
        }
    }

    public int deleteEflyerTrack(EflyerTrackModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            ret = dao.deleteEflyerTrack(m);
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

    public List listEflyerTrack() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerTrackDao dao = new EflyerTrackDao(session);
            return dao.listEflyerTrack();
        } finally {
            session.close();
        }
    }
}