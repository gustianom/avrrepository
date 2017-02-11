package com.tenma.auth.util.logon;

import com.tenma.auth.model.AuthCredential;
import com.tenma.auth.model.AuthModel;
import com.tenma.auth.util.web.AuthInfo;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by IntelliJ IDEA.
 * User: anom
 * Date: 4/1/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TenmaAuth {
    public static String SYSTEM_AUTHENTICATION_SOURCE = "auth.source";
    public static String AUTHENTICATION_BY_LDAP = "ldap";
    public static String AUTHENTICATION_BY_DATABASE = "database";

    private SqlSession session;
    private String userName;
    private String userCredential;
    private String authenticationMedia;
    private AuthHelper authHelper = null;

    public TenmaAuth(SqlSession session, String authenticationMedia) throws Exception {
        this.session = session;
        this.authenticationMedia = authenticationMedia;
        if (AUTHENTICATION_BY_LDAP.equals(authenticationMedia))
            authHelper = new TenmaAuthLDAPHelper();
        else if (AUTHENTICATION_BY_DATABASE.equals(authenticationMedia))
            authHelper = new TenmaAuthDBHelper();
        else throw new Exception("Unrecognized Authentication Media");
    }

    private static String formatMobileNumber(String mobileNumber) {
        if (mobileNumber.startsWith("0")) {
            mobileNumber = mobileNumber.replaceFirst("0", "+62");
        }
        return mobileNumber;
    }

    public SqlSession getSession() {
        return session;
    }

    public String getAuthenticationMedia() {
        return authenticationMedia;
    }

    public int inserUserCredential(AuthCredential authCredential) throws Exception {
        return authHelper.inserUserCredential(this, authCredential);
    }

    public int changeUserCredential(AuthModel authModel) throws Exception {
        return authHelper.changeUserCredential(this, authModel);
    }

    public AuthInfo doAuthentication(String userName, String userCredential) throws Exception {
        AuthInfo authinfo = null;
        try {
            userName = userName.toLowerCase();
            if (userName.startsWith("0"))
                userName = formatMobileNumber(userName);

            this.userName = userName;
            this.userCredential = userCredential;
            authinfo = authHelper.processingAuthenticationChecker(this, userName, userCredential);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null)
                if (!e.getMessage().contains("TENMASYS0000"))
                    e.printStackTrace();
        }
        return authinfo;
    }

}
