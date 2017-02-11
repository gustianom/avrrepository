package com.tenma.fam.dao.refAccCommunity;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.AccountCommunityModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Fri Oct 24 14:43:08 WIB 2014
 */
public class AccountCommunityDao extends Dao {
    public AccountCommunityDao(SqlSession session) {
        super(session);
    }

    public AccountCommunityModel getAccountCommunity(AccountCommunityModel model) {
        return (AccountCommunityModel) session.selectOne("getAccountCommunity", model);
    }

    public int insertAccountCommunity(AccountCommunityModel systemProperty) {
        int result = 0;
        result = session.insert("insertAccountCommunity", systemProperty);
        return result;
    }

    public int updateAccountCommunity(AccountCommunityModel systemProperty) {
        int result = 0;
        result = session.update("updateAccountCommunity", systemProperty);
        return result;
    }

    public int deleteAccountCommunity(AccountCommunityModel systemProperty) {
        int result = 0;
        result = session.delete("deleteAccountCommunity", systemProperty);
        return result;
    }


    public List listAccountCommunity() {
        List list = session.selectList("listAccountCommunity", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalAccountCommunity", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<AccountCommunityModel> listAvailableAccountCommunityMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listAccountCommunityMap", parameterObject);
        else
            return session.selectList("listAllAccountCommunityMap", parameterObject);
    }
}
