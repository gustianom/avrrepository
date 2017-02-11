/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.applicationproperty;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.applicationproperty.ApplicationPropertyDao;
import com.tenma.common.model.ApplicationPropertyModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:46 PM
 */
public class ApplicationPropertyHelper extends TenmaDaoHelper {
    public int insertNewApplicationProperty(ApplicationPropertyModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewApplicationProperty(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewApplicationProperty(SqlSession session, ApplicationPropertyModel model) throws Exception {
        int result = 0;
        try {
            ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
            ApplicationPropertyModel grpModel = dao.getApplicationPropertyDetail(model);
            if (grpModel != null)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertApplicationProperty(model);
                if (result == 0)
                    throw new Exception(TenmaConstants.ADD_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }

    public int updateApplicationProperty(ApplicationPropertyModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateApplicationProperty(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateApplicationProperty(SqlSession session, ApplicationPropertyModel model) throws Exception {
        int ret = 0;
        try {
            ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
            ret = dao.updateApplicationProperty(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return getListRenderer(session, mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(SqlSession session, HashMap mapList, boolean navigated) {
        ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
        return dao.listAvailableApplicationPropertyMap(mapList, navigated);
    }

    public ApplicationPropertyModel getApplicationPropertyDetail(ApplicationPropertyModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return getApplicationPropertyDetail(session, model);
        } finally {
            session.close();
        }
    }

    public ApplicationPropertyModel getApplicationPropertyDetail(SqlSession session, ApplicationPropertyModel model) {
        ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
        return dao.getApplicationPropertyDetail(model);
    }

    public int deleteApplicationProperty(ApplicationPropertyModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
            ret = dao.deleteApplicationProperty(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }


    public List listApplicationPropertyItemsByGroup(String groupName) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyDao dao = new ApplicationPropertyDao(session);
            return dao.listApplicationPropertyItemsByGroup(groupName);
        } finally {
            session.close();
        }
    }
}
