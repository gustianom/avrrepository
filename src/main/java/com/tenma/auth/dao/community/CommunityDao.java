package com.tenma.auth.dao.community;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.CommunityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 08/02/15.
 */
public class CommunityDao extends Dao {
    public CommunityDao(SqlSession session) {
        super(session);
    }


    public CommunityModel getCommunityDetail(CommunityModel community) {
        return (CommunityModel) session.selectOne("getCommunityDetail", community);
    }

    public int insertCommunity(CommunityModel community) {
        return session.insert("insertCommunity", community);
    }

    public int updateCommunity(CommunityModel community) {

        return session.update("updateCommunity", community);
    }

    public int deleteCommunity(CommunityModel community) {
        return session.update("deleteCommunity", community);
    }

    public int softDeleteCommunity(CommunityModel community) {
        return session.update("softDeleteCommunity", community);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCommunity", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getCommunityList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listCommunityMap", parameterObject);
        } else {
            list = session.selectList("listAllCommunityMap", parameterObject);
        }
        return list;
    }

    public List getCommunityListForBalanceService(HashMap parameterObject) {
        List list = null;
        list = session.selectList("listAllCommunityForBalanceServices", parameterObject);
        return list;
    }


    public int getCommunitySequence() {
        Integer sid = (Integer) session.selectOne("getCommunitySequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public List listCommunityItems(HashMap map) {
        List list = session.selectList("listCommunityItems", map);
        return list;
    }

    public List listCommunityNativeItems(HashMap maplist) {
        List list = session.selectList("listNativeCommunityItems", maplist);
        return list;
    }

    public int checkExistingCommunity(HashMap map) {
        Integer count = (Integer) session.selectOne("checkExistingCommunity", map);
        return count == null ? 0 : count.intValue();
    }

    public String getRootMasterCommunityId(String communityId) {
        String count = (String) session.selectOne("getParentCommunityId", communityId);
        return count;
    }

    public int getNextCommunityId() {
        return 0;
    }
}



