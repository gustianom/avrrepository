package com.tenma.auth.util.logon;

import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import com.tenma.auth.util.web.AuthInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/30/12
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
interface AuthHelper {
    int inserUserCredential(TenmaAuth auth, AuthCredential authCredential) throws Exception;

    int changeUserCredential(TenmaAuth tenmaAuth, AuthModel authModel) throws Exception;

    String doExecuteCheckServerDBLocation(TenmaAuth auth) throws Exception;

    String doExecuteCheckServerVersion(TenmaAuth auth) throws Exception;

    AuthInfo processingAuthenticationChecker(TenmaAuth auth, String userName, String userCredential) throws Exception;
}
