package com.tenma.auth.util.logon.action;

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.MemberModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.AuthConstants;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.auth.util.logon.MemberHelper;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.util.web.ClientInfo;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: gustianom
 * Date: 11/26/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginTool {
    public static String LOGON_RESULT = "LOGON_RESULT";
    public static String LOGON_CLIENT_INFO = "LOGON_CLIENT_INFO";
    public static String LOGON_COMMUNITY_MODEL = "LOGON_COMMUNITY_MODEL";
    //    private TenmaRootApplication application;
    private final String authenticationSource;
    private final Locale locale;
    private final String clientIp;

    public LoginTool(String authenticationSource, Locale locale, String clientIp) {
        this.authenticationSource = authenticationSource;
        this.locale = locale;
        this.clientIp = clientIp;
    }


    public final AuthInfo isAuthenticated(String userName, String password) throws Exception {
        LogonHelper helper = new LogonHelper(locale);
        AuthInfo authInfo = helper.authenticate(authenticationSource, userName, password);
        return authInfo;
    }

    /**
     * Keyset :
     * <ul>
     * <li>LOGON_RESULT contain LoginTool.LOGON_RESULT_TYPE</li>
     * <li>LOGON_CLIENT_INFO contain ClientInfo Model</li>
     * <li>LOGON_COMMUNITY_MODEL contain CommunityModel</li>
     * </ul>
     *
     * @param authInfo
     * @return
     * @throws Exception
     */
    public final HashMap doCollectClientDetail(AuthInfo authInfo) throws Exception {
        CommunityModel communityModel = null;
        ClientInfo clientInfo = null;

        LogonHelper helper = new LogonHelper(locale);

        SqlSession session = helper.sqlSessionFactory.openSession();


        String result = LOGON_RESULT_TYPE.AUTHENTICATION_FAILED.getValue();

        try {
            if (authInfo != null) {
                String userId = (authInfo.getClientAuthUserModel().getUserId());
                UserModel u = helper.getUserDetail(session, userId);
                if (helper.isAccountValid(u.getStartDate(), u.getEndDate())) {

                    MemberModel memberModel = new MemberModel();
                    memberModel.setMemberId(u.getMemberId());

                    MemberHelper memberHelper = new MemberHelper();
                    memberModel = memberHelper.getMemberDetail(memberModel);

                    clientInfo = new ClientInfo();
                    clientInfo.setClientUserModel(u);
                    clientInfo.setClientMemberModel(memberModel);
                    clientInfo.setIpAddress(clientIp);

                    communityModel = updateCommunityProfile(u.getDefCommunityId());

                    if (u.getUserStatus() != null) {
                        if (AuthConstants.USER_STATUS.ACTIVE.getValue() == u.getUserStatus().intValue()) {
                            if (AuthConstants.USER_REGISTRATION_STATUS.NEW_REGISTER.getValue() == u.getUserRegistrationStatus().intValue())
                                result = LOGON_RESULT_TYPE.ACCOUNT_INACTIVE.getValue();
                            else {
                                if (u.getLastUpdatedPassword() == null)
                                    result = LOGON_RESULT_TYPE.MUST_CHANGE_PASSWORD.getValue();
                                else {
                                    result = LOGON_RESULT_TYPE.AUTHENTICATION_SUCCESS.getValue();
                                    try {
                                        Integer rootType = helper.getUserRootType(session, u.getUserId());
                                        if (rootType != null && rootType.intValue() != 0) {
                                            if (777 == rootType.intValue())
                                                clientInfo.getClientAuthUserModel().setUserLevelType(AuthConstants.TENMA_USER_TYPE.SUPER.getValue());
                                            else
                                                clientInfo.getClientAuthUserModel().setUserLevelType(AuthConstants.TENMA_USER_TYPE.USER_SYSTEM.getValue());
                                        } else
                                            clientInfo.getClientAuthUserModel().setUserLevelType(AuthConstants.TENMA_USER_TYPE.CLIENT_SYSTEM.getValue());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        } else
                            result = LOGON_RESULT_TYPE.ACCOUNT_LOCKED.getValue();
                    } else
                        result = LOGON_RESULT_TYPE.ACCOUNT_LOCKED.getValue();

                } else {
                    result = LOGON_RESULT_TYPE.ACCOUNT_EXPIRED.getValue();
                }
            }
        } finally {
            session.close();
        }

        System.out.println("LoginTool.doCollectClientDetail hasil = " + clientInfo.getClientAuthUserModel().getUserLevelType());
        HashMap data = new HashMap();
        data.put(LOGON_RESULT, result);
        data.put(LOGON_CLIENT_INFO, clientInfo);
        data.put(LOGON_COMMUNITY_MODEL, communityModel);
        return data;
    }

    private CommunityModel updateCommunityProfile(String defCommunityId) {

        CommunityHelper cmHelper = new CommunityHelper();
        CommunityModel modelHelper = new CommunityModel();
        modelHelper.setCommunityId(defCommunityId);
        CommunityModel modelCommunity = cmHelper.getCommunityDetail(modelHelper);

        return modelCommunity;


    }

    public static enum LOGON_RESULT_TYPE {
        AUTHENTICATION_SUCCESS("1000"), AUTHENTICATION_FAILED("1200"), ACCOUNT_INACTIVE("1400"), ACCOUNT_LOCKED("1500"), ACCOUNT_EXPIRED("1600"), MUST_CHANGE_PASSWORD("1700");

        private String methodvalue = "1400";

        private LOGON_RESULT_TYPE(String value) {
            methodvalue = value;
        }

        public String getValue() {
            return methodvalue;
        }

    }


}
