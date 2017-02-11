package com.tenma.fam.bean.account;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.dao.account.AccountDao;
import com.tenma.model.fam.AccountModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * User: derry.irmansyah
 * Date: 5/16/12
 * Time: 12:09 PM
 */
public class AccountHelper extends TenmaHelper implements CommonPagingHelper {

    private Locale local;

    public AccountHelper(Locale local) {
        this.local = local;
    }

    public AccountHelper() {
    }


    public int insertNewAccountCorporate(AccountModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewAccountCorporate(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewAccountCorporate(SqlSession session, AccountModel model) throws Exception {
        int result = 0;
        AccountDao dao = new AccountDao(session);
        model.setAccountId(model.getAccountId());
        model.setCommunityId(model.getCommunityId());
        model.setStatus(model.getStatus());
        AccountModel grpModel = dao.getAccountDetail(model);
        if (grpModel != null)
            throw new Exception(Constants.ADD_ALREADY_EXIST);
        else {
            result = dao.insertAccount(model);
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);
        }
        return result;
    }

    public int insertNewAccountCommunity(AccountModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            SequenceTool tools = SequenceTool.getInstance();
            int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.ACCOUNT_COMMUNITY_SEQUENCE, false);
            String seqId = tools.addZeroPadWithYear(seq, 14, 2);
            model.setAccountId(seqId);
            AccountModel grpModel = dao.getAccountDetail(model);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertAccount(model);
                session.commit();
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateAccount(AccountModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountDao dao = new AccountDao(session);
            ret = dao.updateAccount(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            logger.info("e = " + e);
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.getAccount(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public AccountModel getAccountDetail(AccountModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.getAccountDetail(model);
        } finally {
            session.close();
        }
    }

    public AccountModel getAccountCommunityDetail(AccountModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.getAccountCommunityDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteAccount(AccountModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountDao dao = new AccountDao(session);
            ret = dao.deleteAccount(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int deleteAccountUser(AccountModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountDao dao = new AccountDao(session);
            ret = dao.deleteAccount(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public List getModuleAccountObject() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.getModuleAccountObject();
        } finally {
            session.close();
        }
    }

    public Integer getAccountNormal(String accountId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.getAccountNormal(accountId);
        } finally {
            session.close();
        }
    }

    public List listAccountItems(AccountModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountDao dao = new AccountDao(session);
            return dao.listAccountItems(model);
        } finally {
            session.close();
        }
    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }
}