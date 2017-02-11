
package com.tenma.auth.dao.screen;


import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.ScreenModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Wed Jun 25 16:22:23 WIB 2014
 */

public class ScreenDao extends Dao {
    public ScreenDao(SqlSession session) {
        super(session);
    }

    public ScreenModel getScreen(ScreenModel model) {
        return (ScreenModel) session.selectOne("getCoreScreen", model);
    }

    public int insertScreen(ScreenModel screenModel) {
        int result = 0;
        result = session.insert("insertCoreScreen", screenModel);
        return result;
    }

    public int updateScreen(ScreenModel screenModel) {
        int result = 0;
        result = session.update("updateCoreScreen", screenModel);
        return result;
    }

    public int deleteScreen(ScreenModel screenModel) {
        int result = 0;
        result = session.delete("deleteCoreScreen", screenModel);
        return result;
    }


    public List listScreen() {
        List list = session.selectList("listCoreScreen", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCoreScreen", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<ScreenModel> listAvailableScreenMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCoreScreenMap", parameterObject);
        else
            return session.selectList("listAllCoreScreenMap", parameterObject);
    }
}
