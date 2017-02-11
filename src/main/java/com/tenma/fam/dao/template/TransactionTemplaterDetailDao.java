/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */
package com.tenma.fam.dao.template;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TransactionTemplaterDetailDao extends Dao {
    public TransactionTemplaterDetailDao(SqlSession session) {
        super(session);
    }

    public TransactionTemplateDetailModel getTemplateDetailDetail(TransactionTemplateDetailModel templateDetail) {
        return (TransactionTemplateDetailModel) session.selectOne("getTemplateDetailDetail", templateDetail);
    }

    public int insertTemplateDetail(TransactionTemplateDetailModel templateDetail) {
        return session.insert("insertTemplateDetail", templateDetail);
    }

    public int updateTemplateDetail(TransactionTemplateDetailModel templateDetail) {

        return session.update("updateTemplateDetail", templateDetail);
    }

    public int deleteTemplateDetail(TransactionTemplateDetailModel templateDetail) {
        return session.update("deleteTemplateDetail", templateDetail);
    }

    public int softDeleteTemplateDetail(TransactionTemplateDetailModel templateDetail) {
        return session.update("softDeleteTemplateDetail", templateDetail);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTemplateDetail", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getTemplateDetailList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listTemplateDetailMap", parameterObject);
        } else {
            list = session.selectList("listAllTemplateDetailMap", parameterObject);
        }
        return list;
    }

    public int countTemplateDetailReference(int templateDetailId) {
        Integer count = (Integer) session.selectOne("countTemplateDetailReference", new Integer(templateDetailId));
        return count == null ? 0 : count.intValue();
    }

    public int countTemplateDetailChild(int templateDetailId) {
        Integer count = (Integer) session.selectOne("countTemplateDetailChild", new Integer(templateDetailId));
        return count == null ? 0 : count.intValue();
    }

    public int getTemplateDetailSequence() {
        Integer sid = (Integer) session.selectOne("getTemplateDetailSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public Double getCountAmount(String templateHeaderNumber) {
        Double inNext;
        Double sid = (Double) session.selectOne("countAmount", templateHeaderNumber);
        inNext = sid.doubleValue();

        return inNext;
    }


}




