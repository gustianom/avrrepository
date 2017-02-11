/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.UserGroupMenuModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class ClientAdminMenuDao extends Dao {
    public ClientAdminMenuDao(SqlSession session) {
        super(session);
    }

    public int insertMenu(UserGroupMenuModel menu) {
        return session.insert("insertCoreUserGroupMenu", menu);
    }

    public int deleteMenu(UserGroupMenuModel menu) {
        return session.delete("deleteCoreUserGroupMenu", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreUserGroupMenu", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getMenuList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";

//        if (parameterObject != null) {
//            Object o = parameterObject.get("groupId");
//            if (o != null) {
//                Integer groupId = (Integer) o;
//                if (Constants.MEMBER_ROLE_TYPE.ADMIN.equals(groupId))
//                    parameterObject.put("groupId", null); // all menu must available for admin
//            }
//        }
        if (includeNavigation)
            param = "listCoreUserGroupMenuMap";
        else
            param = "listAllCoreUserGroupMenuMap";

        System.out.println("ClientAdminMenuDao.getMenuList -------------------------------------");

        System.out.println(param + " parameterObject = " + parameterObject);
        return session.selectList(param, parameterObject);
    }
}
