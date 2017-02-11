/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.bean.menu;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.menu.MenuGroupDao;
import com.tenma.model.core.MenuGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:26 AM
 */
public class MenuGroupHelper extends TenmaHelper<MenuGroupHelper> {

    public static MenuGroupHelper use() {
        MenuGroupHelper helper = new MenuGroupHelper();
        return helper;
    }

    public int insertNewMenuGroup(MenuGroupModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuGroupDao dao = new MenuGroupDao(session);
            result = dao.insertMenuGroup(model);
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

    public int updateMenuGroup(MenuGroupModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            MenuGroupDao dao = new MenuGroupDao(session);
            ret = dao.updateMenuGroup(model);
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
            MenuGroupDao dao = new MenuGroupDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuGroupDao dao = new MenuGroupDao(session);
            return dao.getMenuGroupList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public MenuGroupModel getMenuGroupDetail(MenuGroupModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MenuGroupDao dao = new MenuGroupDao(session);
            return dao.getMenuGroupDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteMenuGroup(MenuGroupModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            MenuGroupDao dao = new MenuGroupDao(session);
            ret = dao.deleteMenuGroup(m);
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