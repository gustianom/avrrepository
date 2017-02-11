/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.generalProperty;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.generalproperty.GeneralPropertyDao;
import com.tenma.common.model.GeneralPropertyModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.common.util.web.Items;
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
public class GeneralPropertyHelper extends TenmaDaoHelper {
    public int insertNewGeneralProperty(GeneralPropertyModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            GeneralPropertyModel grpModel = new GeneralPropertyModel();
            grpModel.setCommunityId(model.getCommunityId());
            grpModel.setGroupType(model.getGroupType());
            grpModel.setPropertyLabel(model.getPropertyLabel());
            grpModel = dao.getGeneralPropertyDetail(model);
            if (grpModel != null)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {
                int inext = dao.getGeneralPropertySequence();
                model.setPropertyId(inext);
                result = dao.insertGeneralProperty(model);
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

    public int updateGeneralProperty(GeneralPropertyModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            ret = dao.updateGeneralProperty(model);
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
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            return dao.listAvailableGeneralPropertyMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public GeneralPropertyModel getGeneralPropertyDetail(GeneralPropertyModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            return dao.getGeneralPropertyDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteGeneralProperty(GeneralPropertyModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            ret = dao.deleteGeneralProperty(m);
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


    public List<Items> listGeneralPropertyItemsByGroup(String groupName) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            return dao.listGeneralPropertyItemsByGroup(groupName);
        } finally {
            session.close();
        }
    }


    public int getGeneralPropertySequence() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyDao dao = new GeneralPropertyDao(session);
            return dao.getGeneralPropertySequence();
        } finally {
            session.close();
        }
    }
}
