/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.currency;


import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.currency.CurrencyDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.CurrencyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:06 AM
 */
public class CurrencyHelper extends TenmaHelper {
    public int insertNewCurrency(CurrencyModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CurrencyDao dao = new CurrencyDao(session);

            CurrencyModel grpModel = new CurrencyModel();
            grpModel.setCurrencyId(model.getCurrencyId());

            grpModel = dao.getCurrencyDetail(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertCurrency(model);
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

    public int updateCurrency(CurrencyModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CurrencyDao dao = new CurrencyDao(session);
            ret = dao.updateCurrency(model);
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
            CurrencyDao dao = new CurrencyDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CurrencyDao dao = new CurrencyDao(session);
            return dao.getCurrencyList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CurrencyModel getCurrencyDetail(CurrencyModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CurrencyDao dao = new CurrencyDao(session);
            CurrencyModel m = new CurrencyModel();
            m.setCurrencyId(model.getCurrencyId());
            return dao.getCurrencyDetail(m);
        } finally {
            session.close();
        }
    }

    public int deleteCurrency(CurrencyModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CurrencyDao dao = new CurrencyDao(session);
            ret = dao.deleteCurrency(m);
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

    public List listCurrencyItems() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CurrencyDao dao = new CurrencyDao(session);
            return dao.listCurrencyItem();
        } finally {
            session.close();
        }
    }
}