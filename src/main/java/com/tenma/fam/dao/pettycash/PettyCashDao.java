package com.tenma.fam.dao.pettycash;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.PettyCashModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generated on Mon Jun 17 19:22:20 ICT 2013
 */
public class PettyCashDao extends Dao {
    public PettyCashDao(SqlSession session) {
        super(session);
    }

    public PettyCashModel getPettycash(PettyCashModel model) {
        return (PettyCashModel) session.selectOne("getPettycash", model);
    }

    public int insertPettycash(PettyCashModel pettyCashModel) {
        int result = 0;
        result = session.insert("insertPettycash", pettyCashModel);
        return result;
    }

    public int updatePettycash(PettyCashModel pettyCashModel) {
        int result = 0;
        result = session.update("updatePettycash", pettyCashModel);

        return result;
    }

    public int deletePettycash(PettyCashModel pettyCashModel) {
        int result = 0;
        result = session.delete("deletePettycash", pettyCashModel);
        return result;
    }

    public int deleteVoucherPettycash(HashMap map) {
        int result = 0;
        result = session.delete("deleteVoucherPettycash", map);
        return result;
    }

    public List listPettycash() {
        List list = session.selectList("listPettycash", null);
        return list;
    }


    public List getPattyCashClossing(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listPettyCashClossingMap", parameterObject);
        } else {
            list = session.selectList("listAllPettyCashClossingMap", parameterObject);
        }
        return list;
    }

    public List getPattyCashVoucher(HashMap parameterObject, boolean includeNavigation) {
        List list = null;
        if (includeNavigation) {
            list = session.selectList("listPettyCashVoucherMap", parameterObject);
        } else {
            list = session.selectList("listAllPettyCashVoucherMap", parameterObject);
        }
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalPettycash", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public Integer maxPettyCash(PettyCashModel m) {
        Integer count = (Integer) session.selectOne("pettyCashMax", m);
        return count == null ? 0 : count.intValue();
    }

    public List<PettyCashModel> listAvailablePettycashMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listPettycashMap", parameterObject);
        else
            return session.selectList("listAllPettycashMap", parameterObject);
    }

    public List<PettyCashModel> listAvailablePettycashVoucherMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listPettycashVoucherMap", parameterObject);
        else
            return session.selectList("listAllPettycashVoucherMap", parameterObject);
    }


}
