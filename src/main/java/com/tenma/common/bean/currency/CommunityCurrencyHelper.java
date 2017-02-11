package com.tenma.common.bean.currency;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.currency.CommunityCurrencyDao;
import com.tenma.common.util.Constants;
import com.tenma.model.common.CommunityCurrencyModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * User: sigit
 * Date: 01/04/13
 * Time: 11:08 AM
 */
public class CommunityCurrencyHelper extends TenmaHelper {


    public int insertNewRefCommunityCurrency(String communityId, String createdBy, String createdFromIpAddress, List<String> listOfCurrency) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            insertNewRefCommunityCurrency(session, communityId, createdBy, createdFromIpAddress, listOfCurrency);
            session.commit();
            if (result == 0)
                throw new Exception(Constants.ADD_FAILED);

        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewRefCommunityCurrency(SqlSession session, String communityId, String createdBy, String createdFromIpAddress, List<String> listOfCurrency) throws Exception {
        CommunityCurrencyModel model = new CommunityCurrencyModel();
        model.setCreatedFrom(createdFromIpAddress);
        model.setUpdatedFrom(createdFromIpAddress);
        model.setCreatedBy(createdBy);
        model.setUpdatedBy(createdBy);
        model.setCommunityId(communityId);

        deleteAllCommunityCurrency(session, model);

        int result = 0;
        CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
        for (int a = 0; a < listOfCurrency.size(); a++) {
            model.setCurrencyId(listOfCurrency.get(a));
            result += dao.insertRefCommunityCurrency(model);
        }

        return result;
    }

    private int deleteAllCommunityCurrency(CommunityCurrencyModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
            ret = dao.deleteAllRefCommunityCurrency(model);
            logger.info("\n\n\n res " + ret);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    private int deleteAllCommunityCurrency(SqlSession session, CommunityCurrencyModel model) throws Exception {
        int ret = 0;
        CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
        ret = dao.deleteAllRefCommunityCurrency(model);
        return ret;
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
            return dao.getRefCommunityCurrencyList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CommunityCurrencyModel getCommunityCurrencyDetail(CommunityCurrencyModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
            return dao.getRefCommunityCurrencyDetail(model);
        } finally {
            session.close();
        }

    }

    public List listCurrencyCommunityItems(String communityId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityCurrencyDao dao = new CommunityCurrencyDao(session);
            return dao.listCommunityCurrencyItem(communityId);
        } finally {
            session.close();
        }
    }

    @Override
    /**
     * Method ini harus di override untuk support paging
     */
    public int countTotalList(HashMap mapList) throws Exception {
        throw new UnsupportedOperationException("You must override this method on your helper class");
    }

}



