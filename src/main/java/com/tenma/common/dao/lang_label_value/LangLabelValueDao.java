package com.tenma.common.dao.lang_label_value;

import com.tenma.common.dao.TenmaDao;
import com.tenma.common.model.LangLabelValueModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Mon Jul 08 21:46:31 ICT 2013
 */
public class LangLabelValueDao extends TenmaDao {
    public LangLabelValueDao(SqlSession session) {
        super(session);
    }

    public LangLabelValueModel getLangLabelValue(LangLabelValueModel model) {
        return (LangLabelValueModel) session.selectOne("getLangLabelValue", model);
    }

    public int insertLangLabelValue(LangLabelValueModel systemProperty) {
        int result = 0;
        result = session.insert("insertLangLabelValue", systemProperty);
        return result;
    }

    public int updateLangLabelValue(LangLabelValueModel systemProperty) {
        int result = 0;
        result = session.update("updateLangLabelValue", systemProperty);

        return result;
    }

    public int deleteLangLabelValue(LangLabelValueModel systemProperty) {
        int result = 0;
        result = session.delete("deleteLangLabelValue", systemProperty);
        return result;
    }


    public List listLangLabelValue() {
        List list = session.selectList("listLangLabelValue", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalLangLabelValue", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<LangLabelValueModel> listAvailableLangLabelValueMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listLangLabelValueMap", parameterObject);
        else
            return session.selectList("listAllLangLabelValueMap", parameterObject);
    }


    public List<LangLabelValueModel> getLangLabelValueDetailByCommunity(LangLabelValueModel model) {
        List<LangLabelValueModel> list = session.selectList("getLangLabelValueDetailByCommunity", model);
        return list;
    }
}
