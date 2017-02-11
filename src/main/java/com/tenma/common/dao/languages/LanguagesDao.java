package com.tenma.common.dao.languages;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.LanguagesModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 19:32:26 ICT 2013
 */
public class LanguagesDao extends TenmaDao {
    public LanguagesDao(SqlSession session) {
        super(session);
    }

    public LanguagesModel getLanguages(LanguagesModel model) {
        return (LanguagesModel) session.selectOne("getLanguages", model);
    }

    public int insertLanguages(LanguagesModel model) {
        int result = 0;
        result = session.insert("insertLanguages", model);
        return result;
    }

    public int updateLanguages(LanguagesModel model) {
        int result = 0;
        result = session.update("updateLanguages", model);

        return result;
    }

    public int deleteLanguages(LanguagesModel model) {
        int result = 0;
        model.setRecordStatus(1);
        result = session.delete("deleteLanguages", model);
        return result;
    }


    public List listLanguages() {
        List list = session.selectList("listLanguages", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalLanguages", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<LanguagesModel> listAvailableLanguagesMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listLanguagesMap", parameterObject);
        else
            return session.selectList("listAllLanguagesMap", parameterObject);
    }
}
