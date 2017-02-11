package com.tenma.sms.bean.smscommunityprofile;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.sms.dao.smscommunityprofile.SmsCommunityProfileDao;
import com.tenma.sms.model.SmsCommunityProfileModel;
import com.tenma.util.SMS_Constants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Fri Dec 13 15:28:34 WIT 2013
 */
public class SmsCommunityProfileHelper extends TenmaDaoHelper {
    public int insertNewSmsCommunityProfile(SmsCommunityProfileModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewSmsCommunityProfile(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewSmsCommunityProfile(SqlSession session, SmsCommunityProfileModel model) throws Exception {
        int result = 0;
        SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
        SmsCommunityProfileModel grpModel = new SmsCommunityProfileModel();
        grpModel.setCommunityId(model.getCommunityId());
        grpModel = dao.getSmsCommunityProfile(grpModel);
        if (grpModel != null)
            throw new Exception(SMS_Constants.ADD_ALREADY_EXIST);
        else {
            result = dao.insertSmsCommunityProfile(model);
            if (result == 0)
                throw new Exception(SMS_Constants.ADD_FAILED);
        }
        return result;
    }

    public int updateSmsCommunityProfile(SmsCommunityProfileModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            ret = dao.updateSmsCommunityProfile(model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            throw new Exception(SMS_Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    /**
     * model contain community id and transaction value
     *
     * @param model
     * @return
     * @throws Exception
     */
    public int deductCommunitySMSBalance(SmsCommunityProfileModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            ret = dao.deducSMSBalance(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(SMS_Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    /**
     * model contain community id and transaction value
     *
     * @param model
     * @return
     * @throws Exception
     */
    public int topUpCommunitySMSBalance(SmsCommunityProfileModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            ret = dao.topUpSMSBalance(model);
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
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            return dao.listAvailableSmsCommunityProfileMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public SmsCommunityProfileModel getSmsCommunityProfileDetail(SmsCommunityProfileModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return getSmsCommunityProfileDetail(session, model);
        } finally {
            session.close();
        }
    }


    public int getCountCurrentSMSBalance(SmsCommunityProfileModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            return dao.getCurrentSMSBalance(model);
        } finally {
            session.close();
        }
    }

    public SmsCommunityProfileModel getSmsCommunityProfileDetail(SqlSession session, SmsCommunityProfileModel model) {
        SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
        return dao.getSmsCommunityProfile(model);
    }

    public int deleteSmsCommunityProfile(SmsCommunityProfileModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SmsCommunityProfileDao dao = new SmsCommunityProfileDao(session);
            ret = dao.deleteSmsCommunityProfile(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(SMS_Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }
}