package com.tenma.core.bean.reference;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.core.dao.reference.StructMenuDao;
import com.tenma.model.core.MenuGroupModel;
import com.tenma.model.core.StructMenuModel;
import com.tenma.common.util.Constants;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustianom on 8/7/14.
 */
public class StructMenuHelper extends TenmaHelper implements CommonPagingHelper {
    public int insertNewCoreStructMenu(StructMenuModel m) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructMenuDao dao = new StructMenuDao(session);
            result = dao.insertMenu(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(Constants.ADD_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCoreStructMenu(String structId, List<StructMenuModel> listSelected) throws Exception {
        int result = 0;

        HashMap map = new HashMap();
        map.put("structId", structId);
        List<StructMenuModel> lsOld = getListRenderer(map, false);

        HashMap mapSelected = new HashMap();

        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructMenuDao dao = new StructMenuDao(session);
            if (listSelected != null) {
                for (StructMenuModel m : listSelected) {
                    m.setMenuStatus(true);
                    m.setRecordStatus(0);
                    result = dao.updateMenu(m);
                    if (result == 0)
                        result = dao.insertMenu(m);

                    mapSelected.put(m.getMenuId(), m);
                }
            }

            if (lsOld != null && lsOld.size() != 0)
                for (StructMenuModel m : lsOld)
                    if (!mapSelected.containsKey(m.getMenuId()))
                        dao.deleteMenu(m);

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

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructMenuDao dao = new StructMenuDao(session);
            if (mapList.containsKey("AVAILABLEMENUPURPOSE"))
                return dao.countQueryAvailableCoreStructMenu(mapList);
            else
                return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<StructMenuModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructMenuDao dao = new StructMenuDao(session);
            if (mapList.containsKey("AVAILABLEMENUPURPOSE"))
                return dao.getQueryAvailableCoreStructMenu(mapList, navigated);
            else
                return dao.getMenuList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public int deleteCoreStructMenu(StructMenuModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            StructMenuDao dao = new StructMenuDao(session);
            ret = dao.deleteMenu(m);
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

    public List<MenuGroupModel> getMenuGroupCategory(HashMap parameter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            StructMenuDao dao = new StructMenuDao(session);
            return dao.getMenuGroupList(parameter);
        } finally {
            session.close();
        }
    }

}
