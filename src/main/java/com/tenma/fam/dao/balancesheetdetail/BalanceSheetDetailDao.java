package com.tenma.fam.dao.balancesheetdetail;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.BalanceSheetDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2016. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Jan 07 13:42:23 WIB 2016
 */
public class BalanceSheetDetailDao extends Dao {
    public BalanceSheetDetailDao(SqlSession session) {
        super(session);
    }

    public BalanceSheetDetailModel getBalanceSheetDetail(BalanceSheetDetailModel model) {
        return (BalanceSheetDetailModel) session.selectOne("getFAMBalanceSheetDetail", model);
    }

    public int insertBalanceSheetDetail(BalanceSheetDetailModel systemProperty) {
        int result = 0;
        result = session.insert("insertFAMBalanceSheetDetail", systemProperty);
        return result;
    }

    public int updateBalanceSheetDetail(BalanceSheetDetailModel systemProperty) {
        int result = 0;
        result = session.update("updateFAMBalanceSheetDetail", systemProperty);
        return result;
    }

    public int deleteBalanceSheetDetail(BalanceSheetDetailModel systemProperty) {
        int result = 0;
        result = session.delete("deleteFAMBalanceSheetDetail", systemProperty);
        return result;
    }


    public List listBalanceSheetDetail() {
        List list = session.selectList("listFAMBalanceSheetDetail", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalFAMBalanceSheetDetail", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<BalanceSheetDetailModel> listAvailableBalanceSheetDetailMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listFAMBalanceSheetDetailMap", parameterObject);
        else
            return session.selectList("listAllFAMBalanceSheetDetailMap", parameterObject);
    }
}
