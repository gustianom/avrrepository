/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.bean.menu;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.menu.FunctionDao;
import com.tenma.model.core.FunctionModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:26 AM
 */
public class FunctionHelper extends TenmaHelper {
    public int insertNewFunction(FunctionModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            FunctionDao dao = new FunctionDao(session);
            FunctionModel m = new FunctionModel();
            m.setFunctionName(model.getFunctionName());
            m.setMenuId(model.getMenuId());
            FunctionModel grpModel = dao.getFunctionDetail(m);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertFunction(model);
                session.commit();
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateFunction(FunctionModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            FunctionDao dao = new FunctionDao(session);
            ret = dao.updateFunction(model);
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
            FunctionDao dao = new FunctionDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            FunctionDao dao = new FunctionDao(session);
            return dao.getFunctionList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public FunctionModel getFunctionDetail(FunctionModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            FunctionDao dao = new FunctionDao(session);
            return dao.getFunctionDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteFunction(FunctionModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            FunctionDao dao = new FunctionDao(session);
            ret = dao.deleteFunction(m);
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

    public List listFunctionItems() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            FunctionDao dao = new FunctionDao(session);
            return dao.listFunctionItems();
        } finally {
            session.close();
        }
    }
}