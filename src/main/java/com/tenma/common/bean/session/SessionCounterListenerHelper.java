package com.tenma.common.bean.session;

import com.tenma.common.bean.TenmaDaoHelper;
import com.tenma.common.dao.session.SessionCounterDao;
import com.tenma.common.model.SessionCounterModel;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.tools.util.TenmaConstants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 6/24/13
 * Time: 2:47 PM
 */
public class SessionCounterListenerHelper extends TenmaDaoHelper {
    public int insertNewSessionCounter(SessionCounterModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SessionCounterDao dao = new SessionCounterDao(session);
            result = dao.insertSessionCounter(model);
            if (result == 0)
                throw new Exception(TenmaConstants.ADD_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }


    public SessionCounterModel getSessionCounterDetail(SessionCounterModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SessionCounterDao dao = new SessionCounterDao(session);
            return dao.getSessionCounterDetail(model);
        } finally {
            session.close();
        }
    }

    public List getSessionCounterList(SessionCounterModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            SessionCounterDao dao = new SessionCounterDao(session);
            return dao.getSessionCounterList(model);
        } finally {
            session.close();
        }
    }

    public int remarkDeleteSessionCounter(SessionCounterModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SessionCounterDao dao = new SessionCounterDao(session);
            ret = dao.remarkDeleteSessionCounter(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public int physicalDeleteSession(SessionCounterModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            SessionCounterDao dao = new SessionCounterDao(session);
            ret = dao.physicalDeleteSessionCounter(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    public int physicalDeleteSessionCounterFromFile(List<SessionCounterModel> list) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = physicalDeleteSessionCounterFromFile(session, list);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }


    public int physicalDeleteSessionCounterFromFile(SqlSession session, List<SessionCounterModel> list) throws Exception {
        int ret = 0;
        try {
            for (SessionCounterModel m : list)
                ret = physicalDeleteSessionCounterFromFile(session, m);

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        }
        return ret;

    }


    public int physicalDeleteSession(SqlSession session, SessionCounterModel m) throws Exception {
        int ret = 0;
        try {
            System.out.println("SessionCounterListenerHelper.physicalDeleteSession");
            SessionCounterDao dao = new SessionCounterDao(session);
            ret = dao.physicalDeleteSessionCounter(m);

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        }
        return ret;
    }

    public int physicalDeleteSessionCounterFromFile(SqlSession session, SessionCounterModel m) throws Exception {
        int ret = 0;
        try {
            System.out.println("SessionCounterListenerHelper.physicalDeleteSession");
            SessionCounterDao dao = new SessionCounterDao(session);
            ret = dao.physicalDeleteSessionCounterFromFile(m);

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(TenmaConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(TenmaConstants.DELETE_FAILED, e);
        }
        return ret;
    }


    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        return 0;
    }

    @Override
    public List getListRenderer(HashMap mapList, boolean navigated) throws Exception {
        return null;
    }
}
