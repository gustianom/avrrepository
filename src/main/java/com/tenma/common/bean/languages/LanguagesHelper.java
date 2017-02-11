package com.tenma.common.bean.languages;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.languages.LanguagesDao;
import com.tenma.common.model.LanguagesModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:26 ICT 2013
 */
public class LanguagesHelper extends TenmaDaoHelper {
    public int insertNewLanguages(LanguagesModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguagesDao dao = new LanguagesDao(session);
            LanguagesModel grpModel = new LanguagesModel();
            grpModel.setLangCode(model.getLangCode());
            grpModel.setRecordStatus(-1);
            grpModel = dao.getLanguages(grpModel);

            if (grpModel != null) {
                if (grpModel.getRecordStatus() == 0)
                    throw new Exception(TenmaConstants.ADD_ALREADY_EXIST);
                else {
                    model.setRecordStatus(0);
                    result = updateLanguages(model);
                }
            } else {
                result = dao.insertLanguages(model);
                if (result == 0)
                    throw new Exception(TenmaConstants.ADD_FAILED);
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateLanguages(LanguagesModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            LanguagesDao dao = new LanguagesDao(session);
            ret = dao.updateLanguages(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(TenmaConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguagesDao dao = new LanguagesDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }


    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguagesDao dao = new LanguagesDao(session);
            List ls = null;
            LanguageCache cache = LanguageCache.getInstance();

            if (!cache.isCache(mapList) || navigated) {
                ls = dao.listAvailableLanguagesMap(mapList, navigated);
                cache.putcache(ls, mapList);
            } else {
                ls = cache.listAvailableLanguagesMap();
            }
            return ls;
        } finally {
            session.close();
        }
    }

    public LanguagesModel getLanguagesDetail(LanguagesModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguagesDao dao = new LanguagesDao(session);
            return dao.getLanguages(model);
        } finally {
            session.close();
        }
    }

    public int deleteLanguages(LanguagesModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            LanguagesDao dao = new LanguagesDao(session);
            ret = dao.deleteLanguages(m);
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

    public List listLanguages() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            LanguagesDao dao = new LanguagesDao(session);
            return dao.listLanguages();
        } finally {
            session.close();
        }
    }
}