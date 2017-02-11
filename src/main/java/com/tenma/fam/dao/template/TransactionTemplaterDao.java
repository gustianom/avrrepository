/*
 * Copyright (c) 2014. PT Tenma Bright Sky
 * Creted by PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 2/19/14
 * Time: 3:10 PM
 */
package com.tenma.fam.dao.template;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.TransactionTemplateModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TransactionTemplaterDao extends Dao {
    public TransactionTemplaterDao(SqlSession session) {
        super(session);
    }

    public TransactionTemplateModel getTemplateHeaderDetail(TransactionTemplateModel templateHeader) {
        return (TransactionTemplateModel) session.selectOne("getTemplateHeaderDetail", templateHeader);
    }

    public int insertTemplateHeader(TransactionTemplateModel templateHeader) {
        return session.insert("insertTemplateHeader", templateHeader);
    }

    public int updateTemplateHeader(TransactionTemplateModel templateHeader) {

        return session.update("updateTemplateHeader", templateHeader);
    }

    public int deleteTemplateHeader(TransactionTemplateModel templateHeader) {
        return session.update("deleteTemplateHeader", templateHeader);
    }

    public int softDeleteTemplateHeader(TransactionTemplateModel templateHeader) {
        return session.update("softDeleteTemplateHeader", templateHeader);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalTemplateHeader", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getTemplateHeaderList(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listTemplateHeaderMap", parameterObject);
        } else {
            list = session.selectList("listAllTemplateHeaderMap", parameterObject);
        }
        return list;
    }

    public int countTemplateHeaderReference(int templateHeaderId) {
        Integer count = (Integer) session.selectOne("countTemplateHeaderReference", new Integer(templateHeaderId));
        return count == null ? 0 : count.intValue();
    }

    public int countTemplateHeaderChild(int templateHeaderId) {
        Integer count = (Integer) session.selectOne("countTemplateHeaderChild", new Integer(templateHeaderId));
        return count == null ? 0 : count.intValue();
    }

    public int getTemplateHeaderSequence() {
        Integer sid = (Integer) session.selectOne("getTemplateHeaderSequence", new Object());
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }


}




