package com.tenma.fam.bean.template;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.fam.dao.template.TransactionTemplaterDetailDao;
import com.tenma.model.fam.TransactionTemplateDetailModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TransactionTemplateDetailHelper extends TenmaHelper {
    public int insertNewTemplateDetail(TransactionTemplateDetailModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);

            int seq = dao.getTemplateDetailSequence();
            result = dao.insertTemplateDetail(model);
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

    public int updateTemplateDetail(TransactionTemplateDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);
            ret = dao.updateTemplateDetail(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);
            return dao.getTemplateDetailList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public TransactionTemplateDetailModel getTemplateDetailDetail(TransactionTemplateDetailModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);
            return dao.getTemplateDetailDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteTemplateDetail(TransactionTemplateDetailModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            TransactionTemplaterDetailDao dao = new TransactionTemplaterDetailDao(session);


            logger.info("model = " + model);
            ret = dao.deleteTemplateDetail(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }
}




