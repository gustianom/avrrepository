package com.tenma.common.dao;


import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:27 PM
 */
public abstract class TenmaDao {
    public SqlSession session;

    public TenmaDao(SqlSession session) {
        setSession(session);
    }

    public SqlSession getSession() {
        return session;
    }

    private void setSession(SqlSession session) {
        this.session = session;
    }

/*
    public PackageLimitModel getSecurePackageLimitation(String schemaName, String tableName)
    {
        PackageLimitModel m = new PackageLimitModel();
        m.setSchemaName(schemaName);
        m.setTableName(tableName);
        return (PackageLimitModel) session.selectOne("getPackageLimitationObject",m);
    }

*/

    /**
     * Counting Total record of List
     * <br/>When the value of result is -1, it means the record is empty
     *
     * @param map
     * @return
     */
    public abstract int countTotalList(HashMap map);
}