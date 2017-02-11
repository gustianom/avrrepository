package com.tenma.auth.util.db;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:16 PM
 */
public abstract class DaoHelper {
    public SqlSessionFactory sqlSessionFactory;

    public DaoHelper() {
        try {
            sqlSessionFactory = DaoConfig.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * retreiving number of rows according to filter assigned
     *
     * @param mapList
     * @return
     * @throws Exception
     */
    public abstract int countTotalList(HashMap mapList) throws Exception;

    /**
     * retriving list a
     *
     * @param mapList
     * @param navigated
     * @return
     * @throws Exception
     */
    public abstract List getListRenderer(HashMap mapList, boolean navigated) throws Exception;
}

