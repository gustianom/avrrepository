package com.tenma.common.dao.language_label;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.LanguageLabelModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:42:31 ICT 2013
 */
public class LanguageLabelDao extends TenmaDao {
    public LanguageLabelDao(SqlSession session) {
        super(session);
    }

    public LanguageLabelModel getLanguageLabel(LanguageLabelModel model) {
        return (LanguageLabelModel) session.selectOne("getLanguageLabel", model);
    }

    public int insertLanguageLabel(LanguageLabelModel systemProperty) {
        int result = 0;
        result = session.insert("insertLanguageLabel", systemProperty);
        return result;
    }

    public int updateLanguageLabel(LanguageLabelModel systemProperty) {
        int result = 0;
        result = session.update("updateLanguageLabel", systemProperty);

        return result;
    }

    public int deleteLanguageLabel(LanguageLabelModel systemProperty) {
        int result = 0;
        result = session.delete("deleteLanguageLabel", systemProperty);
        return result;
    }


    public List listLanguageLabel() {
        List list = session.selectList("listLanguageLabel", null);
        return list;
    }

    public LanguageLabelModel getLanguageLabelUnFlagStatus(LanguageLabelModel model) {
        return (LanguageLabelModel) session.selectOne("queryLanguageLabelUnFlagStatus", model);
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalLanguageLabel", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<LanguageLabelModel> listAvailableLanguageLabelMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listLanguageLabelMap", parameterObject);
        else
            return session.selectList("listAllLanguageLabelMap", parameterObject);
    }

    public int getLanguageLabelSequence() {
        Integer sid = (Integer) session.selectOne("getLanguageLabelSequence");
        int inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }
}
