/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.MenuGroupModel;
import com.tenma.model.core.StructMenuModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class StructMenuDao extends Dao {
    public StructMenuDao(SqlSession session) {
        super(session);
    }

    public int insertMenu(StructMenuModel menu) {
        return session.insert("insertCoreStructMenu", menu);
    }

    public int updateMenu(StructMenuModel menu) {
        return session.update("updateCoreStructMenu", menu);
    }

    public int deleteMenu(StructMenuModel menu) {
        return session.delete("deleteCoreStructMenu", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreStructMenu", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getMenuList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreStructMenuMap";
        else
            param = "listAllCoreStructMenuMap";
        return session.selectList(param, parameterObject);
    }

    public List getQueryAvailableCoreStructMenu(HashMap mapList, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "getQueryAvailableCoreStructMenu";
        else
            param = "getQueryAllAvailableCoreStructMenu";
        return session.selectList(param, mapList);
    }

    public int countQueryAvailableCoreStructMenu(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countQueryAvailableCoreStructMenu", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<MenuGroupModel> getMenuGroupList(HashMap parameter) {
        return session.selectList("listCoreStructMenuGroup", parameter);
    }
}
