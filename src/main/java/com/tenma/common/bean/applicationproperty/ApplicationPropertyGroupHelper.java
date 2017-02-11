/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.applicationproperty;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.applicationproperty.ApplicationPropertyGroupDao;
import com.tenma.common.model.ApplicationPropertyGroupModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:06 AM
 */
public class ApplicationPropertyGroupHelper extends TenmaDaoHelper {
    public int insertNewApplicationProperty(ApplicationPropertyGroupModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            ApplicationPropertyGroupModel grpModel = new ApplicationPropertyGroupModel();
            grpModel.setAppGrpCd(model.getAppGrpCd());
            grpModel = getApplicationPropertyDetail(model);
            if (grpModel != null)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertApplicationPropertyGroup(model);
                if (result == 0)
                    throw new Exception(TenmaConstants.ADD_FAILED);
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

    public int updateApplicationProperty(ApplicationPropertyGroupModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            ret = dao.updateApplicationPropertyGroup(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            return dao.listAvailableApplicationPropertyGroupMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List listApplicationPropertyGroupItems() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            return dao.listApplicationPropertyGroupItems();
        } finally {
            session.close();
        }
    }

    public ApplicationPropertyGroupModel getApplicationPropertyDetail(ApplicationPropertyGroupModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            return dao.getApplicationPropertyGroupDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteApplicationProperty(ApplicationPropertyGroupModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ApplicationPropertyGroupDao dao = new ApplicationPropertyGroupDao(session);
            ret = dao.deleteApplicationPropertyGroup(m);
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
}