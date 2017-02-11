package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.core.dao.reference.CommunityUserGroupDetailDao;
import com.tenma.model.core.UserGroupDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 12/11/14.
 */
public class CommunityUserGroupDetailHelper extends TenmaHelper {
    public int insertNewCoreCommunityUserGroupDetail(UserGroupDetailModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDetailDao dao = new CommunityUserGroupDetailDao(session);
            result = dao.insertUserGroupDetail(m);
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

    public int insertNewCoreCommunityUserGroupDetail(SqlSession session, UserGroupDetailModel m) throws Exception {
        int result = 0;
        CommunityUserGroupDetailDao dao = new CommunityUserGroupDetailDao(session);
        result = dao.insertUserGroupDetail(m);
        return result;
    }

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDetailDao dao = new CommunityUserGroupDetailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<UserGroupDetailModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityUserGroupDetailDao dao = new CommunityUserGroupDetailDao(session);
            return dao.getUserGroupDetailList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public int deleteCoreCommunityUserGroupDetail(UserGroupDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityUserGroupDetailDao dao = new CommunityUserGroupDetailDao(session);
            ret = dao.deleteUserGroupDetail(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;
    }
}
