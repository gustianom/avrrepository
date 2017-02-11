package com.tenma.common.bean.country;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.country.CountryDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.CountryModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Jul 27 11:39:09 WIB 2015
 */
public class CountryHelper extends TenmaHelper {
    public int insertNewCountry(CountryModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CountryDao dao = new CountryDao(session);
            CountryModel grpModel = new CountryModel();
            grpModel.setCountryId(model.getCountryId());
            grpModel = dao.getCountry(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertCountry(model);
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

    public int updateCountry(CountryModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CountryDao dao = new CountryDao(session);
            ret = dao.updateCountry(model);
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
            CountryDao dao = new CountryDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CountryDao dao = new CountryDao(session);
            return dao.listAvailableCountryMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CountryModel getCountryDetail(CountryModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CountryDao dao = new CountryDao(session);
            return dao.getCountry(model);
        } finally {
            session.close();
        }
    }

    public int deleteCountry(CountryModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CountryDao dao = new CountryDao(session);
            ret = dao.deleteCountry(m);
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

    public List listCountry() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CountryDao dao = new CountryDao(session);
            return dao.listCountry();
        } finally {
            session.close();
        }
    }
}