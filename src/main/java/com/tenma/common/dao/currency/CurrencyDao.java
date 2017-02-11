/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.dao.currency;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.CurrencyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:10 AM
 */

public class CurrencyDao extends Dao {
    public CurrencyDao(SqlSession session) {
        super(session);
    }

    public CurrencyModel getCurrencyDetail(CurrencyModel model) {
        CurrencyModel m = null;
        m = (CurrencyModel) session.selectOne("getCurrencyObject", model);
        return m;
    }

    public int insertCurrency(CurrencyModel currency) {
        int result = 0;
        result = session.insert("insertCurrency", currency);
        return result;
    }

    public int updateCurrency(CurrencyModel currency) {
        int result = 0;
        result = session.update("updateCurrency", currency);

        return result;
    }

    public int deleteCurrency(CurrencyModel currency) {
        int result = 0;
        result = session.delete("deleteCurrency", currency);
        return result;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("getCurrencyCount", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List getCurrencyList(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("getCurrencyList", parameterObject);
        else
            return session.selectList("getAllCurrencyList", parameterObject);
    }

    public List listCurrencyItem() {
        List list = session.selectList("listCurrencyItems", null);
        return list;
    }

}
