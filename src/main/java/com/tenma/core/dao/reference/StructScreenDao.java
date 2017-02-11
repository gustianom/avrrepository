/**
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 *
 * @deprecated
 */

package com.tenma.core.dao.reference;

import com.tenma.auth.dao.Dao;
import com.tenma.model.core.StructScreenModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class StructScreenDao extends Dao {
    public StructScreenDao(SqlSession session) {
        super(session);
    }

    public int insertScreen(StructScreenModel menu) {
        return session.insert("insertCoreStructScreen", menu);
    }

    public int deleteScreen(StructScreenModel menu) {
        return session.delete("deleteCoreStructScreen", menu);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreStructScreen", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getScreenList(HashMap parameterObject, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "listCoreStructScreenMap";
        else
            param = "listAllCoreStructScreenMap";
        return session.selectList(param, parameterObject);
    }

    public List getQueryAvailableCoreStructScreen(HashMap mapList, boolean includeNavigation) {
        String param = "";
        if (includeNavigation)
            param = "getQueryAvailableCoreStructScreen";
        else
            param = "getQueryAllAvailableCoreStructScreen";
        return session.selectList(param, mapList);
    }

    public int countQueryAvailableCoreStructScreen(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countQueryAvailableCoreStructScreen", parameterObject);
        return count == null ? 0 : count.intValue();
    }
}
