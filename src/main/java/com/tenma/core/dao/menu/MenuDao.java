/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.menu;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.MenuModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:26 AM
 */
public class MenuDao extends Dao {
    public MenuDao(SqlSession session) {
        super(session);
    }

    @Override
    public int countTotalList(HashMap map) {
        Integer count = (Integer) session.selectOne("countTotalCoreMenu", map);
        return count == null ? 0 : count.intValue();
    }

    public MenuModel getMenuDetail(MenuModel menu) {
        return (MenuModel) session.selectOne("getCoreMenuDetail", menu);
    }

    public int insertMenu(MenuModel menu) {
        return session.insert("insertCoreMenuItem", menu);
    }

    public int updateMenu(MenuModel menu) {

        return session.update("updateCoreMenuItem", menu);
    }

    public int deleteMenu(MenuModel menu) {
        return session.delete("deleteCoreMenuItem", menu);
    }

    public int softDeleteMenu(MenuModel menu) {
        return session.update("softDeleteCoreMenuItem", menu);
    }

    public List getMenuList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listCoreMenuMapItem", parameterObject);
        } else {
            list = session.selectList("listAllCoreMenuMapItem", parameterObject);
        }
        return list;
    }

    public int countMenuReference(int menuId) {
        Integer count = (Integer) session.selectOne("countCoreMenuReference", new Integer(menuId));
        return count == null ? 0 : count.intValue();
    }
}

