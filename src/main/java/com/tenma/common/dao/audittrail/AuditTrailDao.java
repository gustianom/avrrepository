/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.audittrail;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.AuditTrailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:26 PM
 */
public class AuditTrailDao extends Dao {
    public AuditTrailDao(SqlSession session) {
        super(session);
    }

    public AuditTrailModel getAuditTrail(String auditId) {
        AuditTrailModel model = null;
        model = (AuditTrailModel) session.selectOne("getAuditTrail", auditId);
        return model;
    }

    public int insertAuditTrail(AuditTrailModel auditmodel) {
        int result = 0;
        result = session.insert("insertAuditTrail", auditmodel);
        return result;
    }

    public List listAuditTrail() {
        List list = session.selectList("listAuditTrail", null);
        return list;
    }

    public List listTotalAuditTrail() {
        List list = session.selectList("listTotalAuditTrail", null);
        return list;
    }

    public List listTotalUserHits() {
        List list = session.selectList("listTotalUserHits", null);
        return list;
    }

    public List listTotalMenuHits() {
        List list = session.selectList("listTotalMenuHits", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalAuditTrail", parameterObject);
        return count == null ? 0 : count.intValue();
    }


}
