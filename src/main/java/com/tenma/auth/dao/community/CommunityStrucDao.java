/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.auth.dao.community;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.CommunityStrucModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 4:00 PM
 */
public class CommunityStrucDao extends Dao {
    public CommunityStrucDao(SqlSession session) {
        super(session);
    }

    public CommunityStrucModel getCommunityStruc(CommunityStrucModel model) {
        return (CommunityStrucModel) session.selectOne("getCommunityStruc", model);
    }

    public int insertCommunityStruc(CommunityStrucModel communityStruc) {
        int result = 0;
        result = session.insert("insertCommunityStruc", communityStruc);
        return result;
    }

    public int updateCommunityStruc(CommunityStrucModel communityStruc) {
        int result = 0;
        result = session.update("updateCommunityStruc", communityStruc);

        return result;
    }

    public int deleteCommunityStruc(CommunityStrucModel communityStruc) {
        int result = 0;
        result = session.delete("deleteCommunityStruc", communityStruc);
        return result;
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunityStruc", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<CommunityStrucModel> listAvailableCommunityStrucMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCommunityStrucMap", parameterObject);
        else
            return session.selectList("listAllCommunityStrucMap", parameterObject);
    }

    public int getCommunityStructureSquence() {
        Integer sid = (Integer) session.selectOne("getCommunityStrucSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

}
