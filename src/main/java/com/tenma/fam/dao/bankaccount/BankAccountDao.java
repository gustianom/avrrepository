package com.tenma.fam.dao.bankaccount;

import com.tenma.auth.dao.Dao;
import com.tenma.model.fam.BankAccountModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Mon Mar 31 13:44:01 ICT 2014
 */
public class BankAccountDao extends Dao {
    public BankAccountDao(SqlSession session) {
        super(session);
    }

    public BankAccountModel getBankAccount(BankAccountModel model) {
        return (BankAccountModel) session.selectOne("getBankAccount", model);
    }

    public int insertBankAccount(BankAccountModel systemProperty) {
        int result = 0;
        result = session.insert("insertBankAccount", systemProperty);
        return result;
    }

    public int updateBankAccount(BankAccountModel systemProperty) {
        int result = 0;
        result = session.update("updateBankAccount", systemProperty);
        return result;
    }

    public int deleteBankAccount(BankAccountModel systemProperty) {
        int result = 0;
        result = session.delete("deleteBankAccount", systemProperty);
        return result;
    }


    public List listBankAccount() {
        List list = session.selectList("listBankAccount", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalBankAccount", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<BankAccountModel> listAvailableBankAccountMap(HashMap parameterObject, boolean includeNavigation) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("BankAccountDao.listAvailableBankAccountMap");
        if (includeNavigation)
            return session.selectList("listBankAccountMap", parameterObject);
        else
            return session.selectList("listAllBankAccountMap", parameterObject);
    }
}
