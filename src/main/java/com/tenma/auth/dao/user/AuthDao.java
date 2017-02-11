package com.tenma.auth.dao.user;

import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthDao {
    private SqlSession session;

    public AuthDao(SqlSession session) {
        this.session = session;
    }

    public int resetPassword(AuthModel userModel) {
        int res = session.update("resetPassword", userModel);
        return res;
    }

    public int changeUserCredential(AuthModel userModel) {
        int res = session.update("changeUserCredential", userModel);
        return res;
    }

    public int insertUserCredential(AuthCredential userCredential) {
        int res = session.insert("insertUserCredential", userCredential);
        return res;
    }

    public AuthModel getAuthenticationUserDetailByLogonProfile(String idForLogin) {
        AuthModel domain = new AuthModel();

        domain.setIdForLogin(idForLogin);
        domain = (AuthModel) session.selectOne("getAuthenticationUserDetail", domain);
        return domain;
    }

    public AuthCredential retreiveUserCredential(AuthModel userModel) {
        return (AuthCredential) session.selectOne("retreiveUserCredential", userModel);
    }

    public String executeRequestedQuery(String squery) {
        HashMap map = new HashMap();
        map.put("tenmaRequestQuery", squery);
        return (String) session.selectOne("executeRequestedQuery", map);
    }

    public int updateLastUpdatePasswordDate(AuthModel authModel) {
        return session.update("updateLastUpdatePasswordDate", authModel);
    }
}


