package com.tenma.common.gui.display;

import com.tenma.auth.model.AuditModel;
import com.tenma.common.TA;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.common.util.customlabel.CustomStructureLabel;
import com.tenma.common.util.web.ClientInfo;
import com.tenma.core.bean.reference.CommunityUserGroupMenuFunctionHelper;
import com.tenma.core.bean.reference.CommunityUserGroupMenuHelper;
import com.tenma.model.core.CommunityUserGroupMenuModel;
import com.tenma.model.core.UserGroupMenuFunctionModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ndwijaya on 9/21/15.
 * Draft of TenmaPanel v 2 ( Will be testing on course, open for any adjustment as requiared )
 * Existing customizing from old TenmaPanel
 * - remove all report related, should be used not on tenmapanel
 * - remote all email and sms utility related
 * - make security is disabled for panel
 * - considering remove security related
 * Adding new features
 * - responsive layout buildin, including event
 * - Navigator include (still POC-not finish yet)
 * - replace updateworkingarea method with UpdateScreen (TODO)
 */

public abstract class TenmaNewPanel extends Panel implements TenmaInterface, Button.ClickListener, View {
    public final Logger log;
    public Params param;
    private String taskId; // populated when the current Panel is executed from task action
    private Integer menuId;
    private String viewName = null;

    /*
        Default constructor, set security loading disable
     */
    public TenmaNewPanel() {
        log = LoggerFactory.getLogger(TenmaNewPanel.class);
        initializeUI(false); // security function is not loaded.
    }

    /*
        Optionally load security as parameter
        since default loading security mean, cost in query to database every panel is created.
        some of panel is used without using the security
     */
    public TenmaNewPanel(boolean enableSecurity) {
        log = LoggerFactory.getLogger(TenmaNewPanel.class);
        initializeUI(enableSecurity);
    }

    public boolean isPersonalMember() {
        return TA.getCurrent().isPersonalMember();
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String argViewName) {
        this.viewName = argViewName;
    }


    private final void initializeUI(boolean enableSecurity) {
        param = Params.getInstance(TA.getCurrent().getLocale());
        if (enableSecurity) {
            pupulateSecurityMenu();
        }
    }

    public String getLabel(String argLabel) {
        if (argLabel.toUpperCase().startsWith("STATIC.")) {
            return argLabel.substring(7);
        }
        return param.getLabel(argLabel);
    }

    public String getLabel(String structureId, String argLabel) {
        return CustomStructureLabel.getInstance().getStructureLabel(structureId, argLabel);
    }

    public void buttonClick(Button.ClickEvent clickEvent) {
    }

    public final void setAuditTrail(AuditModel m) {
        String currentUserID = TA.getCurrent().getClientInfo().getClientUserModel().getUserId();
        if (currentUserID == null) currentUserID = Constants.SYSTEM;
        m.setCreatedFrom(TA.getCurrent().getRemoteAddress());
        m.setCreatedBy(currentUserID);
        m.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
        m.setUpdatedBy(currentUserID);
    }


    @Override
    public void setContent(Component content) {
        if (content != null) setCompositionRoot(content);
    }

    public void setCompositionRoot(Component compositionRoot) {
        super.setContent(compositionRoot);
        setStyleName(ValoTheme.PANEL_BORDERLESS);
        setId("workarea");
        setSizeFull();
    }


    public boolean isFunctionEnable(String functionCode) {
        Boolean m = false;

        if (functionCode != null && functionCode.trim().length() != 0)
            m = TA.getCurrent().getClientInfo().getSecurityModel().getCustomAuthenticationCode().containsKey(functionCode);

        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;

        return m.booleanValue();
    }

    public boolean isCreateAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isCreateAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isReadAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isReadAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isUpdateAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isUpdateAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isDeleteAccessEnable() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isDeleteAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    public boolean isPrintAccess() {
        Boolean m = TA.getCurrent().getClientInfo().getSecurityModel().isPrintAccess();
        if (TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType() != null)
            if (Constants.TENMA_USER_TYPE.SUPER.getValue() == TA.getCurrent().getClientInfo().getClientAuthUserModel().getUserLevelType().intValue())
                m = true;
        return m.booleanValue();
    }

    private void pupulateSecurityMenu() {
        if (TA.getCurrent().getClientInfo() != null)
            if (TA.getCurrent().getClientInfo().getSessionProcessId() != null) {
                CommunityUserGroupMenuHelper hlp = new CommunityUserGroupMenuHelper();
                List<CommunityUserGroupMenuModel> ls = hlp.queryAuthenticatedMenu(TA.getCurrent().getCommunityModel().getCommunityId(),
                        TA.getCurrent().getClientInfo().getClientMemberModel().getMemberId(),
                        TA.getCurrent().getClientInfo().getSessionProcessId());

                int bigAuth = 0;
                if (ls != null) {
                    for (CommunityUserGroupMenuModel m : ls)
                        if (bigAuth < m.getAuthorizationCode()) bigAuth = m.getAuthorizationCode();
                }
                if (bigAuth == 0)
                    bigAuth = 31; // no security implemented

                TA.getCurrent().getClientInfo().getSecurityModel().setAuthenticationCode(bigAuth);

                populateCustomFuctionalAuthList(TA.getCurrent().getClientInfo());
            }

    }


    private void populateCustomFuctionalAuthList(ClientInfo userInfo) {

        HashMap objFunc = new HashMap();

        HashMap param = new HashMap();
        if (userInfo != null) {
            if (userInfo.getSessionProcessId() != null) {

                CommunityUserGroupMenuFunctionHelper functionHelper = new CommunityUserGroupMenuFunctionHelper();
                List<UserGroupMenuFunctionModel> lsCustomFunction = functionHelper.getListCoreGroupMemberMenuFunction(TA.getCurrent().getCommunityModel().getCommunityId(), userInfo.getSessionProcessId(), userInfo.getClientMemberModel().getMemberId(), false);
                if (lsCustomFunction != null && lsCustomFunction.size() > 0) {
                    for (UserGroupMenuFunctionModel function : lsCustomFunction) {
                        objFunc.put(function.getFunctionCode(), true);
                    }
                }
            }
            userInfo.getSecurityModel().setCustomAuthenticationCode(objFunc);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        java.lang.System.out.println("share.tenma2.common.gui.display.TenmaNewPanel.enter "+getViewName());
//        TA.getCurrent().actionEnterView(this);
    }

    @Override
    public void setFocus(Component componentName) {

    }

    @Override
    public String getTaskId() {
        return null;
    }

    @Override
    public void setTaskId(String taskId) {

    }
}
