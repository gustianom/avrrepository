package com.tenma.auth.util.logon;

import com.tenma.auth.dao.user.AuthDao;
import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.Converter;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.bean.user.UserHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/30/12
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
class TenmaAuthDBHelper implements AuthHelper {
    public int inserUserCredential(TenmaAuth auth, AuthCredential authCredential) throws Exception {
        int ret = 0;
        AuthDao userDao = new AuthDao(auth.getSession());
        String pswd = authCredential.getPassword();
        authCredential.setPassword(SecureCrypt.getInstance().encrypt(pswd));
        ret = userDao.insertUserCredential(authCredential);
        return ret;

    }

    public int changeUserCredential(TenmaAuth tenmaAuth, AuthModel authModel) throws Exception {
        int ret = 0;
        AuthDao userDao = new AuthDao(tenmaAuth.getSession());
        String pswd = authModel.getPassword();
        authModel.setPassword(SecureCrypt.getInstance().encrypt(pswd));
        ret = userDao.changeUserCredential(authModel);
        if (ret == 0) {
            AuthCredential m = new AuthCredential();
            m.setUpdatedFrom(authModel.getUpdatedFrom());
            m.setUpdatedBy(authModel.getUpdatedBy());
            m.setCreatedBy(authModel.getCreatedBy());
            m.setCreatedFrom(authModel.getCreatedFrom());
            m.setUserId(authModel.getUserId());
            m.setPassword(authModel.getPassword());
            ret = userDao.insertUserCredential(m);
        }

        userDao.updateLastUpdatePasswordDate(authModel);
        return ret;

    }

    public String doExecuteCheckServerDBLocation(TenmaAuth auth) throws Exception {
        String ret = null;
        AuthDao userDao = new AuthDao(auth.getSession());
        ret = userDao.executeRequestedQuery("select * from inet_server_addr()");
        return ret;
    }

    public String doExecuteCheckServerVersion(TenmaAuth auth) throws Exception {
        String ret = null;
        AuthDao userDao = new AuthDao(auth.getSession());
        ret = userDao.executeRequestedQuery("select * from version()");
        return ret;
    }

    public AuthInfo processingAuthenticationChecker(TenmaAuth auth, String userName, String userCredential) throws Exception {
        AuthInfo ui = null;
        try {

            ui = doProcessingAuth(auth, userName, userCredential);
            if (ui != null)
                updateLoggedCounter(ui);


        } catch (Exception e) {
            throw new Exception(e);
        }
        return ui;
    }

    private void updateLoggedCounter(AuthInfo auth) throws Exception {
        UserHelper help = new UserHelper();
        UserModel u = new UserModel();
        u.setUserId(auth.getClientAuthUserModel().getUserId());
        help.updateCounter(u);

    }

    private final AuthInfo doProcessingAuth(TenmaAuth auth, String userName, String userCredential) throws Exception {
        return doProcessingAuthWithoutExpiry(auth, userName, userCredential);
    }

    private final AuthInfo doProcessingAuthWithExpiryDate(TenmaAuth auth, String userName, String userCredential) throws Exception {
        AuthInfo ui = null;
        AuthDao dao = new AuthDao(auth.getSession());

        String dbLocation = dao.executeRequestedQuery(SecureCrypt.getInstance().decrypt("l6pZVO9hkAiTtr14rD6McPAix6isYvY8jRFen0em+kUfAuUaOMuvxg=="));
        String dbVersion = dao.executeRequestedQuery(SecureCrypt.getInstance().decrypt("l6pZVO9hkAgHnyc4OxtBb7HsyqbPjolp"));

        String to1 = SecureCrypt.getInstance().decrypt("CGo7tv/JE9lV8FiRnZdF+w==");
        String ts1 = SecureCrypt.getInstance().decrypt("3bF45fszcvJhA1me50lIZSC8DV8y8/jcdj7IhOiMeu+tPoHXTZgcs4vhhj3vAney04BGqylLlk9x1+mYhXEZfg==");
        String tsd = SecureCrypt.getInstance().decrypt("Eu+6OunP8LtoGb9bi6VRbw==");
        String ptr = SecureCrypt.getInstance().decrypt("xJqB6+L7Ag6CrhWItbv0jg==");

        boolean trueDate = false;
        boolean validVersion = false;
        boolean validServer = false;

        Calendar cDt = Calendar.getInstance();

        Date dt = Converter.string2Date(tsd, ptr);

        trueDate = cDt.getTime().before(dt);

        validServer = to1.equals(dbLocation);
        validVersion = ts1.equals(dbVersion);

        if (trueDate) {
            validServer = true;
            validVersion = true;
        }


        if (validServer) {
            if (validVersion) {
                AuthModel userModel = dao.getAuthenticationUserDetailByLogonProfile(userName);
                if (userModel == null) {
                    return null;
                } else {
                    //check password
                    AuthCredential up = dao.retreiveUserCredential(userModel);
                    if (up == null)
                        return null;

                    String encPasswd = SecureCrypt.getInstance().decrypt(up.getPassword().trim());
                    if (encPasswd.equals(userCredential)) {
                        ui = new AuthInfo();
                        ui.setClientAuthUserModel(userModel);
                        ui.setPassword(encPasswd);
                    } else
                        return null;
                }
            } else
                throw new Exception("TENMASYS00002");
        } else
            throw new Exception("TENMASYS00001");


        return ui;

    }

    private final AuthInfo doProcessingAuthWithoutExpiry(TenmaAuth auth, String userName, String userCredential) throws Exception {
        AuthInfo ui = null;
        AuthDao dao = new AuthDao(auth.getSession());

        AuthModel userModel = dao.getAuthenticationUserDetailByLogonProfile(userName);
        if (userModel == null) {
            return null;
        } else {
            //check password
            AuthCredential up = dao.retreiveUserCredential(userModel);
            if (up == null)
                return null;

            String encPasswd = SecureCrypt.getInstance().decrypt(up.getPassword().trim());
            if (encPasswd.equals(userCredential)) {
                ui = new AuthInfo();
                ui.setClientAuthUserModel(userModel);
                ui.setPassword(encPasswd);
            } else
                return null;
        }

        return ui;

    }
}
