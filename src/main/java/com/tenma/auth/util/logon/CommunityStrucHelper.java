package com.tenma.auth.util.logon;
/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

import com.tenma.auth.dao.community.CommunityStrucDao;
import com.tenma.auth.model.CommunityStrucModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.auth.util.Converter;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.error.ErrorInfo;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 3:56 PM
 */
public class CommunityStrucHelper extends TenmaHelper<CommunityStrucHelper> {

    public static CommunityStrucHelper use() {
        CommunityStrucHelper helper = new CommunityStrucHelper();
        return helper;
    }

    public int insertNewCommunityStruc(CommunityStrucModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityStrucDao dao = new CommunityStrucDao(session);
            CommunityStrucModel grpModel = new CommunityStrucModel();
            grpModel.setStructureName(model.getStructureName());
            grpModel = dao.getCommunityStruc(grpModel);
            if (grpModel != null)
                throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
            else {
                int seq = dao.getCommunityStructureSquence();
                model.setStructureId(Converter.getNextSequenceId(seq));
                result = dao.insertCommunityStruc(model);
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

    public int updateCommunityStruc(CommunityStrucModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityStrucDao dao = new CommunityStrucDao(session);
            ret = dao.updateCommunityStruc(model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
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
            CommunityStrucDao dao = new CommunityStrucDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<CommunityStrucModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityStrucDao dao = new CommunityStrucDao(session);
            return dao.listAvailableCommunityStrucMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CommunityStrucModel getCommunityStrucDetail(CommunityStrucModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityStrucDao dao = new CommunityStrucDao(session);
            return dao.getCommunityStruc(model);
        } finally {
            session.close();
        }
    }

    public int deleteCommunityStruc(CommunityStrucModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityStrucDao dao = new CommunityStrucDao(session);
            ret = dao.deleteCommunityStruc(m);
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
}