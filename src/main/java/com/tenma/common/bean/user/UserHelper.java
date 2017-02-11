/*
 * Copyright (c) 2012. PT TENMA BRIGHT SKY. ALL Right Reserved.
 */

package com.tenma.common.bean.user;

import com.tenma.auth.dao.member.MemberDao;
import com.tenma.auth.dao.user.AuthDao;
import com.tenma.auth.dao.user.UserDao;
import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.auth.util.Converter;
import com.tenma.auth.util.logon.TenmaAuth;
import com.tenma.auth.util.logon.TenmaEncrypt;
import com.tenma.common.bean.TenmaHelper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 2:56 PM
 */
public class UserHelper extends TenmaHelper<UserHelper> {
    private String userId;

    public static UserHelper use() {
        UserHelper helper = new UserHelper();
        return helper;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * saving user tenma from admin user apps, the system generate new userId and automatically register current user as a member of Tenma Community
     *
     * @param session              the session must be closed by opener
     * @param user
     * @param authenticationSource
     * @param isAdmin
     * @return
     * @throws Exception
     */
    public int insertTenmaSystemUser(SqlSession session, UserModel user, String authenticationSource, boolean isAdmin) throws Exception {
        int result = doInsertUserSystem(session, user, authenticationSource, isAdmin, AuthConstants.COMMUNITY_MEMBER_STATUS.REGISTERED_BY_THEMSELVES.getValue());
        return result;

    }

    public int insertTenmaSystemUser(SqlSession session, UserModel user, String authenticationSource, boolean isAdmin, int memberStatus) throws Exception {
        int result = doInsertUserSystem(session, user, authenticationSource, isAdmin, memberStatus);
        return result;

    }

    private int doInsertUserSystem(SqlSession session, UserModel user, String authenticationSource, boolean isAdmin, int memberStatus) throws Exception {
        int result = 0;
        try {
            if (user.getUserRegistrationStatus() == null)
                user.setUserRegistrationStatus(0);

            UserDao userDao = new UserDao(session);
            String sid = getNextUserId();
            System.out.println("UserHelper.insertUser " + sid);
            user.setUserId(sid);

            result = userDao.insertUser(user);
            System.out.println("result = " + result);
            if (result <= 0)
                throw new Exception(AuthConstants.ADD_FAILED);

            System.out.println("UserHelper.insertUser -- pass");
            AuthCredential userCredential = new AuthCredential();
            userCredential.setUserId(user.getUserId());
            if (user.getPassword() == null) {
                user.setPassword(TenmaEncrypt.doEncrypt(Converter.generateRandomPassword(true, 6)));
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("user pass + [" + user.getPassword() + "]");
            userCredential.setPassword(user.getPassword());
            userCredential.setCreatedBy(user.getCreatedBy());
            userCredential.setUpdatedBy(user.getUpdatedBy());
            userCredential.setCreatedFrom(user.getCreatedFrom());
            userCredential.setUpdatedFrom(user.getUpdatedFrom());

            TenmaAuth auth = new TenmaAuth(session, authenticationSource);
            System.out.println();
            System.out.println();
            System.out.println("");
            result = auth.inserUserCredential(userCredential);

            if (result <= 0) {
                userDao.deleteUser(user);
                throw new Exception(AuthConstants.ADD_FAILED);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return result;
    }

    /**
     * saving user tenma from admin user apps, the system generate new userId and automatically register current user as a member of Tenma Community
     *
     * @param user
     * @param authenticationSource
     * @param isForCommunityMemberShip
     * @param isAdmin
     * @return
     * @throws Exception
     */
    public int insertTenmaSystemUser(UserModel user, String authenticationSource, boolean isForCommunityMemberShip, boolean isAdmin) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.REUSE);
        if (user.getUserRegistrationStatus() == null)
            user.setUserRegistrationStatus(0);
        try {
            result = insertTenmaSystemUser(session, user, authenticationSource, isAdmin);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }


    /**
     * Insert Self Registered User
     *
     * @param user
     * @param authenticationSource
     * @return
     * @throws Exception
     */
    public int insertTenmaSystemUser(SqlSession session, UserModel user, String authenticationSource) throws Exception {
        int result = insertTenmaSystemUser(session, user, authenticationSource, false);
        return result;
    }


    public int updateUser(UserModel user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = updateUser(session, user);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateUser(SqlSession session, UserModel user) throws Exception {
        int result = 0;
        UserDao userDao = new UserDao(session);
        result = userDao.updateUser(user);

        if (result < 0)
            throw new Exception(AuthConstants.UPDATE_FAILED);
        return result;
    }

    public int updateUserRegistrationStatus(SqlSession session, UserModel user) throws Exception {
        int result = 0;
        UserDao userDao = new UserDao(session);
        result = userDao.updateUserRegistrationStatus(user);

        if (result < 0)
            throw new Exception(AuthConstants.UPDATE_FAILED);
        return result;
    }

    public int updateProfilePicture(UserModel user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            result = userDao.updateProfilePicture(user);
            if (result < 0)
                throw new Exception(AuthConstants.UPDATE_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }


    public int updateOccupationUser(UserModel user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            result = userDao.updateOccupationUser(user);
            if (result < 0)
                throw new Exception(AuthConstants.UPDATE_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int updateCompletenessOfInfo(HashMap user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            result = userDao.updateCompletenessOfInfo(user);
            if (result < 0)
                throw new Exception(AuthConstants.UPDATE_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }


    public int updateUserAktive(UserModel user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);

            result = userDao.updateUserActive(user);
            if (result < 0)
                throw new Exception(AuthConstants.UPDATE_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }


    public int deleteUser(UserModel user) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            int countRefUSer = userDao.countRefUserResponsibility(user.getUserId());
            if (countRefUSer > 0) {
                return Integer.parseInt(AuthConstants.DELETE_FAILED_HAVE_REFERENCE);
            } else {
                result = userDao.softDeleteUser(user);
                if (result <= 0)
                    throw new Exception(AuthConstants.DELETE_FAILED);
            }
            session.commit();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public int countTotalList(HashMap mapList) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            return userDao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap parameterObject, boolean includeNavigation) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            return userDao.getListRenderer(parameterObject, includeNavigation);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(SqlSession session, HashMap parameterObject, boolean includeNavigation) {
        UserDao userDao = new UserDao(session);
        return userDao.getListRenderer(parameterObject, includeNavigation);
    }

    public String getNextUserId() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            int seq = userDao.getNextUserId();
            return Converter.getNextSequenceId(seq);
        } finally {
            session.close();
        }
    }


    public UserModel getUserDetail(UserModel user) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            return userDao.getUserDetail(user);
        } finally {
            session.close();
        }
    }

    public String getUserName(String userId) {
        String nm = "";
        if (!AuthConstants.SYSTEM.equals(userId)) {
            SqlSession session = sqlSessionFactory.openSession();
            try {
                UserDao userDao = new UserDao(session);
                UserModel userModel = new UserModel();
                userModel.setUserId(userId);
                userModel = userDao.getUserDetail(userModel);
                if (userModel != null) {
                    MemberModel memberModel = new MemberModel();
                    memberModel.setMemberId(userModel.getMemberId());

                    MemberDao memberDao = new MemberDao(session);
                    memberModel = memberDao.getMember(memberModel);
                    if (memberModel != null)
                        nm = memberModel.getMemberName();
                }
            } finally {
                session.close();
            }
        } else
            nm = "default.system";
        return nm;
    }


    public int countUserForInvitation(UserModel user) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            return userDao.countUserForInvitation(user);
        } finally {
            session.close();
        }
    }

    public UserModel getUserDetailForum(UserModel user) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            return userDao.getUserDetailForum(user);
        } finally {
            session.close();
        }
    }


    public List listUserItems(HashMap map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao dao = new UserDao(session);
            return dao.listUserItems(map);
        } finally {
            session.close();
        }
    }


    public int updateCounter(UserModel u) throws Exception {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = new UserDao(session);
            result = userDao.updateCounter(u);
            if (result < 0)
                throw new Exception(AuthConstants.UPDATE_FAILED);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }

    public int updatePassword(UserModel user, String authSource) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            ret = updatePassword(session, user, authSource);
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


    /**
     * @param session
     * @param user
     * @param authSource is Database or LDAP
     * @return
     * @throws Exception
     */
    public int updatePassword(SqlSession session, UserModel user, String authSource) throws Exception {

        int ret = 0;
        TenmaAuth auth = new TenmaAuth(session, authSource);
        ret = auth.changeUserCredential(user);

        return ret;
    }


    /**
     * @param userName
     * @param authSource is Database or LDAP
     * @return
     * @throws Exception
     */
    public UserModel findForgottenPassword(String userName, String authSource) throws Exception {
        UserModel result = null;
        SqlSession session = sqlSessionFactory.openSession();
        int ret = 0;
        try {
            AuthDao dao = new AuthDao(session);
            AuthModel userModel = dao.getAuthenticationUserDetailByLogonProfile(userName);
            if (userModel != null) {
                UserModel tmp = new UserModel();
                tmp.setUserId(userModel.getUserId());
                result = getUserDetail(tmp);

                result.setUpdatedBy("SYSTEM");
                result.setUpdatedFrom("SYSTEM");
                result.setCreatedFrom("SYSTEM");
                result.setCreatedBy("SYSTEM");
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
            session.rollback();
            throw new Exception(AuthConstants.UPDATE_FAILED);
        } finally {
            session.close();
        }
        return result;
    }
}