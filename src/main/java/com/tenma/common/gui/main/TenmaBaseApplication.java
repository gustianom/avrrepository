package com.tenma.common.gui.main;

import com.tenma.auth.model.AccountCommunityModel;
import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.CommunityStrucModel;
import com.tenma.auth.util.logon.CommunityStrucHelper;
import com.tenma.auth.util.logon.TenmaAuth;
import com.tenma.auth.util.logon.action.LoginTool;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.TA;

import com.tenma.common.bean.todo.TodoHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.main.changepassword.ConfirmChangePasswordView;
import com.tenma.common.gui.main.forgotpassword.ForgotPassword;
import com.tenma.common.gui.main.forgotpassword.VerifyChangePasswordFromEmailLink;
import com.tenma.common.gui.main.useractivation.UserAccountActivation;
import com.tenma.common.gui.ui.CommonScreenApps;
import com.tenma.common.util.Constants;
import com.tenma.common.util.awssns.GCMInfo;
import com.tenma.common.util.error.TenmaStackTraceManager;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.tenma.common.util.session.TemporarySession;
import com.tenma.common.util.web.ClientInfo;
import com.tenma.fam.bean.refAccCommunity.AccountCommunityHelper;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.cxf.ws.addressing.impl.MAPAggregatorImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;

/**
 * TenmaBaseApplication is base class of TenmaWebApplication
 * that cover all common based fuction on very TenmaWebApplication
 * while TenmaWebApplication will be custom based on the implementation
 * <p>
 * Created by ndwijaya on 1/25/2016.
 */
public abstract class TenmaBaseApplication extends UI implements RequestHandler, ErrorHandler {
    public static final String TENMA_MAIN_VIEW = "home";
    protected GCMInfo gcmInfo = new GCMInfo();

    protected CommonScreenApps welcome = null;
    protected CommunityModel communityModel;
    protected ClientInfo clientInfo;
    protected boolean mobile;
    protected TenmaStackTraceManager stackTraceManager;
    private String hostPath = "";
    private String realPath = "";
    private String nextPageAction;

    public TopPanelAction getTopPanel() {
        return welcome.getTopPanel();
    }

    public VerticalLayout getFocusedArea() {
        if (welcome != null)
            return welcome.focusedWindows();
        else
            return new VerticalLayout();
    }

    protected void init(VaadinRequest request) {
        TA.getCurrent().initVaadin();
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
        ServletContext servletContext = httpSession.getServletContext();
        realPath = servletContext.getRealPath("/");

        System.out.println("servletContext = " + servletContext.getContextPath());
        System.out.println("realpath = " + realPath);

        HttpServletRequest vadiinRequest = (HttpServletRequest) VaadinService.getCurrentRequest();
        hostPath = new StringBuffer()
                .append(vadiinRequest.getScheme()).append("://")
                .append(vadiinRequest.getServerName())
                .append(vadiinRequest.getServerPort() == 80 ? "" : ":")
                .append(vadiinRequest.getServerPort() == 80 ? "" : vadiinRequest.getServerPort())
                .append(vadiinRequest.getContextPath())
                .toString();

        System.out.println("path = " + hostPath);
        TA.getCurrent().setHostPath(hostPath);

        long start = Calendar.getInstance().getTimeInMillis();
        stackTraceManager = TenmaStackTraceManager.getInstance();

        mobile = isMobile(request);
        System.out.println("mobile = " + mobile);

        UI.getCurrent().setErrorHandler(this);
        VaadinSession.getCurrent().addRequestHandler(this);
        this.setId("Meter");
        setLocale(vadiinRequest.getLocale());
        getSession().setLocale(vadiinRequest.getLocale());
        String title = TA.getCurrent().getParams().getProperty("applicationTitle");
        if (!((title != null) && (title.trim().length() != 0)))
            title = "Welcome to Tenma Services";
        getUI().getPage().setTitle(title);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TenmaWebApplication.init ----------------------------------------------------------> path = " + request.getPathInfo());
        System.out.println("TenmaWebApplication.init ----------------------------------------------------------> ses = " + VaadinSession.getCurrent().getAttribute(com.tenma.common.util.Constants.DATA_TRANSMITTED));
        System.out.println("TenmaWebApplication.init ----------------------------------------------------------> req = " + request.getAttribute(com.tenma.common.util.Constants.DATA_TRANSMITTED));
        System.out.println("TenmaWebApplication.init ----------------------------------------------------------> SSSSION ID  = " + request.getWrappedSession().getId());

        String authenticationSource = TA.getCurrent().getParams().getProperty(TenmaAuth.SYSTEM_AUTHENTICATION_SOURCE);

        System.out.println("NewPasswordInvitation.ConfirmPasswordView " + authenticationSource);

        if (authenticationSource == null)
            authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
        else if (authenticationSource.trim().length() == 0)
            authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
        else {
            if (TenmaAuth.AUTHENTICATION_BY_DATABASE.equals(authenticationSource))
                authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
            else if (TenmaAuth.AUTHENTICATION_BY_LDAP.equals(authenticationSource))
                authenticationSource = TenmaAuth.AUTHENTICATION_BY_LDAP;
        }

        TA.getCurrent().setAuthenticationSource(authenticationSource);

        try {
            if ("/login".equals(request.getPathInfo())) {

                boolean authendicated = doUserProfiling(request);
                logonSuccessFully();
                System.out.println("Logon = " + this.getClientInfo().getClientUserModel().getUserFullName());
                if (welcome != null)
                    welcome.setUserLogo(this.getClientInfo().getClientUserModel().getUserFullName());

                if (LoginTool.LOGON_RESULT_TYPE.ACCOUNT_INACTIVE.getValue().equals(nextPageAction)) {
                    UserAccountActivation activation = new UserAccountActivation(this);
                    UI.getCurrent().setContent(activation);
                } else if (LoginTool.LOGON_RESULT_TYPE.MUST_CHANGE_PASSWORD.getValue().equals(nextPageAction)) {
                    ConfirmChangePasswordView activation = new ConfirmChangePasswordView();
                    UI.getCurrent().setContent(activation);
                }

                long end = Calendar.getInstance().getTimeInMillis();
                long logtime = end - start;
                System.out.println("===========================-======================================================================");
                System.out.println("=============================== LOGON : " + logtime + " ===================================");
                System.out.println("===========================-======================================================================");

            } else if ("/verifychpwd".equals(request.getPathInfo())) {
                doVerifyChangePasswordFromEmail(request);
            } else if ("/forgot".equals(request.getPathInfo())) {
                ForgotPassword forgotPassword = new ForgotPassword();
                UI.getCurrent().setContent(forgotPassword);
            } else if ("/verify".equals(request.getPathInfo())) {
                System.out.println("verify account");
            } else if ("/activation".equals(request.getPathInfo())) {
                String code = request.getParameter("code");
//                InvitationConfirm signUp = new InvitationConfirm( code);
//                UI.getCurrent().setContent(signUp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            stackTraceManager.addNewStackTrace(30, e);
        }
    }

    public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {

        boolean isResponseWriten = false;

        if ("/logout".equals(request.getPathInfo())) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("TenmaWebApplication.handleRequest LOGOUT Huraaaaaaaa!..............");
            request.setAttribute(Constants.SERVLET_COMMAND.DO_LOGOUT.getValue(), "OK");
        } else if ("/activation".equals(request.getPathInfo())) {
//            Object data = request.getWrappedSession().getId();
            Component com = getUI().getContent();
//            if (share instanceof InvitationConfirm) {
//                InvitationConfirm link = (InvitationConfirm) getUI().getContent();
//                link.refreshUI(request.getParameter("code"));
//            }
        } else {
            try {
                Object data = TemporarySession.getInstance().getData(request.getWrappedSession().getId());
                if (data != null)
                    doUserProfiling(request);
                else {
                    isResponseWriten = true;
                    response.sendError(401, "login");
                }
            } catch (Exception e) {
                stackTraceManager.addNewStackTrace(30, e);
                e.printStackTrace();
            }
        }
        return isResponseWriten;
    }

    protected boolean doUserProfiling(VaadinRequest request) throws Exception {
        Object o = null;
        boolean authenticated = TA.getCurrent().isAuthenticated();
        if (authenticated) {
//        if (request.getWrappedSession() != null)
            if (request.getWrappedSession().getId() != null)
                o = TemporarySession.getInstance().getData(request.getWrappedSession().getId());
//
//        nextPageAction = "";
//        System.out.println("TenmaWebApplication.doUserProfiling ----------------------------------------------------------> USER  " + o);
            if (o != null) {
                AuthInfo authInfo = (AuthInfo) o;

//               login tool move to ReceiverServlet outside the vaadin
//                LoginTool loginTool = new LoginTool(TenmaAuth.AUTHENTICATION_BY_DATABASE, request.getLocale(), request.getRemoteAddr());
//            HashMap data = loginTool.doCollectClientDetail(authInfo);


                HashMap data = (HashMap) request.getWrappedSession().getAttribute(com.tenma.common.util.Constants.DATADTL_TRANSMITTED);
                System.out.println("data = " + data);

                nextPageAction = data.containsKey(LoginTool.LOGON_RESULT) ? (String) data.get(LoginTool.LOGON_RESULT) : LoginTool.LOGON_RESULT_TYPE.AUTHENTICATION_FAILED.getValue();
                ClientInfo clientInfo = data.containsKey(LoginTool.LOGON_CLIENT_INFO) ? (ClientInfo) data.get(LoginTool.LOGON_CLIENT_INFO) : null;
                CommunityModel communityModel = data.containsKey(LoginTool.LOGON_COMMUNITY_MODEL) ? (CommunityModel) data.get(LoginTool.LOGON_COMMUNITY_MODEL) : null;

                if (LoginTool.LOGON_RESULT_TYPE.ACCOUNT_EXPIRED.getValue().equals(nextPageAction))
                    getCurrent().getPage().setLocation("accountExpired.jsp");
                else if (LoginTool.LOGON_RESULT_TYPE.ACCOUNT_LOCKED.getValue().equals(nextPageAction))
                    getCurrent().getPage().setLocation("accountLockedAttemp.jsp");
                else {
                    AccountCommunityHelper helper = new AccountCommunityHelper();
                    AccountCommunityModel argModel = new AccountCommunityModel();
                    argModel.setCommunityId(communityModel.getCommunityId());
                    System.out.println("argModel.getCommunityId() = " + argModel.getCommunityId());
                    AccountCommunityModel accountCommunityModel = helper.getAccountCommunityDetail(argModel);
                    System.out.println("accountCommunityModel = " + accountCommunityModel);
                    if (accountCommunityModel != null) {
                        communityModel.setCommunityAccount(accountCommunityModel);
                    }
                    setAuthenticatedUserProfile(clientInfo, communityModel);
                    clientInfo.setTicketId(authInfo.getTicketId());

                    try {
                        getClientInfo().setSessionProcessId(-100); //LOGON
                        getClientInfo().setSessionProcessGroupId(-100); //LOGON
                        WrappedSession wvsession = UI.getCurrent().getSession().getSession();
                        String sessionId = wvsession == null ? "" : wvsession.getId();
                        System.out.println("sessionId = " + sessionId);
                        TenmaMonitor monitor = TenmaMonitor.getInstance();
                        long time = Calendar.getInstance().getTimeInMillis();
                        String userId = clientInfo.getClientUserModel().getUserId();
                        monitor.registerLog(sessionId, userId);
                        monitor.setStartTime(userId, time);
                        monitor.pageLoaded();
                    } catch (Exception e) {
                        stackTraceManager.addNewStackTrace(10, e);
//                    TODO Ticket no  NPL is not handle
//                    e.printStackTrace();
                    }
                }
            }
        }
        return authenticated;
    }

    public void logonSuccessFully() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("---.logonSuccessFully");

//        boolean isContinue = doCheckMandatorySystemTask();
//        if (isContinue) {
        // load ApplicationPlatform from database, create if not exist
//            String applicationPlatform = AWSSNSUtil.getInstance(this).loadApplicationPlatform();
//            if (applicationPlatform == null) {
//                applicationPlatform = AWSSNSUtil.getInstance(this).createAWSGCMApplicationPlatform(Constants.AWS_GGM_APPLICATION_NAME);
//                SystemPropertyHelper helper = new SystemPropertyHelper();
//                SystemPropertyModel model = new SystemPropertyModel();
//                model.setSystemPropertyName(Constants.AWS_GGM_APPLICATION_PLATFORMNAME);
//                model.setSystemPropertyValue(applicationPlatform);
//                try {
//                    int rs = helper.updateSystemProperty(model);
//                    if (rs == 1)
//                        System.out.println("New GCMAppliction platform  = " + applicationPlatform + " was created ");
//                } catch (Exception e) {
//                    System.out.println("Fail to update community GCM App platform = " + e.getMessage());
//                }
//            }
//            gcmInfo.setGcmapplicationPlatform(applicationPlatform);

        CommunityStrucModel strucModel = new CommunityStrucModel();
        strucModel.setStructureId(TA.getCurrent().getCommunityModel().getCommunityStructure());

        CommunityStrucHelper strucHelper = new CommunityStrucHelper();
        strucModel = strucHelper.getCommunityStrucDetail(strucModel);
        System.out.println("Usert Screen ID : " + this.clientInfo.getClientUserModel().getScreenId());
        if (strucModel != null && (strucModel.getScreenClass() != null && strucModel.getScreenClass().trim().length() != 0)) {
            loadingScreen(strucModel.getScreenClass());
        } else {
            Panel p = new Panel(new Label("Undefined screen structure"));
            p.setSizeFull();
            UI.getCurrent().setContent(p);
            setSizeFull();
        }
        System.out.println("Community Account = " + TA.getCurrent().getCommunityModel().getCommunityAccount().toString());
        geoLogging();
//        }
    }

//    public boolean doCheckMandatorySystemTask() {
//        TodoHelper helper = new TodoHelper();
//
//        boolean isContinue = true;
//
//        HashMap p = new HashMap();
//        p.put(Constants.COMMUNITY_ID, TA.getCurrent().getCommunityModel().getCommunityId());
//        p.put("notifyTo", TA.getCurrent().getClientInfo().getClientUserModel().getUserId());
//        p.put("systemMandatory", Constants.TASK_SYSTEM_MANDATORY_STATUS.MANDATORY.getValue());
//        int cnt = helper.countTotalList(p);
//        if (cnt > 0) {
//            isContinue = false;
////            ViewFirstTimeSetup setup = new ViewFirstTimeSetup(this);
////            UI.getCurrent().setContent(setup);
//        }
//        return isContinue;
//    }

    private void geoLogging() {
        System.out.println("Client IP = " + this.getClientInfo().getIpAddress());
        String ip = this.getClientInfo().getIpAddress();
        if (ip != null) {
            if (!(ip.equalsIgnoreCase("127.0.0.1") || ip.startsWith("192.") || ip.startsWith("172."))) {
                Client client = ClientBuilder.newClient();
                String rs = "";
                try {
//                    remark due, we need to asynchronus geologing
//                    WebTarget target = client.target("http://www.telize.com/geoip/" + this.getClientInfo().getIpAddress());
//                    rs = target.request().get(String.class);
                } catch (Exception e) {
                    System.out.println("IP GEO LOG = Access FAILED");
                }
                System.out.println("rs = " + rs);
            } else {
                System.out.println("Client is Local IP = " + ip);
            }
        }
    }


    private boolean isMobile(VaadinRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        return userAgent != null
                && userAgent.toLowerCase().matches(
                ".*(iphone|ipad|android|mobile).*");
    }

    protected void loadingScreen(String sceenName) {
        System.out.println("Loading screen = " + sceenName);
        try {
            Class panelClass = Class.forName(sceenName);
            Constructor moduleConstructor = panelClass.getConstructor();
            welcome = (CommonScreenApps) moduleConstructor.newInstance();
            TA.getCurrent().setCommonScreen(welcome);
            UI.getCurrent().setContent(welcome);
            welcome.doRenderWelcome();
            welcome.setSizeFull();
            setSizeFull();
            if (welcome.isRequiredBlankScreen()) {
                TenmaNewPanel blank = new BlankPanel();
                TA.getCurrent().updateWorkingArea(blank);
            }
        } catch (Exception e) {
            stackTraceManager.addNewStackTrace(40, e);
            e.printStackTrace();
        }
    }

    private void doVerifyChangePasswordFromEmail(VaadinRequest request) {
        String code = request.getParameter("code");
        VerifyChangePasswordFromEmailLink components = new VerifyChangePasswordFromEmailLink(code);
    }


    public final void setAuthenticatedUserProfile(ClientInfo clientInfo, CommunityModel communityModel) {
        setClientInfo(clientInfo);
        TA.getCurrent().setCommunityModel(communityModel);
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getCurrentLocaleLanguageCode() {
        return TA.getCurrent().getParams().getLanguageCode();
    }

    public void setCurrentLocaleLanguageCode(String currentLocaleLanguageCode) {
        TA.getCurrent().getParams().setLanguageCode(currentLocaleLanguageCode);
    }

    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        AbstractComponent component = DefaultErrorHandler.findAbstractComponent(event);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        PrintWriter pwriter = new PrintWriter(ostream);
        event.getThrowable().printStackTrace(pwriter);
        pwriter.flush();
        StringBuilder stacktrace = new StringBuilder(ostream.toString());
        stackTraceManager.addNewStackTrace(stacktrace.toString());
        event.getThrowable().printStackTrace();
        if (component != null) {
            new Notification("System Error", "General Error, contact administrator", Notification.Type.WARNING_MESSAGE, true).show(Page.getCurrent());
            return;
        }
        DefaultErrorHandler.doDefault(event);
    }

    class BlankPanel extends TenmaNewPanel {
        public BlankPanel() {
            setViewName(TENMA_MAIN_VIEW);
            setStyleName(ValoTheme.PANEL_BORDERLESS);
        }
    }


}
