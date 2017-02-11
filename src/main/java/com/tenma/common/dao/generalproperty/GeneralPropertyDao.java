/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.generalproperty;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.GeneralPropertyModel;
import com.tenma.common.util.web.Items;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:47 PM
 */
public class GeneralPropertyDao extends TenmaDao {
    public GeneralPropertyDao(SqlSession session) {
        super(session);
    }

    public GeneralPropertyModel getGeneralPropertyDetail(GeneralPropertyModel model) {
        return (GeneralPropertyModel) session.selectOne("getGeneralProperty", model);
    }

    public int insertGeneralProperty(GeneralPropertyModel applicationProperty) {
        int result = 0;
        result = session.insert("insertGeneralProperty", applicationProperty);
        return result;
    }

    public int updateGeneralProperty(GeneralPropertyModel applicationProperty) {
        int result = 0;
        result = session.update("updateGeneralProperty", applicationProperty);

        return result;
    }

    public int deleteGeneralProperty(GeneralPropertyModel applicationProperty) {
        int result = 0;
        result = session.delete("deleteGeneralProperty", applicationProperty);
        return result;
    }


    public List listGeneralProperty() {
        List list = session.selectList("listGeneralProperty", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalGeneralProperty", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getGeneralPropertyByResponsibilityId(Object parameterObject) {
        List list = session.selectList("applicationPropertyByResponsibilityId", parameterObject);
        return list;
    }

    public List<GeneralPropertyModel> listAvailableGeneralPropertyMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listGeneralPropertyMap", parameterObject);
        else
            return session.selectList("listAllGeneralPropertyMap", parameterObject);
    }

    /**
     * @param groupName
     * @return
     */
    public List<Items> listGeneralPropertyItemsByGroup(String groupName) {
        HashMap map = new HashMap();
        map.put("groupCode", groupName);
        List list = session.selectList("listGeneralPropertyItemsByGroup", map);
        return list;
    }


    public int getGeneralPropertySequence() {
        Integer sid = (Integer) session.selectOne("getGeneralPropertySequence");
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }
}