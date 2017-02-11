package com.tenma.auth.util.logon;

import com.tenma.auth.dao.community.CommunityDao;
import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.auth.util.Converter;
import com.tenma.common.bean.TenmaHelper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by gustianom on 08/02/15.
 */
public class CommunityHelper extends TenmaHelper<CommunityHelper> {
    private static CommunityHelper instance;

    public static CommunityHelper use() {
        CommunityHelper helper = new CommunityHelper();
        return helper;
    }

    public static CommunityHelper getInstance() {
        if (instance == null) {
            instance = new CommunityHelper();
        }
        return instance;
    }

    public int insertNewCommunity(CommunityModel model, Locale local, boolean isSubComunity) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewCommunity(session, model, local, isSubComunity);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCommunity(SqlSession session, CommunityModel model, Locale local, boolean isSubCommunity) throws Exception {
        int result = 0;
        CommunityDao dao = new CommunityDao(session);

        int cnt = checkExistingCommunity(model.getCommunityName());
        if (cnt > 0)
            throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
        else {
            String seqId = getNextCommunityId();
            model.setCommunityId(seqId);
            //model.setBasedCurrency("IDR");
            result = dao.insertCommunity(model);
            if (result == 0)
                throw new Exception(AuthConstants.ADD_FAILED);
        }
        return result;
    }

    private String getNextCommunityId() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao communityDao = new CommunityDao(session);
            int seq = communityDao.getNextCommunityId();
            return Converter.getNextSequenceId(seq);
        } finally {
            session.close();
        }
    }

    public int updateCommunity(CommunityModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateCommunity(session, model);
            session.commit();
        } catch (Exception e) {
            System.out.println("e = " + e);
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateCommunity(SqlSession session, CommunityModel model) throws Exception {
        CommunityDao dao = new CommunityDao(session);
        return dao.updateCommunity(model);
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public int countTotalListCommunityAccount(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.getCommunityList(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public List getListForBalanceService(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.getCommunityListForBalanceService(mapList);
        } finally {
            session.close();
        }
    }

    public CommunityModel getCommunityDetail(CommunityModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return getCommunityDetail(session, model);
        } finally {
            session.close();
        }
    }

    public CommunityModel getCommunityDetail(SqlSession session, CommunityModel model) {
        CommunityDao dao = new CommunityDao(session);
        return dao.getCommunityDetail(model);
    }

    public CommunityModel getCommunityDetailByName(String communityName) {
        CommunityModel tmp = new CommunityModel();
        tmp.setCommunityName(communityName);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.getCommunityDetail(tmp);
        } finally {
            session.close();
        }
    }

    public int deleteCommunity(CommunityModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityDao dao = new CommunityDao(session);
            ret = dao.deleteCommunity(model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public List listCommunityItems(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.listCommunityItems(mapList);
        } finally {
            session.close();
        }
    }

    public List listCommunityNativeItems(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            return dao.listCommunityNativeItems(mapList);
        } finally {
            session.close();
        }
    }

    public int checkExistingCommunity(String communityName) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
            HashMap map = new HashMap();
            map.put("communityName", communityName);
            return dao.checkExistingCommunity(map);
        } finally {
            session.close();
        }
    }

    public String getRootMasterCommunityId(String communityId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityDao dao = new CommunityDao(session);
//            HashMap map = new HashMap();
//            map.put("communityName", communityName);

            String communityParent = communityId;
            String finalCommunityId = communityParent;
            while (communityParent != null) {
                finalCommunityId = communityParent;
                communityParent = dao.getRootMasterCommunityId(communityParent);
            }

            return finalCommunityId;
//            return dao.checkExistingCommunity(map);
        } finally {
            session.close();
        }
    }


}