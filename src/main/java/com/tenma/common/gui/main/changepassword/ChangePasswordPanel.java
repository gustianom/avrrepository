package com.tenma.common.gui.main.changepassword;

import com.tenma.auth.model.UserModel;
import com.tenma.common.TA;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.gui.display.TenmaNewPanel;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * Created by PT Tenma Bright Sky
 * User: anom
 * Date: 7/24/13
 * Time: 3:00 PM
 */
public class ChangePasswordPanel extends TenmaNewPanel {

    private ConfirmChangePasswordView parentComponent;
    private UserModel selectedUserModel;
    private PasswordField fieldNewPassword;
    private PasswordField fieldConfirmPassword;

    public ChangePasswordPanel(ConfirmChangePasswordView components) {
        super();
        parentComponent = components;

        this.selectedUserModel = TA.getCurrent().getClientInfo().getClientUserModel();

        System.out.println("ConfirmPasswordView.ConfirmPasswordView " + getLocale());
        setCompositionRoot(createLayoutChangePassword());

    }

    private VerticalLayout createLayoutChangePassword() {
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
        parentComponent.getBtnViewAccount().setVisible(true);
        parentComponent.getButtonContinue().setVisible(false);
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