/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.applicationproperty;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.ApplicationPropertyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:47 PM
 */
public class ApplicationPropertyDao extends TenmaDao {
    public ApplicationPropertyDao(SqlSession session) {
        super(session);
    }

    public ApplicationPropertyModel getApplicationPropertyDetail(ApplicationPropertyModel model) {
        return (ApplicationPropertyModel) session.selectOne("getApplicationProperty", model);
    }

    public int insertApplicationProperty(ApplicationPropertyModel applicationProperty) {
        int result = 0;
        result = session.insert("insertApplicationProperty", applicationProperty);
        return result;
    }

    public int updateApplicationProperty(ApplicationPropertyModel applicationProperty) {
        int result = 0;
        result = session.update("updateApplicationProperty", applicationProperty);
        return result;
    }

    public int deleteApplicationProperty(ApplicationPropertyModel applicationProperty) {
        int result = 0;
        result = session.delete("deleteApplicationProperty", applicationProperty);
        return result;
    }


    public List listApplicationProperty() {
        List list = session.selectList("listApplicationProperty", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalApplicationProperty", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getApplicationPropertyByResponsibilityId(Object parameterObject) {
        List list = session.selectList("applicationPropertyByResponsibilityId", parameterObject);
        return list;
    }

    public List<ApplicationPropertyModel> listAvailableApplicationPropertyMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listApplicationPropertyMap", parameterObject);
        else
            return session.selectList("listAllApplicationPropertyMap", parameterObject);
    }

    /**
     * @param groupName
     * @return
     */
    public List listApplicationPropertyItemsByGroup(String groupName) {
        HashMap map = new HashMap();
        map.put("appGrpCd", groupName);
        List list = session.selectList("listApplicationPropertyItemsByGroup", map);
        return list;
    }
}