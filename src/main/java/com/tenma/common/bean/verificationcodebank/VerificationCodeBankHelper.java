package com.tenma.common.bean.verificationcodebank;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.verificationcodebank.VerificationCodeBankDao;
import com.tenma.common.util.Constants;
import com.tenma.model.common.VerificationCodeBankModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jul 24 15:22:16 ICT 2013
 */
public class VerificationCodeBankHelper extends TenmaHelper {
    public int insertNewVerificationCodeBank(SqlSession session, VerificationCodeBankModel model) throws Exception {
        int result = 0;
        VerificationCodeBankDao dao = new VerificationCodeBankDao(session);

        VerificationCodeBankModel grpModel = dao.getVerificationCodeBank(model);
        if (grpModel != null)
            throw new Exception(Constants.ADD_ALREADY_EXIST);
        else {
            result = dao.insertVerificationCodeBank(model);
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);
        }
        return result;
    }

    public int insertNewVerificationCodeBank(VerificationCodeBankModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewVerificationCodeBank(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            VerificationCodeBankDao dao = new VerificationCodeBankDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    @Override
    public List getListRenderer(HashMap mapList, boolean navigated) throws Exception {
        //Method ini harus di override untuk support paging
        throw new UnsupportedOperationException("You must override this method on your helper class");

    }

    public VerificationCodeBankModel getVerificationCodeBankDetail(VerificationCodeBankModel verificationCode) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            VerificationCodeBankDao dao = new VerificationCodeBankDao(session);
            return dao.getVerificationCodeBank(verificationCode);
        } finally {
            session.close();
        }
    }

    public int deleteVerificationCodeBank(VerificationCodeBankModel verificationCode) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = deleteVerificationCodeBank(session, verificationCode);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public int deleteVerificationCodeBank(SqlSession session, VerificationCodeBankModel verificationCode) throws Exception {
        int ret = 0;
        VerificationCodeBankDao dao = new VerificationCodeBankDao(session);
        ret = dao.deleteVerificationCodeBank(verificationCode);
        return ret;

    }
}