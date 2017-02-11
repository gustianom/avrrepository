package com.tenma.auth.util.logon;

import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import com.tenma.auth.util.web.AuthInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/30/12
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
class TenmaAuthLDAPHelper implements AuthHelper {
    public int inserUserCredential(TenmaAuth auth, AuthCredential authCredential) throws Exception {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int changeUserCredential(TenmaAuth tenmaAuth, AuthModel authModel) throws Exception {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String doExecuteCheckServerDBLocation(TenmaAuth auth) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String doExecuteCheckServerVersion(TenmaAuth auth) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AuthInfo processingAuthenticationChecker(TenmaAuth auth, String userName, String userCredential) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
