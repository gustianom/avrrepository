package com.tenma.common.bean.community;

import com.tenma.auth.dao.community.CommunityMemberDao;
import com.tenma.auth.model.CommunityMemberModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.util.CommonPagingHelper;
import com.tenma.common.util.error.ErrorInfo;
import com.tenma.common.util.sequence.SequenceTool;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Version 1.0.
 * Generated on Fri Feb 21 16:03:48 ICT 2014
 */
public class CommunityMemberHelper extends TenmaHelper<CommunityMemberHelper> implements CommonPagingHelper {

    public static CommunityMemberHelper use() {
        CommunityMemberHelper helper = new CommunityMemberHelper();
        return helper;
    }

    public int getMemberType(String communityId, Integer memberId) {
        int rs = 0;
        CommunityMemberModel acmm = new CommunityMemberModel();
        acmm.setCommunityId(communityId);
        acmm.setMemberId(memberId);
        CommunityMemberModel cmm = getCommunityMemberDetail(acmm);
        if (cmm == null)

        {
            rs = AuthConstants.MEMBER_ROLE_TYPE.COMMON.getValue();
        } else

        {
            System.out.println(cmm.getMemberType());
            rs = cmm.getMemberType();
        }
        return rs;
    }


    public int getMemberType(SqlSession session, CommunityMemberModel model) {
        int rs = 0;
        CommunityMemberModel cmm = getCommunityMemberDetail(session, model);
        if (cmm == null) {
            rs = AuthConstants.MEMBER_ROLE_TYPE.COMMON.getValue();
        } else {
            System.out.println(cmm.getMemberType());
            rs = cmm.getMemberType();
        }
        return rs;

    }

    public int insertNewCommunityMember(CommunityMemberModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewCommunityMember(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewCommunityMember(SqlSession session, CommunityMemberModel model) throws Exception {
        int result = 0;
        CommunityMemberDao dao = new CommunityMemberDao(session);
        CommunityMemberModel grpModel = new CommunityMemberModel();
        SequenceTool tools = SequenceTool.getInstance();
        grpModel.setCommunityId(model.getCommunityId());
        grpModel.setMemberId(model.getMemberId());
        grpModel = dao.getCommunityMember(grpModel);
        if (grpModel != null)
            throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
        else {
            if (model.getMemberGroup() == null) model.setMemberGroup(0); // avoid NPL set the default membergroup 0
            result = dao.insertCommunityMember(model);
            if (result == 0)
                throw new Exception(AuthConstants.ADD_FAILED);
        }
        return result;
    }

    public int updateCommunityMember(CommunityMemberModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateCommunityMember(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateCommunityMember(SqlSession session, CommunityMemberModel model) throws Exception {
        int ret = 0;
        CommunityMemberDao dao = new CommunityMemberDao(session);
        ret = dao.updateCommunityMember(model);
        return ret;
    }

    public int updateMemberType(CommunityMemberModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateMemberType(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }


    public int updateMemberType(SqlSession session, CommunityMemberModel model) throws Exception {
        int ret = 0;
        CommunityMemberDao dao = new CommunityMemberDao(session);
        ret = dao.updateCommunityMemberType(model);
        return ret;
    }


    public int countTotalActiveList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.countTotalActiveList(mapList);
        } finally {
            session.close();
        }
    }

    public int countTotalNewActiveList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.countTotalNewActiveList(mapList);
        } finally {
            session.close();
        }
    }

    public List listAllNewMemberActive(HashMap map, boolean all) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.listCommunityNewestMemberMap(map, all);
        } finally {
            session.close();
        }
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List<CommunityMemberModel> getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.listAvailableCommunityMemberMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public CommunityMemberModel getCommunityMemberDetail(CommunityMemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            return dao.getCommunityMember(model);
        } finally {
            session.close();
        }
    }

    public CommunityMemberModel getCommunityMemberDetail(SqlSession session, CommunityMemberModel model) {
        CommunityMemberDao dao = new CommunityMemberDao(session);
        return dao.getCommunityMember(model);
    }

    public int deleteCommunityMember(CommunityMemberModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            CommunityMemberDao dao = new CommunityMemberDao(session);
            ret = dao.deleteCommunityMember(m);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            if (e.getCause().toString().toUpperCase().indexOf(ErrorInfo.VIOLATES_FOREIGN_KEY_CONTRAINT.toUpperCase()) != -1)
                throw new Exception(AuthConstants.DELETE_FAILED_HAVE_REFERENCE, e);
            else
                throw new Exception(AuthConstants.DELETE_FAILED, e);
        } finally {
            session.close();
        }
        return ret;

    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }
}