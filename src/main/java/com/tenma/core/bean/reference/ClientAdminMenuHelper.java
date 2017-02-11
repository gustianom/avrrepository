package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.reference.ClientAdminMenuDao;
import com.tenma.model.core.UserGroupMenuModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 8/7/14.
 * for mankidu and community menu registration
 */
public class ClientAdminMenuHelper extends TenmaHelper {
    public int insertNewClientAdminMenu(UserGroupMenuModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            result = dao.insertMenu(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            return dao.getMenuList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public int deleteClientAdminMenu(UserGroupMenuModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            ret = dao.deleteMenu(m);
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

    /*
    public List getQueryAvailableClientAdminMenu(HashMap mapList, boolean includeNavigation) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            return dao.getQueryAvailableClientAdminMenu(mapList, includeNavigation);
        } finally {
            session.close();
        }
    }

    public int countQueryAvailableClientAdminMenu(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ClientAdminMenuDao dao = new ClientAdminMenuDao(session);
            return dao.countQueryAvailableClientAdminMenu(mapList);
        } finally {
            session.close();
        }
    }

    */
}
