/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.core.dao.menu;

import com.tenma.auth.dao.Dao;
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
public class FunctionDao extends Dao {
    public FunctionDao(SqlSession session) {
        super(session);
    }

    public FunctionModel getFunctionDetail(FunctionModel function) {
        return (FunctionModel) session.selectOne("getCoreFunctionDetail", function);
    }

    public int insertFunction(FunctionModel function) {
        return session.insert("insertCoreFunction", function);
    }

    public int updateFunction(FunctionModel function) {

        return session.update("updateCoreFunction", function);
    }

    public int deleteFunction(FunctionModel function) {
        return session.delete("deleteCoreFunction", function);
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("getCoreFunctionCount", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getFunctionList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("getCoreFunctionList", parameterObject);
        } else {
            list = session.selectList("getAllCoreFunctionList", parameterObject);
        }
        return list;
    }

    public List listFunctionItems() {
        return session.selectList("listCoreFunctionItems", null);
    }


}

