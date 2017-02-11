package com.tenma.core.bean.reference;


import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.core.dao.reference.CommunityUserGroupMenuDao;
import com.tenma.model.core.CommunityUserGroupMenuModel;
import com.tenma.model.core.MenuGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 12/11/14.
 */
public class CommunityUserGroupMenuHelper extends TenmaHelper {
    public int insertNewCoreCommunityUserGroupMenu(CommunityUserGroupMenuModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            result = dao.insertMenu(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCoreCommunityUserGroupMenu(SqlSession session, CommunityUserGroupMenuModel m) throws Exception {
        int result = 0;
        CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
        result = dao.insertMenu(m);
        return result;
    }

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<CommunityUserGroupMenuModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            return dao.getMenuList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public int deleteCoreCommunityUserGroupMenu(CommunityUserGroupMenuModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            ret = dao.deleteMenu(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;
    }

    public List<CommunityUserGroupMenuModel> getUserRegisteredGroup(Integer memberId, String communityId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            return dao.getUserRegisteredGroup(memberId, communityId);
        } finally {
            session.close();
        }
    }

    public List<MenuGroupModel> getInstalledMenuGroupCategory(String communityId, Integer userGroupId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            return dao.getInstalledMenuGroupCategory(communityId, userGroupId);
        } finally {
            session.close();
        }
    }

    public List<CommunityUserGroupMenuModel> queryAuthenticatedMenu(String communityId, Integer memberId, Integer menuItemId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
            return dao.queryAuthenticatedMenu(communityId, memberId, menuItemId);
        } finally {
            session.close();
        }
    }

    public int updateCoreCommunityUserGroupMenu(CommunityUserGroupMenuModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateCoreCommunityUserGroupMenu(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FIELD, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public int updateCoreCommunityUserGroupMenu(SqlSession session, CommunityUserGroupMenuModel model) throws Exception {
        int ret = 0;
        CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
        ret = dao.updateMenu(model);
        return ret;

    }

    public int insertCoreCommunityUserGroupMenu(SqlSession session, CommunityUserGroupMenuModel headerModel) throws Exception {
        int result = 0;


        CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);
        logger.info("\n\n\n\n\n\n *******insertCoreCommunityUserGroupMenu");
        result = dao.insertNewMenu(headerModel);
        logger.info("\n\n\n\n\n\n *******insertCoreCommunityUserGroupMenu");
        logger.info("headerModel = " + headerModel);
        return result;
    }

    public int insertCoreCommunityUserGroupMenuTeacher(SqlSession session, CommunityUserGroupMenuModel headerModel) throws Exception {
        int result = 0;


        CommunityUserGroupMenuDao dao = new CommunityUserGroupMenuDao(session);

        result = dao.insertNewMenuTeacher(headerModel);

        return result;
    }


}
