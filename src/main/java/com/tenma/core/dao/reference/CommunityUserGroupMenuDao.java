/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.common.util.Constants;
import com.tenma.model.core.CommunityUserGroupMenuModel;
import com.tenma.model.core.MenuGroupModel;
import com.tenma.model.core.UserGroupHeaderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class CommunityUserGroupMenuDao extends Dao {
    public CommunityUserGroupMenuDao(SqlSession session) {
        super(session);
    }

    public int insertMenu(CommunityUserGroupMenuModel menu) {
        System.out.println("CommunityUserGroupMenuDao.insertMenu " + menu);
        System.out.println("menu. = " + menu.getCommunityId());
        System.out.println("menu.getMenuId() = " + menu.getMenuId());
        System.out.println("menu.getItemId() = " + menu.getItemId());
        System.out.println("menu.getItemType() = " + menu.getItemType());
        return session.insert("insertCoreCommunityUserGroupMenu", menu);
    }

    public int updateMenu(CommunityUserGroupMenuModel model) {
        return session.update("updateCoreCommunityUserGroupMenu", model);

    }

    public int deleteMenu(CommunityUserGroupMenuModel menu) {
        return session.delete("deleteCoreCommunityUserGroupMenu", menu);
    }

    public int deleteMenu(String communityId, Integer userGroupId) {
        CommunityUserGroupMenuModel menu = new CommunityUserGroupMenuModel();
        menu.setCommunityId(communityId);
        menu.setItemId(userGroupId);
        menu.setItemType(0);
        return session.delete("deleteCoreCommunityUserGroupMenu", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreCommunityUserGroupMenu", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getMenuList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation) {
            if (parameterObject.containsKey("userRegisteredMenu"))
                param = "listCoreCommunityUserGroupRegisteredMenuMap";
            else
                param = "listCoreCommunityUserGroupMenuMap";
        } else {
            if (parameterObject.containsKey("userRegisteredMenu"))
                param = "listAllCoreCommunityUserGroupRegisteredMenuMap";
            else
                param = "listAllCoreCommunityUserGroupMenuMap";
        }
        return session.selectList(param, parameterObject);
    }

    public List<CommunityUserGroupMenuModel> getUserRegisteredGroup(Integer memberId, String communityId) {
        HashMap map = new HashMap();
        map.put("memberId", memberId);
        map.put(Constants.COMMUNITY_ID, communityId);
        return session.selectList("getCoreUserRegisteredGroup", map);
    }

    public List<MenuGroupModel> getInstalledMenuGroupCategory(String communityId, Integer userGroupId) {
        HashMap parameter = new HashMap();
        parameter.put(Constants.COMMUNITY_ID, communityId);
        parameter.put("itemId", userGroupId);
        parameter.put("itemType", 0);
        return session.selectList("getCoreUserGroupInstalledMenuGroup", parameter);
    }

    public List<CommunityUserGroupMenuModel> queryAuthenticatedMenu(String communityId, Integer memberId, Integer menuItemId) {
        HashMap parameter = new HashMap();
        parameter.put(Constants.COMMUNITY_ID, communityId);
        parameter.put("memberId", memberId);
        parameter.put("menuItemId", menuItemId);
        return session.selectList("queryAuthenticatedMenu", parameter);
    }


    public int insertNewMenu(CommunityUserGroupMenuModel model) {
        return session.insert("insertCoreCommunityUserGroupHeaderMenu", model);
    }

    public int insertNewMenuTeacher(CommunityUserGroupMenuModel model) {
        return session.insert("insertCoreCommunityUserGroupHeaderTeacherMenu", model);
    }
}
