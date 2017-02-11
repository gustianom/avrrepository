package com.tenma.fam.dao.bank_management;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.BankManagementModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2014. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.2
 * Generated on Mon Sep 08 10:13:58 WIB 2014
 */
public class BankManagementDao extends Dao {
    public BankManagementDao(SqlSession session) {
        super(session);
    }

    public BankManagementModel getBankManagement(BankManagementModel model) {
        return (BankManagementModel) session.selectOne("getBankManagement", model);
    }

    public int insertBankManagement(BankManagementModel systemProperty) {
        int result = 0;
        result = session.insert("insertBankManagement", systemProperty);
        return result;
    }

    public int updateBankManagement(BankManagementModel systemProperty) {
        int result = 0;
        result = session.update("updateBankManagement", systemProperty);
        return result;
    }

    public int deleteBankManagement(BankManagementModel systemProperty) {
        int result = 0;
        result = session.delete("deleteBankManagement", systemProperty);
        return result;
    }


    public List listBankManagement() {
        List list = session.selectList("listBankManagement", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalBankManagement", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<BankManagementModel> listAvailableBankManagementMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listBankManagementMap", parameterObject);
        else
            return session.selectList("listAllBankManagementMap", parameterObject);
    }
}
