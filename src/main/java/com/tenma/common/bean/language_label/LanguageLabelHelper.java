package com.tenma.common.bean.language_label;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.language_label.LanguageLabelDao;
import com.tenma.common.model.LanguageLabelModel;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:42:31 ICT 2013
 */
public class LanguageLabelHelper extends TenmaDaoHelper implements CommonPagingHelper {
    public int insertNewLanguageLabel(LanguageLabelModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewLanguageLabel(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewLanguageLabel(SqlSession session, LanguageLabelModel model) throws Exception {
        int result = 0;
        LanguageLabelDao dao = new LanguageLabelDao(session);
        LanguageLabelModel grpModel = new LanguageLabelModel();
        grpModel.setLabelName(model.getLabelName());
        grpModel = dao.getLanguageLabel(grpModel);
        if (grpModel != null) {
            if (grpModel.getRecordStatus() == 0)
                throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
            else {
                model.setLabelId(grpModel.getLabelId());
                model.setRecordStatus(0); // update as active
                result = dao.updateLanguageLabel(model);
                if (result == 0)
                    throw new Exception(TenmaConstants.ADD_FAILED);
            }
        } else {
            int inext = dao.getLanguageLabelSequence();
            model.setLabelId(inext);
            result = dao.insertLanguageLabel(model);
            if (result == 0)
                throw new Exception(TenmaConstants.ADD_FAILED);
        }
        return result;
    }

    public int updateLanguageLabel(LanguageLabelModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateLanguageLabel(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateLanguageLabel(SqlSession session, LanguageLabelModel model) throws Exception {
        int ret = 0;
        LanguageLabelDao dao = new LanguageLabelDao(session);
        ret = dao.updateLanguageLabel(model);
        return ret;
    }

    @Override
    public List getCustomListRenderer(HashMap hashMap, boolean navigated) {
        return this.getListRenderer(hashMap, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            return dao.listAvailableLanguageLabelMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public LanguageLabelModel getLanguageLabelDetail(LanguageLabelModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            return dao.getLanguageLabel(model);
        } finally {
            session.close();
        }
    }

    public int deleteLanguageLabel(LanguageLabelModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            ret = dao.deleteLanguageLabel(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public List listLanguageLabel() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            return dao.listLanguageLabel();
        } finally {
            session.close();
        }
    }

    public LanguageLabelModel getLanguageLabelUnFlagStatus(LanguageLabelModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguageLabelDao dao = new LanguageLabelDao(session);
            return dao.getLanguageLabelUnFlagStatus(model);
        } finally {
            session.close();
        }
    }
}