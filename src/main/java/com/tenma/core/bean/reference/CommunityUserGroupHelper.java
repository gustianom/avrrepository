package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.core.dao.reference.CommunityUserGroupDao;
import com.tenma.core.dao.reference.CommunityUserGroupDetailDao;
import com.tenma.core.dao.reference.CommunityUserGroupMenuDao;
import com.tenma.model.core.UserGroupHeaderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 12/11/14.
 */
public class CommunityUserGroupHelper extends TenmaHelper {
    public int insertNewCoreCommunityUserGroup(UserGroupHeaderModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewCoreCommunityUserGroup(session, m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCoreCommunityUserGroup(SqlSession session, UserGroupHeaderModel m) throws Exception {
        int result = 0;
        CommunityUserGroupDao dao = new CommunityUserGroupDao(session);
        int nextSeq = dao.getQueryGroupSequence();
        m.setGroupId(nextSeq);

        logger.info("CommunityUserGroupHelper.insertNewCoreCommunityUserGroup " + m);
        result = dao.insertUserGroup(m);
        return result;
    }

    public int updateNewCoreCommunityUserGroup(UserGroupHeaderModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDao dao = new CommunityUserGroupDao(session);
            result = dao.updateUserGroup(m);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;

    }

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDao dao = new CommunityUserGroupDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<UserGroupHeaderModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDao dao = new CommunityUserGroupDao(session);
            return dao.getUserGroupList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public int deleteCoreCommunityUserGroup(UserGroupHeaderModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityUserGroupMenuDao menuDao = new CommunityUserGroupMenuDao(session);
            CommunityUserGroupDetailDao detailDao = new CommunityUserGroupDetailDao(session);
            CommunityUserGroupDao dao = new CommunityUserGroupDao(session);

            detailDao.deleteUserGroupDetail(model.getCommunityId(), model.getGroupId());
            menuDao.deleteMenu(model.getCommunityId(), model.getGroupId());
            ret = dao.deleteUserGroup(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;
    }

    public UserGroupHeaderModel getUserGroupDetail(UserGroupHeaderModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDao dao = new CommunityUserGroupDao(session);
            return dao.getUserGroupDetail(model);
        } finally {
            session.close();
        }
    }

}
