package com.tenma.common.bean.structurelabel;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.structurelabel.StructureLabelDao;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
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
public class StructureLabelHelper extends TenmaHelper {
    public int insertNewStructureLabel(StructureLabelModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            StructureLabelModel grpModel = new StructureLabelModel();
            grpModel.setStructId(model.getStructId());
            grpModel = dao.getStructureLabel(grpModel);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                result = dao.insertStructureLabel(model);
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
                session.commit();
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateStructureLabel(StructureLabelModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            ret = dao.updateStructureLabel(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            return dao.listAvailableStructureLabelMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public StructureLabelModel getStructureLabelDetail(StructureLabelModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            return dao.getStructureLabel(model);
        } finally {
            session.close();
        }
    }

    public int deleteStructureLabel(StructureLabelModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            ret = dao.deleteStructureLabel(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listStructureLabel() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructureLabelDao dao = new StructureLabelDao(session);
            return dao.listStructureLabel();
        } finally {
            session.close();
        }
    }
}