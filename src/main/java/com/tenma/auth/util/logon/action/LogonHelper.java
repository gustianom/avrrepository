package com.tenma.auth.util.logon.action;

import com.tenma.auth.dao.user.UserDao;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.db.DaoHelper;
import com.tenma.auth.util.logon.TenmaAuth;
import com.tenma.auth.util.web.AuthInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:15 PM
 */
public class LogonHelper extends DaoHelper {
    private Locale local;

    public LogonHelper(Locale local) {
        this.local = local;
    }


    public AuthInfo authenticate(String authenticationSource, String userName, String passwd) throws Exception {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.REUSE, false);
        AuthInfo authInfo = null;
        try {
            TenmaAuth auth = new TenmaAuth(session, authenticationSource);
            authInfo = auth.doAuthentication(userName, passwd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return authInfo;
    }

    public UserModel getUserDetail(String userId) {
        SqlSession session = sqlSessionFactory.openSession();
        UserModel mdl = null;
        try {
            mdl = getUserDetail(session, userId);
        } finally {
            session.close();
        }
        return mdl;
    }

    public UserModel getUserDetail(SqlSession session, String userId) {
        UserDao dao = new UserDao(session);
        return dao.getUser(userId);
    }

    public Integer getUserRootType(SqlSession session, String userId) {
        UserDao dao = new UserDao(session);
        return dao.getUserRootType(userId);
    }

    public boolean isAccountValid(Date startDate, Date endDate) {
        boolean res = false;
        Date currDate = Calendar.getInstance(local).getTime();
        if (endDate == null) endDate = currDate;

        if (startDate.before(currDate)) {
            if (endDate.equals(currDate)) res = true;
            else if (endDate.after(currDate)) res = true;
        }
        return res;
    }

    @Override
    /**
     * Method ini harus di override untuk support paging
     */
    public int countTotalList(HashMap mapList) throws Exception {
        throw new UnsupportedOperationException("You must override this method on your helper class");
    }

    @Override
    /**
     * Method ini harus di override untuk support paging
     */
    public List getListRenderer(HashMap mapList, boolean navigated) throws Exception {
        throw new UnsupportedOperationException("You must override this method on your helper class");
    }
}
