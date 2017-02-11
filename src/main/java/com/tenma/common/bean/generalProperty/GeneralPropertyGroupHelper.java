/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.generalProperty;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.generalproperty.GeneralPropertyGroupDao;
import com.tenma.common.model.GeneralPropertyGroupModel;
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
public class GeneralPropertyGroupHelper extends TenmaDaoHelper {
    public int insertNewGeneralProperty(GeneralPropertyGroupModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            GeneralPropertyGroupModel grpModel = new GeneralPropertyGroupModel();
            grpModel.setGroupCode(model.getGroupCode());
            grpModel.setCommunityId(model.getCommunityId());
            grpModel = getGeneralPropertyDetail(model);
            if (grpModel != null)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertGeneralPropertyGroup(model);
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

    public int updateGeneralProperty(GeneralPropertyGroupModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            ret = dao.updateGeneralPropertyGroup(model);
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
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            return dao.listAvailableGeneralPropertyGroupMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List listGeneralPropertyGroupItems() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            return dao.listGeneralPropertyGroupItems();
        } finally {
            session.close();
        }
    }

    public GeneralPropertyGroupModel getGeneralPropertyDetail(GeneralPropertyGroupModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            return dao.getGeneralPropertyGroupDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteGeneralProperty(GeneralPropertyGroupModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            ret = dao.deleteGeneralPropertyGroup(m);
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

    public int getGeneralPropertyGroupSequence() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GeneralPropertyGroupDao dao = new GeneralPropertyGroupDao(session);
            return dao.getGeneralPropertyGroupSequence();
        } finally {
            session.close();
        }
    }
}