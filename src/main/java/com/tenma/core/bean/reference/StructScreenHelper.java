package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.Constants;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.reference.StructScreenDao;
import com.tenma.model.core.StructScreenModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 8/7/14.
 */
public class StructScreenHelper extends TenmaHelper {
    public int insertNewCoreStructScreen(StructScreenModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            result = dao.insertScreen(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCoreStructScreen(String structId, List<StructScreenModel> listSelected) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            StructScreenModel tmp = new StructScreenModel();
            tmp.setStructId(structId);

            dao.deleteScreen(tmp);
            if (listSelected != null) {
                for (int i = 0; i < listSelected.size(); i++) {
                    StructScreenModel m = listSelected.get(i);
                    result = dao.insertScreen(m);
                }
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            return dao.getScreenList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public int deleteCoreStructScreen(StructScreenModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            StructScreenDao dao = new StructScreenDao(session);
            ret = dao.deleteScreen(m);
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

    public List getQueryAvailableCoreStructScreen(HashMap mapList, boolean includeNavigation) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            return dao.getQueryAvailableCoreStructScreen(mapList, includeNavigation);
        } finally {
            session.close();
        }
    }

    public int countQueryAvailableCoreStructScreen(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructScreenDao dao = new StructScreenDao(session);
            return dao.countQueryAvailableCoreStructScreen(mapList);
        } finally {
            session.close();
        }
    }
}
