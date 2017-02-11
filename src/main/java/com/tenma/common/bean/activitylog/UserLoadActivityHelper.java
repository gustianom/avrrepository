package com.tenma.common.bean.activitylog;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.activitylog.UserLoadActivityDao;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.Constants;
import com.tenma.model.common.UserLoadActivityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Thu Feb 13 12:48:04 ICT 2014
 */
public class UserLoadActivityHelper extends TenmaDaoHelper implements CommonPagingHelper {

    public int insertNewUserLoadActivity(UserLoadActivityModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            result = dao.insertUserLoadActivity(model);
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

    public int updateUserLoad(UserLoadActivityModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            result = dao.updateUserLoadActivity(model);
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


    public List listLastUserActivity(String communityId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            HashMap mapList = new HashMap();
            mapList.put(Constants.COMMUNITY_ID, communityId);
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listUserLoadActivity(mapList);
        } finally {
            session.close();
        }
    }

    public List listTopMenuActivity(String communityId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            HashMap mapList = new HashMap();
            mapList.put(Constants.COMMUNITY_ID, communityId);
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listTopMenuActivity(mapList);
        } finally {
            session.close();
        }
    }

    public int insertNewUserLoadActivity(List<UserLoadActivityModel> ls) {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            int size = ls.size();
            for (int i = 0; i < size; i++) {
                UserLoadActivityModel model = ls.get(i);
                System.out.println(i + " " + model.getLogId() + " " + model.getMenuItemId() + "/" + model.getHitCount());
                result = result + dao.insertUserLoadActivity(model);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listAvailableUserLoadActivityMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListUserLoadActivityToExcel(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listUserLoadActivityToExcel(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getCountUserLoadActivity(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listCountUserLoadActivity(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getCountUserLoadActivityByDate(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listCountUserLoadActivityByDate(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getCountUserLoadActivityByUser(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listCountUserLoadActivityByUser(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public UserLoadActivityModel getUserLoadActivityDetail(UserLoadActivityModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.getUserLoadActivity(model);
        } finally {
            session.close();
        }
    }


    public List listUserLoadActivity() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserLoadActivityDao dao = new UserLoadActivityDao(session);
            return dao.listUserLoadActivity();
        } finally {
            session.close();
        }
    }
}