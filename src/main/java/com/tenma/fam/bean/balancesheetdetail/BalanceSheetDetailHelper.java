package com.tenma.fam.bean.balancesheetdetail;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.fam.dao.balancesheetdetail.BalanceSheetDetailDao;
import com.tenma.model.fam.BalanceSheetDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 13:42:23 WIB 2016
 */
public class BalanceSheetDetailHelper extends TenmaDaoHelper {
    public int insertNewBalanceSheetDetail(BalanceSheetDetailModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            BalanceSheetDetailModel grpModel = new BalanceSheetDetailModel();
            grpModel.setCommunityId(model.getCommunityId());
            grpModel.setAccountId(model.getAccountId());
            grpModel.setBalanceSheetId(model.getBalanceSheetId());
            grpModel = dao.getBalanceSheetDetail(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertBalanceSheetDetail(model);
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

    public int updateBalanceSheetDetail(BalanceSheetDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            ret = dao.updateBalanceSheetDetail(model);
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
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            return dao.listAvailableBalanceSheetDetailMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public BalanceSheetDetailModel getBalanceSheetDetailDetail(BalanceSheetDetailModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            return dao.getBalanceSheetDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteBalanceSheetDetail(BalanceSheetDetailModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            ret = dao.deleteBalanceSheetDetail(m);
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

    public List listBalanceSheetDetail() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BalanceSheetDetailDao dao = new BalanceSheetDetailDao(session);
            return dao.listBalanceSheetDetail();
        } finally {
            session.close();
        }
    }
}