package com.tenma.common.bean.emailtype;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.emailtype.EmailTypeDao;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.TenmaConverter;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.model.common.EmailTypeModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 5/27/13
 * Time: 9:38 AM
 */
public class EmailTypeHelper extends TenmaHelper implements CommonPagingHelper {
    public int insertNewEmailTypeList(EmailTypeModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            EmailTypeModel m = new EmailTypeModel();
            m.setEmailType(model.getEmailType());
            EmailTypeModel grpModel = dao.getEmailTypeDetail(m);
            if (grpModel != null)
                throw new Exception(Constants.ADD_ALREADY_EXIST);
            else {
                int seq = dao.getEmailTypeSequence();
                model.setEmailId(TenmaConverter.getNextSequenceId(seq));
                result = dao.insertEmailType(model);
                session.commit();
                if (result == 0)
                    throw new Exception(Constants.ADD_FAILED);
            }
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateEmailTypeList(EmailTypeModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            ret = dao.updateEmailType(model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            throw new Exception(Constants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    @Override
    public List<EmailTypeModel> getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<EmailTypeModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            return dao.getEmailTypeList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public EmailTypeModel getEmailTypeDetail(EmailTypeModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            return dao.getEmailTypeDetail(model);
        } finally {
            session.close();
        }
    }

    public int deleteEmailTypeList(EmailTypeModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            EmailTypeDao dao = new EmailTypeDao(session);
            ret = dao.deleteEmailType(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(Constants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(Constants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;
    }
}

