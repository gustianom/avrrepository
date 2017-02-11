package com.tenma.auth.util.db;

import org.apache.ibatis.io.Resources;
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
public class MasterDaoConfig {
    private static final String resource = "xml/postgre/SqlMapConfig.xml";
    private static Properties props = null;

    private static SqlSessionFactory sqlSessionFactory;
    private static MasterDaoConfig sqlFactory;

    private MasterDaoConfig() {

    }

    private static MasterDaoConfig getInstance() throws IOException {
        //System.out.println("");
        //System.out.println("");
        //System.out.println("");
        //System.out.println("");
        //System.out.println("MasterDaoConfig.getInstance");
        Reader reader = null;

        if (sqlFactory == null) {
            //System.out.println("resource = " + resource);
            reader = Resources.getResourceAsReader(resource);
            //System.out.println("reader = " + reader);

            sqlFactory = new MasterDaoConfig();
            sqlFactory.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        }
        return sqlFactory;
    }

    public static synchronized SqlSessionFactory getSessionFactory(String resource) throws IOException {
        if (sqlFactory == null) {
            getInstance();
        }
        return sqlSessionFactory;
    }


    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
