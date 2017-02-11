/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.applicationproperty;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.ApplicationPropertyGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:10 AM
 */
public class ApplicationPropertyGroupDao extends TenmaDao {
    public ApplicationPropertyGroupDao(SqlSession session) {
        super(session);
    }

    public ApplicationPropertyGroupModel getApplicationPropertyGroupDetail(ApplicationPropertyGroupModel model) {
        return (ApplicationPropertyGroupModel) session.selectOne("getApplicationPropertyGroup", model);
    }

    public int insertApplicationPropertyGroup(ApplicationPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.insert("insertApplicationPropertyGroup", appGrpModel);
        return result;
    }

    public int updateApplicationPropertyGroup(ApplicationPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.update("updateApplicationPropertyGroup", appGrpModel);
        return result;
    }

    public int deleteApplicationPropertyGroup(ApplicationPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.delete("deleteApplicationPropertyGroup", appGrpModel);
        return result;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalApplicationPropertyGroup", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List listApplicationPropertyGroupItems() {
        List list = session.selectList("listApplicationPropertyGroupItems", null);
        return list;
    }

    public List<ApplicationPropertyGroupModel> listAvailableApplicationPropertyGroupMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listApplicationPropertyGroupMap", parameterObject);
        else
            return session.selectList("listAllApplicationPropertyGroupMap", parameterObject);
    }
}
