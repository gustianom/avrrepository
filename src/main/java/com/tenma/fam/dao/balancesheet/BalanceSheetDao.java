package com.tenma.fam.dao.balancesheet;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.BalanceSheetModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 10:49:49 WIB 2016
 */
public class BalanceSheetDao extends Dao {
    public BalanceSheetDao(SqlSession session) {
        super(session);
    }

    public BalanceSheetModel getBalanceSheet(BalanceSheetModel model) {
        return (BalanceSheetModel) session.selectOne("getFAMBalanceSheet", model);
    }

    public int insertBalanceSheet(BalanceSheetModel systemProperty) {
        int result = 0;
        result = session.insert("insertFAMBalanceSheet", systemProperty);
        return result;
    }

    public int updateBalanceSheet(BalanceSheetModel systemProperty) {
        int result = 0;
        result = session.update("updateFAMBalanceSheet", systemProperty);
        return result;
    }

    public int deleteBalanceSheet(BalanceSheetModel systemProperty) {
        int result = 0;
        result = session.delete("deleteFAMBalanceSheet", systemProperty);
        return result;
    }


    public List listBalanceSheet() {
        List list = session.selectList("listFAMBalanceSheet", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalFAMBalanceSheet", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<BalanceSheetModel> listAvailableBalanceSheetMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listFAMBalanceSheetMap", parameterObject);
        else
            return session.selectList("listAllFAMBalanceSheetMap", parameterObject);
    }
}
