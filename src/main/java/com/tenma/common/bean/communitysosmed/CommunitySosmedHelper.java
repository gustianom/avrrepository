package com.tenma.common.bean.communitysosmed;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.communitysosmed.CommunitySosmedDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.CommunitySosmedModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Dec 23 21:11:11 WIT 2013
 */
public class CommunitySosmedHelper extends TenmaHelper {
    public int insertNewCommunitySosmed(CommunitySosmedModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            CommunitySosmedModel grpModel = new CommunitySosmedModel();
            grpModel.setSosmedName(model.getSosmedName());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel.setSosmedType(model.getSosmedType());
            grpModel = dao.getCommunitySosmed(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertCommunitySosmed(model);
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

    public int updateCommunitySosmed(CommunitySosmedModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            ret = dao.updateCommunitySosmed(model);
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
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            return dao.listAvailableCommunitySosmedMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CommunitySosmedModel getCommunitySosmedDetail(CommunitySosmedModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            return dao.getCommunitySosmed(model);
        } finally {
            session.close();
        }
    }

    public int deleteCommunitySosmed(CommunitySosmedModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            ret = dao.deleteCommunitySosmed(m);
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

    public List listCommunitySosmed() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunitySosmedDao dao = new CommunitySosmedDao(session);
            return dao.listCommunitySosmed();
        } finally {
            session.close();
        }
    }
}