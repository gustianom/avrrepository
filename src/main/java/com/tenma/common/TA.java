package com.tenma.common;

import com.tenma.auth.model.AccountCommunityModel;
import com.tenma.auth.model.AuditModel;
import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.util.logon.action.LoginTool;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.gui.display.TenmaHeaderPanel;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.main.TenmaBaseApplication;
import com.tenma.common.gui.ui.CommonScreenApps;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.awssns.GCMInfo;
import com.tenma.common.util.monitor.TenmaMonitor;
import com.tenma.common.util.web.ClientInfo;
import com.tenma.fam.bean.refAccCommunity.AccountCommunityHelper;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by ndwijaya on 1/6/2016.
 * version 2.0 alpha - version 1.0 is TenmaApplication
 * this class is intented to replace TenmaApplication
 * - can be call everywhere withou passing as parameter in constructor
 * - can be use in service and other outside Vaadin
 * - can be created before Vaadin is called.
 * - make the sourcecode less boilerplate
 */
public class TA {
    private static final Logger slogger = LoggerFactory.getLogger(TA.class);
    private final Logger logger = LoggerFactory.getLogger(TA.class);
    protected List<WorkingAreaChangeListener> workingAreaListner = new ArrayList<>();
    protected TenmaNewPanel activeScreen = null;
    private boolean personalMember = false;
    private CommunityModel communityModel;
    private CommonScreenApps welcome;
    private Navigator workNavigator; // Navigator for support PREV-NEXT browser button
    private GCMInfo gcmInfo = new GCMInfo();
    private ClientInfo clientInfo;
    private String authenticationSource;
    private Params params;
    //    private TenmaNewPanel activeScreen = null;
    private String remoteAddress;
    private boolean mobile;
    private String hostPath = "";
    private String realPath = "";
    private TenmaHeaderPanel headerArea;
    private Integer currentState;
    private String stateLayout;
    private UI vaadinUI;

    public TA(Locale locale) {
        params = Params.getInstance(locale);
    }

    public static TA initSession(HttpServletRequest request) {
        slogger.info("init-session");
        TA instance = null;
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.TENMA_APPLICATION) != null) {
            instance = (TA) session.getAttribute(Constants.TENMA_APPLICATION);
        } else {
            instance = new TA(request.getLocale());
            session.setAttribute(Constants.TENMA_APPLICATION, instance);
        }

        if (instance == null) {
            slogger.info("init-build-new-TA...");
            instance = new TA(request.getLocale());
            session.setAttribute(Constants.TENMA_APPLICATION, instance);
        }
        slogger.info("init-done.");
        return instance;
    }

    public static TA getCurrent() {
        TA instance = null;

        HttpServletRequest request = (HttpServletRequest) VaadinService.getCurrentRequest();

        if (request != null) {
            if (request.getSession().getAttribute(Constants.TENMA_APPLICATION) != null) {
                instance = (TA) request.getSession().getAttribute(Constants.TENMA_APPLICATION);
            } else {
                instance = new TA(request.getLocale());
                request.getSession().setAttribute(Constants.TENMA_APPLICATION, instance);
            }
        }
        return instance;
    }

    public static TA getCurrent(HttpServletRequest request) {
        TA instance = null;
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.TENMA_APPLICATION) != null) {
            instance = (TA) session.getAttribute(Constants.TENMA_APPLICATION);
        } else {
            instance = new TA(request.getLocale());
            session.setAttribute(Constants.TENMA_APPLICATION, instance);
        }
        return instance;
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public void init(HashMap data, AuthInfo authInfo) {
        clientInfo = data.containsKey(LoginTool.LOGON_CLIENT_INFO) ? (ClientInfo) data.get(LoginTool.LOGON_CLIENT_INFO) : null;
        CommunityModel acm = data.containsKey(LoginTool.LOGON_COMMUNITY_MODEL) ? (CommunityModel) data.get(LoginTool.LOGON_COMMUNITY_MODEL) : null;
        AccountCommunityHelper helper = new AccountCommunityHelper();
        AccountCommunityModel argModel = new AccountCommunityModel();
        argModel.setCommunityId(acm.getCommunityId());
        logger.info("argModel.getCommunityId() = " + argModel.getCommunityId());
        AccountCommunityModel accountCommunityModel = helper.getAccountCommunityDetail(argModel);
        logger.info("accountCommunityModel = " + accountCommunityModel);
        if (accountCommunityModel != null) {
            acm.setCommunityAccount(accountCommunityModel);
        }
        communityModel = acm;
        clientInfo.setTicketId(authInfo.getTicketId());

    }

    public void setCommonScreen(CommonScreenApps welcome) {
        this.welcome = welcome;
    }

    public void initVaadin() {
        params = Params.getInstance(getLocale());
        HttpServletRequest request = (HttpServletRequest) VaadinService.getCurrentRequest();
        remoteAddress = getClientIpAddr(request);
        vaadinUI = UI.getCurrent();
    }

    public UI getVaadinUI() {
        return vaadinUI;
    }

    public boolean isAuthenticated() {
        return true;
    }

    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) VaadinService.getCurrentRequest();
    }

    public CommunityModel getCommunityModel() {
        return communityModel;
    }

    public void setCommunityModel(CommunityModel communityModel) {
        this.communityModel = communityModel;
    }

    public boolean isPersonalMember() {
        return personalMember;
    }

    public void setAsPersonalMember() {
        personalMember = true;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    public String getServerFullHost() {
        return hostPath;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public Params getParams() {
        return params;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Locale getLocale() {
        return UI.getCurrent().getLocale();
    }

    public void goHome() {
        workNavigator.navigateTo(TenmaBaseApplication.TENMA_MAIN_VIEW);
    }

    public boolean isMobileAccess() {
        return mobile;
    }

    public void setMobileAccess(boolean mobile) {
        this.mobile = mobile;
    }

    public GCMInfo getGcmInfo() {
        return gcmInfo;
    }

    public final void setAuditTrail(AuditModel m) {
        String currentUserID = clientInfo.getClientUserModel().getUserId();
        if (currentUserID == null) currentUserID = Constants.SYSTEM;
        m.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        m.setCreatedBy(currentUserID);
        m.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
        m.setUpdatedBy(currentUserID);
    }

    public void updateWorkingArea(Panel newModule) {
        TenmaMonitor.getInstance().pageLoaded();
        VerticalLayout vl = new VerticalLayout();
        if (welcome != null)
            vl = welcome.getWorkingPanelArea();
        vl.removeAllComponents();
        vl.addComponent(newModule);
        vl.setSizeFull();
        newModule.setWidth("100%");

    }

    public void updateWorkingArea(TenmaPanel newModule) {
        logger.info("updateWorkingArea(TenmaPanel newModule)");
        TenmaMonitor.getInstance().pageLoaded();
        VerticalLayout vl = new VerticalLayout();
        if (welcome != null)
            vl = welcome.getWorkingPanelArea();
        vl.removeAllComponents();
        if (newModule != null) {
            HorizontalLayout hz = new HorizontalLayout();
            if (newModule.isHeaderEnable()) {
                headerArea = newModule.getHeader();
                vl.addComponent(newModule.getHeader());
            }
            if (!newModule.isList()) {
                vl.addComponent(newModule);

            } else {
                Component c = newModule.getLeftComponent();
                if (c != null) {
                    if (c instanceof VerticalLayout) {
                        VerticalLayout vc = (VerticalLayout) c;
                        vc.setWidth("30px");
                    }
                    hz.addComponent(c);
                }
                hz.addComponent(newModule);
                hz.setSizeFull();
                hz.setExpandRatio(newModule, 1.0f);
                vl.addComponent(hz);
            }
            if (newModule.isFooterEnable()) {
                vl.addComponent(newModule.getFooter());
            }

            if (!newModule.isList()) {
                vl.setExpandRatio(newModule, 1.0f);

            } else {
                vl.setExpandRatio(hz, 1.0f);
            }

            vl.setPrimaryStyleName("rootlist");
        }


        vl.setSizeFull();

        if (newModule != null) {
            newModule.setWidth("100%");
            if (newModule.getViewName() != null) {
                regNavigator(newModule);
            }
        }
    }

    public void updateWorkingArea(TenmaNewPanel newModule) {
        logger.info("updateWorkingArea(TenmaNewPanel newModule, String path)");

        TenmaMonitor.getInstance().pageLoaded();
        VerticalLayout vl = new VerticalLayout();
        if (welcome != null)
            vl = welcome.getWorkingPanelArea();
        vl.removeAllComponents();
        vl.addComponent(newModule);
        vl.setSizeFull();

        if (newModule != null) {
            newModule.setWidth("100%");
            updateWorkingAreaEvent(newModule);
            if (newModule.getViewName() != null) {
                regNavigator(newModule);
            }
        }

    }

    private void regNavigator(Object module) {
        if (module instanceof TenmaNewPanel) {
            System.out.println("TA.reg-Navigator ->" + ((TenmaNewPanel) module).getViewName());
            if (workNavigator == null) {
                VerticalLayout workLayout = welcome.getWorkingPanelArea();
                Navigator.ComponentContainerViewDisplay workview = new Navigator.ComponentContainerViewDisplay(workLayout);
                workNavigator = new Navigator(UI.getCurrent(), workview);
            }
            TenmaNewPanel tenmaNewPanel = (TenmaNewPanel) module;
//            logger.info("workNavigator = " + workNavigator.getCurrentView());
            workNavigator.addView(tenmaNewPanel.getViewName(), tenmaNewPanel);
            workNavigator.navigateTo(tenmaNewPanel.getViewName());
//            logger.info("workNavigator = " + workNavigator.getCurrentView());
        } else if (module instanceof TenmaPanel) {
            if (workNavigator == null) {
                VerticalLayout workLayout = welcome.getWorkingPanelArea();
                Navigator.ComponentContainerViewDisplay workview = new Navigator.ComponentContainerViewDisplay(workLayout);
                workNavigator = new Navigator(UI.getCurrent(), workview);
            }
            TenmaPanel tenmaPanel = (TenmaPanel) module;
//            logger.info("workNavigator = " + workNavigator.getCurrentView());
            workNavigator.addView(tenmaPanel.getViewName(), tenmaPanel);
            workNavigator.navigateTo(tenmaPanel.getViewName());
//            logger.info("workNavigator = " + workNavigator.getCurrentView());
        }
    }

    private void updateWorkingAreaEvent(TenmaNewPanel panel) {
        for (WorkingAreaChangeListener walistener : workingAreaListner) {
            walistener.workingAreaChangeEvent(activeScreen, panel);
        }
        activeScreen = panel;
        logger.info("panel = " + panel);
    }

    public void addWorkingAreaChangeListener(WorkingAreaChangeListener listener) {
        logger.info("TenmaWebApplication.addWorkingAreaChangeListener ");
        workingAreaListner.add(listener);
    }

    public String getAuthenticationSource() {
        return authenticationSource;
    }

    public void setAuthenticationSource(String authenticationSource) {
        this.authenticationSource = authenticationSource;
    }

    public String getCurrentLocaleLanguageCode() {
        return params.getLanguageCode();
    }

    public void setCurrentLocaleLanguageCode(String tmpLangCode) {
        params.setLanguageCode(tmpLangCode);
    }

    public Integer getMenuCurrentState() {
        return currentState;
    }

    public void setMenuCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getStateLayout() {
        return stateLayout;
    }

    public void setStateLayout(String stateLayout) {
        this.stateLayout = stateLayout;
    }

    public boolean isSuperAdmin() {
        //only superadmin
        boolean superAdmin = false;
        if (getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                superAdmin = true;

        return superAdmin;
    }
}



