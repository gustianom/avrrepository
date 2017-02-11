package com.tenma.fam.bean.bankaccount;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.fam.dao.bankaccount.BankAccountDao;
import com.tenma.model.fam.BankAccountModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Mon Mar 31 13:44:01 ICT 2014
 */
public class BankAccountHelper extends TenmaDaoHelper {
    public int insertNewBankAccount(BankAccountModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankAccountDao dao = new BankAccountDao(session);
            BankAccountModel grpModel = new BankAccountModel();
            grpModel.setAcccountNumber(model.getAcccountNumber());
            grpModel = dao.getBankAccount(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertBankAccount(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
                session.commit();
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateBankAccount(BankAccountModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BankAccountDao dao = new BankAccountDao(session);
            ret = dao.updateBankAccount(model);
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
            BankAccountDao dao = new BankAccountDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankAccountDao dao = new BankAccountDao(session);
            return dao.listAvailableBankAccountMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public BankAccountModel getBankAccountDetail(BankAccountModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankAccountDao dao = new BankAccountDao(session);
            return dao.getBankAccount(model);
        } finally {
            session.close();
        }
    }

    public int deleteBankAccount(BankAccountModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BankAccountDao dao = new BankAccountDao(session);
            ret = dao.deleteBankAccount(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listBankAccount() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankAccountDao dao = new BankAccountDao(session);
            return dao.listBankAccount();
        } finally {
            session.close();
        }
    }
}