package com.tenma.core.bean.reference;

import com.tenma.auth.model.CommunityModel;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.core.dao.reference.CommunityMenuDao;
import com.tenma.model.core.CommunityMenuModel;
import com.tenma.model.core.MenuGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 12/11/14.
 */
public class CommunityMenuHelper extends TenmaHelper {
    public int insertNewCoreCommunityMenu(CommunityMenuModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMenuDao dao = new CommunityMenuDao(session);
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

    public int insertNewCoreCommunityMenu(CommunityMenuModel m, SqlSession session) throws Exception {
        int result = 0;
        CommunityMenuDao dao = new CommunityMenuDao(session);
        result = dao.insertMenu(m);
        return result;
    }

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMenuDao dao = new CommunityMenuDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<CommunityMenuModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMenuDao dao = new CommunityMenuDao(session);
            return dao.getMenuList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public int deleteCoreCommunityMenu(CommunityMenuModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityMenuDao dao = new CommunityMenuDao(session);
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

    public List<MenuGroupModel> getMenuGroupCategory(HashMap parameter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMenuDao dao = new CommunityMenuDao(session);
            return dao.getMenuGroupCategory(parameter);
        } finally {
            session.close();
        }
    }

    public int insertCoreCommunityMenu(SqlSession session, CommunityModel communityModel) throws Exception {
        int result = 0;

        CommunityMenuDao dao = new CommunityMenuDao(session);
        result = dao.insertNewMenu(communityModel);
        return result;

    }


}
