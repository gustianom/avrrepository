package com.tenma.fam.dao.account;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.AccountModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/16/12
 * Time: 12:07 PM
 */
public class AccountDao extends Dao {
    public AccountDao(SqlSession session) {
        super(session);
    }

    public AccountModel getAccountDetail(AccountModel model) {
        System.out.println("model.getAccountId() = " + model.getAccountId());
        return (AccountModel) session.selectOne("getAccountDetail", model);
    }

    public AccountModel getAccountCommunityDetail(AccountModel model) {
        return (AccountModel) session.selectOne("getAccountCommunityDetail`", model);
    }

    public int insertAccount(AccountModel account) {
        int result = 0;
        result = session.insert("insertAccount", account);
        return result;
    }

    public int updateAccount(AccountModel account) {
        int result = 0;
        result = session.update("updateAccount", account);

        return result;
    }

    public int updateAccountParent(AccountModel account) {
        int result = 0;
        result = session.update("updateAccountParent", account);

        return result;
    }


    public int deleteAccount(AccountModel account) {
        int result = 0;
        result = session.update("deleteAccount", account);

        return result;
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalAccount", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getAccountByResponsibilityId(Object parameterObject) {
        List list = session.selectList("accountByResponsibilityId", parameterObject);
        return list;
    }

    public List<AccountModel> listAvailableAccountMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listAccountMap", parameterObject);
        else
            return session.selectList("listAllAccountMap", parameterObject);
    }


    public int getNextAccountId() {
        int inNext;
        Integer sid = (Integer) session.selectOne("getAccountIdSequence", new Object());
        System.out.println("AccountDao.getNextActivation sid = " + sid);
        inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public List getAccount(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listAccountMap", parameterObject);
        } else {
            list = session.selectList("listAllAccountMap", parameterObject);
        }
        return list;
    }

//    public List getAccountCommunity(HashMap parameterObject, boolean includeNavigation) {
//        System.out.println("parameterObject.get(\"status\") = " + parameterObject.get("status"));
//        List list = null;
//        if (includeNavigation) {
//            list = session.selectList("listAccountCommunityMap", parameterObject);
//        } else {
//            list = session.selectList("listAllAccountCommunityMap", parameterObject);
//        }
//        return list;
//    }


    public List getModuleAccountObject() {
        List list = session.selectList("getModuleAccountObject");
        return list;
    }

    public List listAccountItems(AccountModel accountModel) {
        List list = session.selectList("listAccountItems", accountModel);
        return list;
    }

    public Integer getAccountNormal(String accountId) {
        Integer count = (Integer) session.selectOne("getAccountNormal", accountId);
        return count == null ? 0 : count.intValue();
    }

}