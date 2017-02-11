package com.tenma.fam.bean.saldoaccount;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.fam.dao.saldoaccount.SaldoAccountReportDao;
import com.tenma.model.fam.SaldoAccountReportModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class SaldoAccountReportHelper extends TenmaDaoHelper {


    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SaldoAccountReportDao dao = new SaldoAccountReportDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }


    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SaldoAccountReportDao dao = new SaldoAccountReportDao(session);
            return dao.getListRender(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public List<SaldoAccountReportModel> getSaldoAccountGroupReportList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SaldoAccountReportDao dao = new SaldoAccountReportDao(session);
            return dao.getSaldoAccountGroupReportList(mapList);
        } finally {
            session.close();
        }
    }

    public List<SaldoAccountReportModel> getSaldoAccountReportList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SaldoAccountReportDao dao = new SaldoAccountReportDao(session);
            return dao.getSaldoAccountReportList(mapList);
        } finally {
            session.close();
        }
    }


}