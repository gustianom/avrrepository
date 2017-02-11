package com.tenma.fam.bean.balancesheet;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.fam.dao.balancesheet.BalanceSheetDao;
import com.tenma.model.fam.BalanceSheetModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 10:49:49 WIB 2016
 */
public class BalanceSheetHelper extends TenmaDaoHelper {
    public int insertNewBalanceSheet(BalanceSheetModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            BalanceSheetModel grpModel = new BalanceSheetModel();
            grpModel.setCommunityId(model.getCommunityId());
            grpModel.setBalanceSheetName(model.getCommunityId().toLowerCase());
            grpModel = dao.getBalanceSheet(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertBalanceSheet(model);
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

    public int updateBalanceSheet(BalanceSheetModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            ret = dao.updateBalanceSheet(model);
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
            BalanceSheetDao dao = new BalanceSheetDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            return dao.listAvailableBalanceSheetMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public BalanceSheetModel getBalanceSheetDetail(BalanceSheetModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            return dao.getBalanceSheet(model);
        } finally {
            session.close();
        }
    }

    public int deleteBalanceSheet(BalanceSheetModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            ret = dao.deleteBalanceSheet(m);
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

    public List listBalanceSheet() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDao dao = new BalanceSheetDao(session);
            return dao.listBalanceSheet();
        } finally {
            session.close();
        }
    }
}