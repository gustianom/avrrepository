/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.auth.dao;

import com.tenma.common.dao.TenmaDao;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:27 PM
 */
public abstract class Dao extends TenmaDao {

    public Dao(SqlSession session) {
        super(session);
    }
}