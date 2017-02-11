package com.tenma.mail.dao.undeliveredmail;

import com.tenma.auth.dao.Dao;
import com.tenma.model.email.UndeliveredMailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2015. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.5.beta.1
 * Generated on Thu Mar 05 10:41:17 WIB 2015
 */
public class UndeliveredMailDao extends Dao {
    public UndeliveredMailDao(SqlSession session) {
        super(session);
    }

    public UndeliveredMailModel getUndeliveredMail(UndeliveredMailModel model) {
        return (UndeliveredMailModel) session.selectOne("getCOREUndeliveredMail", model);
    }

    public int insertUndeliveredMail(UndeliveredMailModel systemProperty) {
        int result = 0;
        result = session.insert("insertCOREUndeliveredMail", systemProperty);
        return result;
    }

    public int updateUndeliveredMail(UndeliveredMailModel systemProperty) {
        int result = 0;
        result = session.update("updateCOREUndeliveredMail", systemProperty);
        return result;
    }

    public int deleteUndeliveredMail(UndeliveredMailModel systemProperty) {
        int result = 0;
        result = session.delete("deleteCOREUndeliveredMail", systemProperty);
        return result;
    }


    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalCOREUndeliveredMail", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List<UndeliveredMailModel> listAvailableUndeliveredMailMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listCOREUndeliveredMailMap", parameterObject);
        else
            return session.selectList("listAllCOREUndeliveredMailMap", parameterObject);
    }
}
