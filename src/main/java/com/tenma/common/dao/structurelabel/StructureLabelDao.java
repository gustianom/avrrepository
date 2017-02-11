package com.tenma.common.dao.structurelabel;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.StructureLabelModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Tue Mar 04 08:59:18 ICT 2014
 */
public class StructureLabelDao extends Dao {
    public StructureLabelDao(SqlSession session) {
        super(session);
    }

    public StructureLabelModel getStructureLabel(StructureLabelModel model) {
        return (StructureLabelModel) session.selectOne("getStructureLabel", model);
    }

    public int insertStructureLabel(StructureLabelModel systemProperty) {
        int result = 0;
        result = session.insert("insertStructureLabel", systemProperty);
        return result;
    }

    public int updateStructureLabel(StructureLabelModel systemProperty) {
        int result = 0;
        result = session.update("updateStructureLabel", systemProperty);
        return result;
    }

    public int deleteStructureLabel(StructureLabelModel systemProperty) {
        int result = 0;
        result = session.delete("deleteStructureLabel", systemProperty);
        return result;
    }


    public List listStructureLabel() {
        List list = session.selectList("listStructureLabel", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalStructureLabel", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<StructureLabelModel> listAvailableStructureLabelMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listStructureLabelMap", parameterObject);
        else
            return session.selectList("listAllStructureLabelMap", parameterObject);
    }
}
