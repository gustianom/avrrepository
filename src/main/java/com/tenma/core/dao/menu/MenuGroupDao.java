/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.menu;

import com.tenma.auth.dao.Dao;
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
public class MenuGroupDao extends Dao {
    public MenuGroupDao(SqlSession session) {
        super(session);
    }

    public MenuGroupModel getMenuGroupDetail(MenuGroupModel menuGroup) {
        return (MenuGroupModel) session.selectOne("getCoreMenuGroupDetail", menuGroup);
    }

    public int insertMenuGroup(MenuGroupModel menuGroup) {
        return session.insert("insertCoreMenuGroup", menuGroup);
    }

    public int updateMenuGroup(MenuGroupModel menuGroup) {

        return session.update("updateCoreMenuGroup", menuGroup);
    }

    public int deleteMenuGroup(MenuGroupModel menuGroup) {
        return session.delete("deleteCoreMenuGroup", menuGroup);
    }

    public int softDeleteMenuGroup(MenuGroupModel menuGroup) {
        return session.update("softDeleteCoreMenuGroup", menuGroup);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreMenuGroup", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getMenuGroupList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listCoreMenuGroupMap", parameterObject);
        } else {
            list = session.selectList("listAllCoreMenuGroupMap", parameterObject);
        }
        return list;
    }

    public int countMenuGroupReference(int menuGroupId) {
        Integer count = (Integer) session.selectOne("countCoreMenuGroupReference", new Integer(menuGroupId));
        return count == null ? 0 : count.intValue();
    }

    public int countMenuGroupChild(int menuGroupId) {
        Integer count = (Integer) session.selectOne("countCoreMenuGroupChild", new Integer(menuGroupId));
        return count == null ? 0 : count.intValue();
    }

}

