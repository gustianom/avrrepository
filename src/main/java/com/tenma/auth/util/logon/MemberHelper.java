package com.tenma.auth.util.logon;


import com.tenma.auth.dao.member.MemberDao;
import com.tenma.auth.model.MemberModel;
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
 * Generator Version 1.1.
 * Generated on Fri Apr 25 16:21:32 WIB 2014
 */
public class MemberHelper extends TenmaHelper<MemberHelper> implements CommonPagingHelper {
    private Integer memberId;

    public static MemberHelper use() {
        MemberHelper helper = new MemberHelper();
        return helper;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public int insertNewMember(MemberModel model) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewMember(session, model);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            session.close();
        }
        return result;
    }

    public int insertNewMember(SqlSession session, MemberModel model) throws Exception {
        int result = 0;

        boolean isEmailExist = false;
        boolean isPhoneExist = false;

        MemberDao dao = new MemberDao(session);

        int countField = 0;
        if (model.getEmailAddress() != null && model.getEmailAddress().trim().length() != 0)
            countField++;
        else
            model.setEmailAddress(null);

        if (model.getMobilePhone() != null && model.getMobilePhone().trim().length() != 0)
            countField++;
        else
            model.setMobilePhone(null);

        if (countField == 0) {
            System.out.println("****------------------ Phone or EMAIL must not empty------------------- **");
            throw new Exception(AuthConstants.ADD_FAILED_FIELD_PHONE_OR_EMAIL_MUSTNOT_EMPTY);
        } else {
            if (model.getEmailAddress() != null && model.getEmailAddress().trim().length() != 0) {
                String emailAddress = model.getEmailAddress().toLowerCase();
                model.setEmailAddress(emailAddress);
                MemberModel grpModel = new MemberModel();
                grpModel.setEmailAddress(model.getEmailAddress()); //check email
                grpModel = dao.getMember(grpModel);
                if (grpModel != null)
                    isEmailExist = true;
            }

            if (!isEmailExist && model.getMobilePhone() != null && model.getMobilePhone().trim().length() != 0) {
                MemberModel grpModel = new MemberModel();
                grpModel.setMobilePhone(model.getMobilePhone()); //check phone
                grpModel = dao.getMember(grpModel);
                if (grpModel != null)
                    isPhoneExist = true;
            }


            if (!isEmailExist && !isPhoneExist) {
//                int seq = dao.getMemberSequence();
//              counter member change from logic select max() into M_SEQUENCE
//              logic select max() will be not working if we cleanup database since the physical record is purging
//              in case of migration with specific counter format include KEYWORD is not workable using selext max()
                int seq = SequenceTool.getInstance().getNewCounter("", "MBR", false);
                model.setMemberId(seq);
//                System.out.println("seq = " + seq);
//                System.out.println("PROFILE PICTURE LENGTH = " + model.getProfilePicture() == null ? "NULL" : model.getProfilePicture().length());
                result = dao.insertMember(model);
                if (result == 0)
                    throw new Exception(AuthConstants.ADD_FAILED);
                else memberId = seq;
            } else
                throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
        }
        return result;
    }

    public int updateRegisterMember(MemberModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateRegisterMember(session, model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateRegisterMember(SqlSession session, MemberModel model) throws Exception {
        int ret = 0;
        MemberDao dao = new MemberDao(session);
        ret = dao.updateMember(model);
        return ret;
    }

    public int updateMember(MemberModel model) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updateMember(session, model);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            if (e.getMessage().contains(AuthConstants.ADD_ALREADY_EXIST))
                throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
            else
                throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return ret;
    }

    public int updateMember(SqlSession session, MemberModel model) throws Exception {
        int ret = 0;
        boolean isContinue = false;
        MemberDao dao = new MemberDao(session);

        if (model.getEmailAddress() != null || model.getMobilePhone() != null) {
            if (model.getEmailAddress() != null && model.getEmailAddress().trim().length() != 0) {
                MemberModel grpModel = new MemberModel();
                grpModel.setEmailAddress(model.getEmailAddress()); //check email
                grpModel = dao.getMember(grpModel);
                if (grpModel != null && !model.getMemberId().equals(grpModel.getMemberId()))
                    throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
                else
                    isContinue = true;
            }
            if (isContinue && model.getMobilePhone() != null && model.getMobilePhone().trim().length() != 0) {
                MemberModel grpModel = new MemberModel();
                grpModel.setMobilePhone(model.getMobilePhone()); //check phone
                grpModel = dao.getMember(grpModel);
                if (grpModel != null && !model.getMemberId().equals(grpModel.getMemberId()))
                    throw new Exception(AuthConstants.ADD_ALREADY_EXIST);
                else
                    isContinue = true;
            }
        } else
            isContinue = true;

        if (isContinue)
            ret = dao.updateMember(model);
        return ret;
    }

    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            return dao.listAvailableMemberMap(mapList, navigated);
        } finally {
            session.close();
        }
    }

    public MemberModel getMemberDetail(MemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            return dao.getMember(model);
        } finally {
            session.close();
        }
    }

    public String getMemberName(MemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            String st = dao.getMemberName(model);
            if (st == null)
                st = "-";
            return st;
        } finally {
            session.close();
        }
    }

    public String getMemberProfile(MemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            return dao.getMemberProfile(model);
        } finally {
            session.close();
        }
    }

    public int updateJustProfile(MemberModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            int st = dao.updateJustProfile(model);
            session.commit();
            return st;
        } finally {
            session.close();
        }
    }

    public int deleteMember(MemberModel m) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            MemberDao dao = new MemberDao(session);
            ret = dao.deleteMember(m);
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

    public List listMember() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            MemberDao dao = new MemberDao(session);
            return dao.listMember();
        } finally {
            session.close();
        }
    }

    @Override
    public List getCustomListRenderer(HashMap mapList, boolean navigated) {
        return getListRenderer(mapList, navigated);
    }

    public int insertNewPassiveMember(SqlSession session, String memberName, String profilePicture, String createdFrom, String createdBy) throws Exception {
        int memberId = -1;
        MemberModel model = new MemberModel();
        int seq = SequenceTool.getInstance().getNewCounter("", "MBR", false);
        model.setMemberId(seq);
        model.setMemberName(memberName);
        model.setProfilePicture(profilePicture);
        model.setCreatedBy(createdBy);
        model.setCreatedFrom(createdFrom);
        model.setUpdatedBy(createdBy);
        model.setUpdatedFrom(createdFrom);
        MemberDao dao = new MemberDao(session);
        int result = dao.insertMember(model);
        if (result > 0) {
            memberId = seq;
        }
        return memberId;
    }

    public int insertNewPassiveMember(String memberName, String profilePicture, String createdFrom, String createdBy) throws Exception {
        int result = -1;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = insertNewPassiveMember(session, memberName, profilePicture, createdFrom, createdBy);
            session.commit();
            System.out.println("---- INSERT NEW PASSIVE MEMBER : " + memberName + " ---- ( memberId=" + result + ")");
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
