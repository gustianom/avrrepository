package com.tenma.auth.dao.user;

import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.UserModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: anom
 * Date: 4/1/12
 * Time: 3:46 PM
 */
public class UserDao extends Dao {
    public UserDao(SqlSession session) {
        super(session);
    }

    public UserModel getUser(String userId) {
        UserModel m = new UserModel();
        m.setUserId(userId);
        return (UserModel) session.selectOne("getUserDetail", m);
    }


    public int insertUser(UserModel userModel) {
        int res = session.update("insertUser", userModel);
        return res;
    }

    public int updateUser(UserModel userModel) {
        int res = session.update("updateUser", userModel);
        return res;
    }

    public int updateUserRegistrationStatus(UserModel userModel) {
        int res = session.update("updateUserRegistrationStatus", userModel);
        return res;
    }

    public int updateProfilePicture(UserModel userModel) {
        int res = session.update("updateProfilePicture", userModel);
        return res;
    }

    public int updateUserForEmployee(UserModel userModel) {
        int res = session.update("updateUserForEmployee", userModel);
        return res;
    }


    public int updateOccupationUser(UserModel userModel) {
        int res = session.update("updateOccupationUser", userModel);
        return res;
    }

    public int updateUserActive(UserModel userModel) {
        int res = session.update("updateUserActive", userModel);
        return res;
    }

    public int deleteUser(UserModel userModel) {
        int res = session.update("deleteUser", userModel);
        return res;
    }

    public int softDeleteUser(UserModel userModel) {
        int res = session.update("softDeleteUser", userModel);
        return res;
    }

    public int updateCompletenessOfInfo(HashMap map) {
        int res = session.update("updateCompletenessOfInfo", map);
        return res;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalUser", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public List getListRenderer(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listUserMap", parameterObject);
        else
            return session.selectList("listAllUserMap", parameterObject);
    }

    public int getNextUserId() {
        int inNext;
        Integer sid = (Integer) session.selectOne("getUserSequence", new Object());
        System.out.println("UserDao.getNextUserId sid = " + sid);
        inNext = 0;
        if (sid != null)
            inNext = sid.intValue();
        inNext++;
        return inNext;
    }

    public UserModel getUserDetail(UserModel userModel) {
        return (UserModel) session.selectOne("getUserDetail", userModel);
    }


    public int countUserForInvitation(UserModel userModel) {
        Integer count = (Integer) session.selectOne("countUserForInvitation", userModel);
        return count == null ? 0 : count;
    }

    public UserModel getUserDetailForum(UserModel userModel) {
        return (UserModel) session.selectOne("getUserDetailForum", userModel);
    }

    public HashMap getExtendedUserDetail(HashMap map) {
        return (HashMap) session.selectOne("queryInfo", map);
    }


    public int countRefUserResponsibility(String userId) {
        Integer count = (Integer) session.selectOne("countRefUserResponsibility", userId);
        return count == null ? 0 : count.intValue();
    }

    public List getUserPasswordList(Object parameterObject) {
        return session.selectList("getUserPasswordList", parameterObject);
    }


    public int updateActiveEmail(UserModel model) {
        int res = session.update("updateActiveEmail", model);
        return res;
    }

    public List listUserItems(HashMap map) {
        List list = session.selectList("getListUserItems", map);
        return list;
    }

    public int updateCounter(UserModel u) {
        int res = session.update("updateCounter", u);
        return res;
    }

    public Integer getUserRootType(String userId) {
        Integer count = (Integer) session.selectOne("getUserRootType", userId);
        return count == null ? 0 : count;
    }
}

