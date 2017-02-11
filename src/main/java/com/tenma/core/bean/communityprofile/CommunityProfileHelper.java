package com.tenma.core.bean.communityprofile;


import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.communityprofile.CommunityProfileDao;
import com.tenma.model.core.CommunityProfileModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Oct 05 23:28:51 ICT 2015
 */
public class CommunityProfileHelper extends TenmaHelper {
    public int insertNewCommunityProfile(CommunityProfileModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            CommunityProfileModel grpModel = new CommunityProfileModel();
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = dao.getCommunityProfile(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertCommunityProfile(model);
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

    public int updateCommunityProfile(CommunityProfileModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            ret = dao.updateCommunityProfile(model);
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
            CommunityProfileDao dao = new CommunityProfileDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            return dao.listAvailableCommunityProfileMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CommunityProfileModel getCommunityProfileDetail(CommunityProfileModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            return dao.getCommunityProfile(model);
        } finally {
            session.close();
        }
    }

    public int deleteCommunityProfile(CommunityProfileModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            ret = dao.deleteCommunityProfile(m);
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

    public List listCommunityProfile() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityProfileDao dao = new CommunityProfileDao(session);
            return dao.listCommunityProfile();
        } finally {
            session.close();
        }
    }
}