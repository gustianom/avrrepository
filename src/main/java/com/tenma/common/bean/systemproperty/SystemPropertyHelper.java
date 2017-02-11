/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.systemproperty;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.systemproperty.SystemPropertyDao;
import com.tenma.common.model.SystemPropertyModel;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 3:56 PM
 */
public class SystemPropertyHelper extends TenmaHelper implements CommonPagingHelper {
    public int insertNewSystemProperty(SystemPropertyModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            SystemPropertyModel grpModel = new SystemPropertyModel();
            grpModel.setSystemPropertyName(model.getSystemPropertyName());
            ;
            grpModel = dao.getSystemProperty(grpModel);
            if (grpModel != null)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {

                result = dao.insertSystemProperty(model);
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

    public int updateSystemProperty(SystemPropertyModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            ret = dao.updateSystemProperty(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    @Override
    public List getCustomListRenderer(HashMap hashMap, boolean navigated) {
        return getListRenderer(hashMap, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            return dao.listAvailableSystemPropertyMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public SystemPropertyModel getSystemPropertyDetail(SystemPropertyModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            return dao.getSystemProperty(model);
        } finally {
            session.close();
        }
    }

    public int deleteSystemProperty(SystemPropertyModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            ret = dao.deleteSystemProperty(m);
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

    public List listSystemProperty() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SystemPropertyDao dao = new SystemPropertyDao(session);
            return dao.listSystemProperty();
        } finally {
            session.close();
        }
    }
}