package com.tenma.fam.bean.refAccCommunity;

import com.tenma.auth.model.AccountCommunityModel;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.fam.dao.refAccCommunity.AccountCommunityDao;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Fri Oct 24 14:43:08 WIB 2014
 */
public class AccountCommunityHelper extends TenmaHelper {
    public int insertNewAccountCommunity(AccountCommunityModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
//            AccountCommunityModel grpModel = new AccountCommunityModel();
//            grpModel.setCommunityId(model.getCommunityId());
//            grpModel.setCommunityId(model.getCommunityId());
//            grpModel = dao.getAccountCommunity(grpModel);
//            if (grpModel != null)
//                throw new Exception(FAM_Constants.ADD_ALREADY_EXIST);
//            else {
            result = dao.insertAccountCommunity(model);
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);
            session.commit();
//            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateAccountCommunity(AccountCommunityModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
            ret = dao.updateAccountCommunity(model);
            session.commit();
        } catch (Exception e) {
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
            AccountCommunityDao dao = new AccountCommunityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
            return dao.listAvailableAccountCommunityMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public AccountCommunityModel getAccountCommunityDetail(AccountCommunityModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
            return dao.getAccountCommunity(model);
        } finally {
            session.close();
        }
    }

    public int deleteAccountCommunity(AccountCommunityModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
            ret = dao.deleteAccountCommunity(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().contains(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()))
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listAccountCommunity() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AccountCommunityDao dao = new AccountCommunityDao(session);
            return dao.listAccountCommunity();
        } finally {
            session.close();
        }
    }
}