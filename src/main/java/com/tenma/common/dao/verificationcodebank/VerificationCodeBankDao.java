package com.tenma.common.dao.verificationcodebank;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.VerificationCodeBankModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Wed Jul 24 15:22:16 ICT 2013
 */
public class VerificationCodeBankDao extends Dao {
    public VerificationCodeBankDao(SqlSession session) {
        super(session);
    }

    @Override
    public int countTotalList(HashMap map) {
        return 0;
    }

    public VerificationCodeBankModel getVerificationCodeBank(VerificationCodeBankModel verificationCode) {
        return (VerificationCodeBankModel) session.selectOne("getVerificationCodeBank", verificationCode);
    }

    public int insertVerificationCodeBank(VerificationCodeBankModel verificationCode) {
        int result = 0;
        result = session.insert("insertVerificationCodeBank", verificationCode);
        return result;
    }

    public int deleteVerificationCodeBank(VerificationCodeBankModel verificationCode) {
        int result = 0;
        result = session.delete("deleteVerificationCodeBank", verificationCode);
        return result;
    }
}
