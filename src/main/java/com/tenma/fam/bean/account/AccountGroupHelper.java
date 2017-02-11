package com.tenma.fam.bean.account;

import com.tenma.common.TA;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.sequence.SEQUENCE_Constants;
import com.tenma.common.util.sequence.SequenceTool;
import com.tenma.fam.dao.account.AccountGroupDao;
import com.tenma.model.fam.AccountGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 5/30/12
 * Time: 9:48 AM
 */
//derry, lanjutkan saja yang memakai primefaces, sampai versi yang baru sudah matang
//tolong kasi tahu juga sigit

public class AccountGroupHelper extends TenmaHelper {
    public int insertNewAccountGroup(AccountGroupModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            AccountGroupModel m = new AccountGroupModel();
            m.setAccountGrpName(model.getAccountGrpName());
            AccountGroupModel grpModel = dao.getAccountGrpDetail(m);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                SequenceTool tools = SequenceTool.getInstance();
                int seq = tools.getNewCounter(TA.getCurrent().getCommunityModel().getCommunityId(), SEQUENCE_Constants.ACCOUNT_GROUP_SEQUENCE, true);
                String seqId = tools.addZeroPadWithYear(seq, 12, 4);
                model.setAccountGrpId(seqId);
                result = dao.insertAccountGrp(model);
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

    public int updateAccountGroup(AccountGroupModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            ret = dao.updateAccountGrp(model);
            session.commit();
        } catch (Exception e) {
            logger.info("e = " + e);
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            return dao.getAccountGrpList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public AccountGroupModel getAccountGroupDetail(AccountGroupModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            return dao.getAccountGrpDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteAccountGroup(AccountGroupModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            logger.info("model = " + model);
            ret = dao.deleteAccountGrp(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public List listAccountGroup() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountGroupDao dao = new AccountGroupDao(session);
            return dao.listAccountGroup();
        } finally {
            session.close();
        }
    }

}

