package com.tenma.common.gui.main.forgotpassword;

import com.tenma.auth.model.CommunityModel;
import com.tenma.auth.model.UserModel;
import com.tenma.auth.util.logon.CommunityHelper;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.tenma.common.util.Constants;
import com.tenma.common.util.NotificationTools;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class ConfirmPasswordView extends TenmaNewPanel implements ForgotInterface {

    private final ForgotPassword parentComponent;
    private final String domainName;
    private UserModel selectedUserModel;
    private PasswordField fieldNewPassword;
    private PasswordField fieldConfirmPassword;

    public ConfirmPasswordView(ForgotPassword components) {
        super();
        HttpServletRequest request = (HttpServletRequest) VaadinService.getCurrentRequest();

        domainName = new StringBuffer()
                .append(request.getScheme()).append("://")
                .append(request.getServerName())
                .append(request.getServerPort() == 80 ? "" : ":")
                .append(request.getServerPort() == 80 ? "" : request.getServerPort())
                .append(request.getContextPath())
                .toString();

        parentComponent = components;
        parentComponent.setForceStageIndex(ForgotPassword.STAGE_INDEX.ENTER_NEW_PASSWORD);

        this.selectedUserModel = (UserModel) components.dataForgot.get(components.USER_MODEL);

        System.out.println("ConfirmPasswordView.ConfirmPasswordView " + getLocale());
        setCompositionRoot(createLayoutForgot());

    }

    private VerticalLayout createLayoutForgot() {
        VerticalLayout lay = new VerticalLayout();
        lay.setWidth("100%");
        lay.setSpacing(true);


        StringBuffer buf = new StringBuffer()
                .append("<span class=\"orange\"><b>")
                .append(param.getLabel("forgotPassword.updatePassword"))
                .append("</b></span>");

        Label l = new Label(buf.toString());
        l.setContentMode(ContentMode.HTML);

        lay.addComponent(new Label());
        lay.addComponent(new Label());
        lay.addComponent(l);
        lay.addComponent(new Label());
        lay.addComponent(createVerificationEntry());

        return lay;
    }

    private Component createVerificationEntry() {
        HorizontalLayout ly = new HorizontalLayout();
        ly.setSpacing(true);
        ly.setWidth("100%");
        ly.setMargin(true);

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setWidth("100%");

        fieldNewPassword = new PasswordField(param.getLabel("forgotPassword.newPassword"));
        fieldConfirmPassword = new PasswordField(param.getLabel("default.confirmPassword"));

        vl.addComponent(fieldNewPassword);
        vl.addComponent(fieldConfirmPassword);

        StringBuffer buf = new StringBuffer()
                .append(param.getLabel("forgotPassword.enterNewPasswordMessage").replaceFirst("#@@emailAddress@@#", selectedUserModel.getEmailAddress()));

        Label lb = new Label(buf.toString());
        lb.setContentMode(ContentMode.HTML);

        ly.addComponent(lb);
        ly.addComponent(vl);
        return ly;
    }

    private void viewUpdateSuccess() {

        CommunityModel communityModel = new CommunityModel();
        communityModel.setCommunityId(selectedUserModel.getDefCommunityId());

        CommunityHelper hml = new CommunityHelper();
        communityModel = hml.getCommunityDetail(communityModel);
        String communityName = "";
        if (communityModel != null) communityName = communityModel.getCommunityName();

        HashMap map = new HashMap();
        map.put("emailAddress", selectedUserModel.getEmailAddress());
        map.put("communityName", communityName);
        map.put("userName", selectedUserModel.getEmailAddress());
        map.put("userFullName", selectedUserModel.getUserFullName());
        map.put("domainName", domainName);
        NotificationTools tools = new NotificationTools(TA.getCurrent().getLocale(), Constants.SYSTEM, Constants.SYSTEM, "127.0.0.1");

        try {
            tools.doSendUserNotificationEmail(map, selectedUserModel.getEmailAddress(), Constants.EMAIL_TYPE.NOTIFY_RESET_PASSWORD, null, null);
        } catch (Exception e) {

        }

        VerticalLayout ly = new VerticalLayout();
        ly.setWidth("100%");
        ly.setSpacing(true);
        ly.setMargin(true);

        ly.addComponent(new Label());
        ly.addComponent(new Label());
        ly.addComponent(new Label(param.getLabel("forgotPassword.sucessfullyResetPasswordTitle")));
        ly.addComponent(new Label(param.getLabel("forgotPassword.sucessfullyResetPasswordMessage")
                .replaceFirst("#@@emailAddress@@#", selectedUserModel.getEmailAddress())
                .replaceFirst("#@@userFullName@@#", selectedUserModel.getUserFullName()))
        );
        parentComponent.getButtonContinue().setVisible(false);
        parentComponent.getBtnViewAccount().setVisible(true);
        setCompositionRoot(ly);
    }

    public boolean saveCurrentStage() throws Exception {
        boolean isCont = false;
        String val = fieldNewPassword.getValue();
        String conf = fieldConfirmPassword.getValue();

        if (val.equals(conf)) {
            UserHelper hlp = new UserHelper();
            UserModel m = new UserModel();
            m.setUserId(selectedUserModel.getUserId());
            m.setPassword(val);
            m.setUpdatedBy(selectedUserModel.getUserId());
            m.setUpdatedFrom(TA.getCurrent().getRemoteAddress());
            m.setCreatedBy(selectedUserModel.getUserId());
            m.setCreatedFrom(TA.getCurrent().getRemoteAddress());
            int res = hlp.updatePassword(m, TA.getCurrent().getAuthenticationSource());
            if (res != 0) {
                viewUpdateSuccess();
            }

        } else
            Notification.show("Confirm password and password not equal", Notification.Type.ERROR_MESSAGE);
        return isCont;
    }

    public void doFieldFocus() {

    }
}