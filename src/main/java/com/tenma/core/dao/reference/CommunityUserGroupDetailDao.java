/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.UserGroupDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class CommunityUserGroupDetailDao extends Dao {
    public CommunityUserGroupDetailDao(SqlSession session) {
        super(session);
    }

    public int insertUserGroupDetail(UserGroupDetailModel menu) {
        return session.insert("insertCoreCommunityUserGroupDetail", menu);
    }

    public int deleteUserGroupDetail(UserGroupDetailModel menu) {
        return session.delete("deleteCoreCommunityUserGroupDetail", menu);
    }

    public int deleteUserGroupDetail(String communityId, Integer userGroupId) {
        UserGroupDetailModel menu = new UserGroupDetailModel();
        menu.setCommunityId(communityId);
        menu.setGroupId(userGroupId);
        return session.delete("deleteCoreCommunityUserGroupDetail", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreCommunityUserGroupDetail", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<UserGroupDetailModel> getUserGroupDetailList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreCommunityUserGroupDetailMap";
        else
            param = "listAllCoreCommunityUserGroupDetailMap";
        return session.selectList(param, parameterObject);
    }

}
