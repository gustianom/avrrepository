/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.common.util.Constants;
import com.tenma.model.core.UserGroupMenuFunctionModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class CommunityUserGroupMenuFunctionDao extends Dao {
    public CommunityUserGroupMenuFunctionDao(SqlSession session) {
        super(session);
    }

    public int insertFunction(UserGroupMenuFunctionModel function) {
        return session.insert("insertCoreUserGroupMenuFunction", function);
    }

    public int deleteFunctio(UserGroupMenuFunctionModel function) {
        return session.delete("deleteCoreUserGroupMenuFunction", function);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreUserGroupMenuFunction", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    /**
     * used to collect function code for user group or private memberid
     *
     * @param parameterObject
     * @param includeNavigation
     * @return
     */
    public List<UserGroupMenuFunctionModel> getListRenderer(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreUserGroupMenuFunctionMap";
        else param = "listAllCoreUserGroupMenuFunctionMap";
        return session.selectList(param, parameterObject);
    }

    /**
     * used to collect available function for selected memberid on all registered group
     *
     * @param communityId
     * @param menuId
     * @param memberId
     * @param includeNavigation
     * @return
     */
    public List<UserGroupMenuFunctionModel> getListCoreGroupMemberMenuFunction(String communityId, Integer menuId, Integer memberId, boolean includeNavigation) {

        HashMap parameterObject = new HashMap();
        parameterObject.put(Constants.COMMUNITY_ID, communityId);
        parameterObject.put("menuId", menuId);
        parameterObject.put("itemId", memberId);

        String param = "";
        if (includeNavigation)
            param = "listCoreGroupMemberMenuFunctionMap";
        else param = "listAllCoreGroupMemberMenuFunctionMap";
        return session.selectList(param, parameterObject);
    }


}
