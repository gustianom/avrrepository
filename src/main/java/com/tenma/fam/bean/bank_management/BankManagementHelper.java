package com.tenma.fam.bean.bank_management;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.fam.dao.bank_management.BankManagementDao;
import com.tenma.model.fam.BankManagementModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Mon Sep 08 10:13:58 WIB 2014
 */
public class BankManagementHelper extends TenmaDaoHelper {
    public int insertNewBankManagement(BankManagementModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankManagementDao dao = new BankManagementDao(session);
            BankManagementModel grpModel = new BankManagementModel();
            grpModel.setBankId(model.getBankId());
            grpModel = dao.getBankManagement(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertBankManagement(model);
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

    public int updateBankManagement(BankManagementModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BankManagementDao dao = new BankManagementDao(session);
            ret = dao.updateBankManagement(model);
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
            BankManagementDao dao = new BankManagementDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankManagementDao dao = new BankManagementDao(session);
            return dao.listAvailableBankManagementMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public BankManagementModel getBankManagementDetail(BankManagementModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankManagementDao dao = new BankManagementDao(session);
            return dao.getBankManagement(model);
        } finally {
            session.close();
        }
    }

    public int deleteBankManagement(BankManagementModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BankManagementDao dao = new BankManagementDao(session);
            ret = dao.deleteBankManagement(m);
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

    public List listBankManagement() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BankManagementDao dao = new BankManagementDao(session);
            return dao.listBankManagement();
        } finally {
            session.close();
        }
    }
}