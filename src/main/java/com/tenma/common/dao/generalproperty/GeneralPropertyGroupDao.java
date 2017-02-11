/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.generalproperty;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.GeneralPropertyGroupModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:10 AM
 */
public class GeneralPropertyGroupDao extends TenmaDao {
    public GeneralPropertyGroupDao(SqlSession session) {
        super(session);
    }

    public GeneralPropertyGroupModel getGeneralPropertyGroupDetail(GeneralPropertyGroupModel model) {
        return (GeneralPropertyGroupModel) session.selectOne("getGeneralPropertyGroup", model);
    }

    public int insertGeneralPropertyGroup(GeneralPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.insert("insertGeneralPropertyGroup", appGrpModel);
        return result;
    }

    public int updateGeneralPropertyGroup(GeneralPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.update("updateGeneralPropertyGroup", appGrpModel);

        return result;
    }

    public int deleteGeneralPropertyGroup(GeneralPropertyGroupModel appGrpModel) {
        int result = 0;
        result = session.delete("deleteGeneralPropertyGroup", appGrpModel);
        return result;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalGeneralPropertyGroup", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List listGeneralPropertyGroupItems() {
        List list = session.selectList("listGeneralPropertyGroupItems", null);
        return list;
    }

    public List<GeneralPropertyGroupModel> listAvailableGeneralPropertyGroupMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listGeneralPropertyGroupMap", parameterObject);
        else
            return session.selectList("listAllGeneralPropertyGroupMap", parameterObject);
    }

    public int getGeneralPropertyGroupSequence() {
        Integer sid = (Integer) session.selectOne("getGeneralPropertyGroupSequence");
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }
}
