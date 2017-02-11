package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.core.dao.reference.CommunityUserGroupMenuFunctionDao;
import com.tenma.model.core.UserGroupMenuFunctionModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 12/11/14.
 */
public class CommunityUserGroupMenuFunctionHelper extends TenmaHelper {
    public int insertFunction(UserGroupMenuFunctionModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuFunctionDao dao = new CommunityUserGroupMenuFunctionDao(session);
            result = dao.insertFunction(m);
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

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuFunctionDao dao = new CommunityUserGroupMenuFunctionDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<UserGroupMenuFunctionModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuFunctionDao dao = new CommunityUserGroupMenuFunctionDao(session);
            return dao.getListRenderer(mapList, navigated);
        } finally {
            session.close();
        }
    }

    /**
     * used to collect available function for selected memberid on all registered group
     *
     * @param communityId
     * @param menuId
     * @param memberId
     * @param navigated
     * @return
     */
    public List<UserGroupMenuFunctionModel> getListCoreGroupMemberMenuFunction(String communityId, Integer menuId, Integer memberId, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupMenuFunctionDao dao = new CommunityUserGroupMenuFunctionDao(session);
            return dao.getListCoreGroupMemberMenuFunction(communityId, menuId, memberId, navigated);
        } finally {
            session.close();
        }
    }

    public int deleteCoreCommunityUserGroupMenu(UserGroupMenuFunctionModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = deleteCoreCommunityUserGroupMenu(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;
    }

    public int deleteCoreCommunityUserGroupMenu(SqlSession session, UserGroupMenuFunctionModel model) throws Exception {
        int ret = 0;
        CommunityUserGroupMenuFunctionDao dao = new CommunityUserGroupMenuFunctionDao(session);
        ret = dao.deleteFunctio(model);
        return ret;
    }

}
