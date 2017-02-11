/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.systemproperty;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.SystemPropertyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 4:00 PM
 */
public class SystemPropertyDao extends TenmaDao {
    public SystemPropertyDao(SqlSession session) {
        super(session);
    }

    public SystemPropertyModel getSystemProperty(SystemPropertyModel model) {
        return (SystemPropertyModel) session.selectOne("getSystemProperty", model);
    }

    public int insertSystemProperty(SystemPropertyModel systemProperty) {
        int result = 0;
        result = session.insert("insertSystemProperty", systemProperty);
        return result;
    }

    public int updateSystemProperty(SystemPropertyModel systemProperty) {
        int result = 0;
        result = session.update("updateSystemProperty", systemProperty);

        return result;
    }

    public int deleteSystemProperty(SystemPropertyModel systemProperty) {
        int result = 0;
        result = session.delete("deleteSystemProperty", systemProperty);
        return result;
    }


    public List listSystemProperty() {
        List list = session.selectList("listSystemProperty", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalSystemProperty", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<SystemPropertyModel> listAvailableSystemPropertyMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listSystemPropertyMap", parameterObject);
        else
            return session.selectList("listAllSystemPropertyMap", parameterObject);
    }
}
