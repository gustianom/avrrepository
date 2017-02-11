package com.tenma.core.bean.groupcommunity;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.groupcommunity.GroupCommunityDao;
import com.tenma.model.core.GroupCommunityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Tue Dec 02 22:19:21 ICT 2014
 */
public class GroupCommunityHelper extends TenmaHelper {
    public int insertNewGroupCommunity(GroupCommunityModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            GroupCommunityModel grpModel = new GroupCommunityModel();
            grpModel.setGroupId(model.getGroupId());
            grpModel = dao.getGroupCommunity(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertGroupCommunity(model);
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

    public int updateGroupCommunity(GroupCommunityModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            ret = dao.updateGroupCommunity(model);
            session.commit();
        } catch (Exception e) {
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
            GroupCommunityDao dao = new GroupCommunityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }


    public List getListProductQtty() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            HashMap map = new HashMap();
            return dao.listProductQtty(map);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            return dao.listAvailableGroupCommunityMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public GroupCommunityModel getGroupCommunityDetail(GroupCommunityModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            return dao.getGroupCommunity(model);
        } finally {
            session.close();
        }
    }

    public int deleteGroupCommunity(GroupCommunityModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            ret = dao.deleteGroupCommunity(m);
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

    public List listGroupCommunity() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            GroupCommunityDao dao = new GroupCommunityDao(session);
            return dao.listGroupCommunity();
        } finally {
            session.close();
        }
    }
}