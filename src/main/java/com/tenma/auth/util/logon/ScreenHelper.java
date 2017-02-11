package com.tenma.auth.util.logon;


import com.tenma.auth.dao.screen.ScreenDao;
import com.tenma.auth.model.ScreenModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.error.ErrorInfo;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Wed Jun 25 16:22:23 WIB 2014
 */
public class ScreenHelper extends TenmaHelper<ScreenHelper> {

    public static ScreenHelper use() {
        ScreenHelper helper = new ScreenHelper();
        return helper;
    }

    public int insertNewScreen(ScreenModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ScreenDao dao = new ScreenDao(session);
            ScreenModel grpModel = new ScreenModel();
            grpModel.setScreenName(model.getScreenName());
            grpModel.setScreenType(model.getScreenType());
            grpModel = dao.getScreen(grpModel);
            if (grpModel != null)
                throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertScreen(model);
                if (result == 0)
                    throw new Exception(AuthConstants.ADD_FAILED);
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

    public int updateScreen(ScreenModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ScreenDao dao = new ScreenDao(session);
            ret = dao.updateScreen(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ScreenDao dao = new ScreenDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<ScreenModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ScreenDao dao = new ScreenDao(session);
            return dao.listAvailableScreenMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public ScreenModel getScreenDetail(ScreenModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ScreenDao dao = new ScreenDao(session);
            return dao.getScreen(model);
        } finally {
            session.close();
        }
    }

    public int deleteScreen(ScreenModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ScreenDao dao = new ScreenDao(session);
            ret = dao.deleteScreen(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(AuthConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(AuthConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listScreen() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ScreenDao dao = new ScreenDao(session);
            return dao.listScreen();
        } finally {
            session.close();
        }
    }
}