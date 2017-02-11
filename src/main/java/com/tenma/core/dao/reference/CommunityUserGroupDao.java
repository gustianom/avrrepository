/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.UserGroupHeaderModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class CommunityUserGroupDao extends Dao {
    public CommunityUserGroupDao(SqlSession session) {
        super(session);
    }

    public int insertUserGroup(UserGroupHeaderModel menu) {
        return session.insert("insertCoreCommunityUserGroup", menu);
    }

    public int updateUserGroup(UserGroupHeaderModel menu) {
        return session.update("updateCoreCommunityUserGroup", menu);
    }

    public int deleteUserGroup(UserGroupHeaderModel menu) {
        return session.delete("deleteCoreCommunityUserGroup", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreCommunityUserGroup", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getUserGroupList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreCommunityUserGroupMap";
        else
            param = "listAllCoreCommunityUserGroupMap";
        return session.selectList(param, parameterObject);
    }

    public UserGroupHeaderModel getUserGroupDetail(UserGroupHeaderModel model) {
        return (UserGroupHeaderModel) session.selectOne("getCoreCommunityUserGroup", model);
    }

    public int getQueryGroupSequence() {
        Integer count = (Integer) session.selectOne("getQueryGroupSequence");
        return count == null ? 0 : count.intValue();
    }
}
