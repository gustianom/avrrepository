/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.bean.menu;


import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.menu.MenuDao;
import com.tenma.model.core.MenuModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class MenuListHelper extends TenmaHelper<MenuListHelper> {

    public static MenuListHelper use() {
        MenuListHelper helper = new MenuListHelper();
        return helper;
    }

    public int insertNewMenuList(MenuModel model) throws Exception {
        // CHANGE USING UPSERT - INSERT ON CONFLIC UPDATE
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuDao dao = new MenuDao(session);
            result = dao.insertMenu(model);
            session.commit();
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateMenuList(MenuModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            MenuDao dao = new MenuDao(session);
            ret = dao.updateMenu(model);
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
            MenuDao dao = new MenuDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuDao dao = new MenuDao(session);
            return dao.getMenuList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public MenuModel getMenuListDetail(MenuModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuDao dao = new MenuDao(session);
            return dao.getMenuDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteMenuList(MenuModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            MenuDao dao = new MenuDao(session);
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
}