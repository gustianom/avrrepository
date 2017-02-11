/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.fam.dao.saldoaccount;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.ReportModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 12/12/12
 * Time: 3:10 pM
 */
public class SaldoAccountReportDao extends Dao {
    public SaldoAccountReportDao(SqlSession session) {
        super(session);
    }

    public ReportModel getReportDetail(ReportModel model) {
        ReportModel m = null;
        m = (ReportModel) session.selectOne("getReport", model);
        return m;
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalReport", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List getListRender(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listReportMap", parameterObject);
        else
            return session.selectList("listAllReportMap", parameterObject);
    }

    public List getSaldoAccountGroupReportList(HashMap parameterObject) {
        return session.selectList("listSaldoAccountGroupReport", parameterObject);

    }

    public List getSaldoAccountReportList(HashMap parameterObject) {
        return session.selectList("listSaldoAccountReport", parameterObject);

    }


}
