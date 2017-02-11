/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.audittrail;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.audittrail.AuditTrailDao;
import com.tenma.model.common.AuditTrailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:25 PM
 */
public class AuditTrailHelper extends TenmaHelper {

    public int insertNewAuditTrail(AuditTrailModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            result = dao.insertAuditTrail(model);
            if (result == 0)
                throw new Exception("Failed to add system property");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }


    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public AuditTrailModel getAuditTrailDetail(AuditTrailModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.getAuditTrail(model.getAuditId());
        } finally {
            session.close();
        }
    }


    public List listAuditTrail() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.listAuditTrail();
        } finally {
            session.close();
        }
    }

    public List listTotalAuditTrail() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.listTotalAuditTrail();
        } finally {
            session.close();
        }
    }

    public List listTotalUserHits() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.listTotalUserHits();
        } finally {
            session.close();
        }
    }

    public List listTotalMenuHits() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuditTrailDao dao = new AuditTrailDao(session);
            return dao.listTotalMenuHits();
        } finally {
            session.close();
        }
    }

    @Override
    /**
     * Method ini harus di override untuk support paging
     */
    public List getListRenderer(HashMap mapList, boolean navigated) throws Exception {
        throw new UnsupportedOperationException("You must override this method on your helper class");
    }
}
