package com.tenma.common.bean.eflyer;

import com.tenma.common.dao.eflyer.EflyerDao;
import com.tenma.common.util.CommonPagingHelper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tenma05 on 02/04/15.
 */
public class EflyerPagingHelper extends EflyerHelper implements CommonPagingHelper {
    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EflyerDao dao = new EflyerDao(session);
            return dao.listAllEflyerWithoutJson(mapList, navigated);
        } finally {
            session.close();
        }
    }
}
