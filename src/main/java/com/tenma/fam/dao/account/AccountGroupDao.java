package com.tenma.fam.dao.account;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.AccountGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/30/12
 * Time: 9:46 AM
 */
public class AccountGroupDao extends Dao {
    public AccountGroupDao(SqlSession session) {
        super(session);
    }

    public AccountGroupModel getAccountGrpDetail(AccountGroupModel accountGrp) {
        return (AccountGroupModel) session.selectOne("getAccountGrpDetail", accountGrp);
    }

    public int insertAccountGrp(AccountGroupModel accountGrp) {
        return session.insert("insertAccountGrp", accountGrp);
    }

    public int updateAccountGrp(AccountGroupModel accountGrp) {

        return session.update("updateAccountGrp", accountGrp);
    }

    public int deleteAccountGrp(AccountGroupModel accountGrp) {
        return session.update("deleteAccountGrp", accountGrp);
    }

    public int softDeleteAccountGrp(AccountGroupModel accountGrp) {
        return session.update("softDeleteAccountGrp", accountGrp);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalAccountGrp", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getAccountGrpList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listAccountGroupMap", parameterObject);
        } else {
            list = session.selectList("listAllAccountGroupMap", parameterObject);
        }
        return list;
    }

    public int countAccountGrpReference(int accountGrpId) {
        Integer count = (Integer) session.selectOne("countAccountGrpReference", new Integer(accountGrpId));
        return count == null ? 0 : count.intValue();
    }

    public int countAccountGrpChild(int accountGrpId) {
        Integer count = (Integer) session.selectOne("countAccountGrpChild", new Integer(accountGrpId));
        return count == null ? 0 : count.intValue();
    }

    public int getAccountGrpSequence() {
        Integer sid = (Integer) session.selectOne("getAccountGrpSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public List listAccountGroup() {
        List list = session.selectList("getAccountGrpDetail", null);
        return list;
    }
}


