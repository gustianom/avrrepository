/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.CommunityModel;
import com.tenma.model.core.CommunityMenuModel;
import com.tenma.model.core.MenuGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class CommunityMenuDao extends Dao {
    public CommunityMenuDao(SqlSession session) {
        super(session);
    }

    public int insertMenu(CommunityMenuModel menu) {
        return session.insert("insertCoreCommunityMenu", menu);
    }

    public int deleteMenu(CommunityMenuModel menu) {
        return session.delete("deleteCoreCommunityMenu", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreCommunityMenu", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getMenuList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreCommunityMenuMap";
        else
            param = "listAllCoreCommunityMenuMap";
        return session.selectList(param, parameterObject);
    }

    public List<MenuGroupModel> getMenuGroupCategory(HashMap parameter) {
        return session.selectList("getCommunityMenuGroupAvailableList", parameter);
    }


    public int insertNewMenu(CommunityModel communityModel) {
        return session.insert("insertCoreCommunityModel", communityModel);
    }
}
