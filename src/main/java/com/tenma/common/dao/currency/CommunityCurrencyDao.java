package com.tenma.common.dao.currency;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.CommunityCurrencyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: sigit
 * Date: 01/04/13
 * Time: 10:58 AM
 */
public class CommunityCurrencyDao extends Dao {
    public CommunityCurrencyDao(SqlSession session) {
        super(session);
    }

    public CommunityCurrencyModel getRefCommunityCurrencyDetail(CommunityCurrencyModel refComMember) {
        return (CommunityCurrencyModel) session.selectOne("getRefCommunityCurrencyDetail", refComMember);
    }

    public int insertRefCommunityCurrency(CommunityCurrencyModel refComMember) {
        return session.insert("insertRefCommunityCurrency", refComMember);
    }

    public int deleteAllRefCommunityCurrency(CommunityCurrencyModel refComMember) {
        return session.update("deleteAllRefCommunityCurrency", refComMember);
    }

    public int softDeleteRefCommunityCurrency(CommunityCurrencyModel refComMember) {
        return session.update("softDeleteRefCommunityCurrency", refComMember);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalRefCommunityCurrList", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getRefCommunityCurrencyList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listRefCommunityCurrListMap", parameterObject);
        } else {
            list = session.selectList("listAllRefCommunityCurrListMap", parameterObject);
        }
        return list;
    }

    public List listCommunityCurrencyItem(String communityId) {
        List list = session.selectList("listCommunityCurrencyItems", communityId);
        return list;
    }

}
