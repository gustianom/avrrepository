package com.tenma.fam.dao.pettycash;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.PettycashCycleModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generated on Tue Jun 18 11:48:02 ICT 2013
 */
public class PettycashCycleDao extends Dao {
    public PettycashCycleDao(SqlSession session) {
        super(session);
    }

    public PettycashCycleModel getPettycashCycle(PettycashCycleModel model) {
        return (PettycashCycleModel) session.selectOne("getPettycashCycle", model);
    }

    public int insertPettycashCycle(PettycashCycleModel petCycleModel) {
        int result = 0;
        result = session.insert("insertPettycashCycle", petCycleModel);
        return result;
    }

    public int updatePettycashCycle(PettycashCycleModel petCycleModel) {
        int result = 0;
        result = session.update("updatePettycashCycle", petCycleModel);

        return result;
    }

    public int deletePettycashCycle(PettycashCycleModel petCycleModel) {
        int result = 0;
        result = session.delete("deletePettycashCycle", petCycleModel);
        return result;
    }


    public List listPettycashCycle() {
        List list = session.selectList("listPettycashCycle", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalPettycashCycle", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public int countCurrencyList(PettycashCycleModel model) {
        Integer count = (Integer) session.selectOne("countCurrencyPettycashCycle", model);
        return count == null ? 0 : count.intValue();
    }


    public List<PettycashCycleModel> listAvailablePettycashCycleMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listPettycashCycleMap", parameterObject);
        else
            return session.selectList("listAllPettycashCycleMap", parameterObject);
    }

    public List listPettyCurrencyItem(PettycashCycleModel pettycashCycleModel) {
        List list = session.selectList("listPettyCashCurrencyItems", pettycashCycleModel);
        return list;
    }

    public List listCurrencyActiveItem(PettycashCycleModel pettycashCycleModel) {
        List list = session.selectList("listSelectCurrencyActive", pettycashCycleModel);
        return list;
    }

    public String getCurrencyDefault(PettycashCycleModel pettycashCycleModel) {
        return (String) session.selectOne("selectCurrencyDefault", pettycashCycleModel);
    }

    public String getCurrencyActiveDefault(PettycashCycleModel pettycashCycleModel) {
        return (String) session.selectOne("selectCurrencyActiveDefault", pettycashCycleModel);
    }
}
