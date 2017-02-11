package com.tenma.common.dao.country;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.CountryModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Mon Jul 27 11:39:09 WIB 2015
 */
public class CountryDao extends Dao {
    public CountryDao(SqlSession session) {
        super(session);
    }

    public CountryModel getCountry(CountryModel model) {
        return (CountryModel) session.selectOne("getPUBLICCountry", model);
    }

    public int insertCountry(CountryModel systemProperty) {
        int result = 0;
        result = session.insert("insertPUBLICCountry", systemProperty);
        return result;
    }

    public int updateCountry(CountryModel systemProperty) {
        int result = 0;
        result = session.update("updatePUBLICCountry", systemProperty);
        return result;
    }

    public int deleteCountry(CountryModel systemProperty) {
        int result = 0;
        result = session.delete("deletePUBLICCountry", systemProperty);
        return result;
    }


    public List listCountry() {
        List list = session.selectList("listPUBLICCountry", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalPUBLICCountry", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<CountryModel> listAvailableCountryMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listPUBLICCountryMap", parameterObject);
        else
            return session.selectList("listAllPUBLICCountryMap", parameterObject);
    }
}
