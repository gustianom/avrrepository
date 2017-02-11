package com.tenma.auth.util.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:08 AM
 */
public class DaoConfig {
    private static final String resource = "xml/postgre/SqlMapConfig.xml";
    private static Properties props = null;

    private static SqlSessionFactory sqlSessionFactory;
    private static DaoConfig sqlFactory;

    private DaoConfig() {
        System.out.println("Tenma DAO Config - init done");
    }

    private static DaoConfig getInstance() throws IOException {
        if (sqlFactory == null) {
            try {
                Reader reader = Resources.getResourceAsReader(resource);
                sqlFactory = new DaoConfig();
                sqlFactory.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            SqlSession session = sqlSessionFactory.openSession();
            if (session == null) System.out.println("Session is NULL");
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlFactory;
    }

    public static synchronized SqlSessionFactory getSessionFactory() throws IOException {

        if (sqlFactory == null) {
            getInstance();
        }
        return sqlSessionFactory;
    }


    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
